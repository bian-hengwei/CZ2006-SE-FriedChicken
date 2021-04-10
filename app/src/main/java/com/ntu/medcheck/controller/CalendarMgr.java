package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.utils.SafeItemOnClickListener;
import com.ntu.medcheck.view.EditCheckupActivity;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarMgr implements OnNavigationButtonClickedListener {

    public CalendarMgr() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getMonth(int year, int month, View view) {
        Map<Integer, Object> map = new HashMap<>();
        String yearMonth = String.format("%04d%02d", year, month);
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            map.put(Integer.parseInt((child.getTime().getDay())), "current");
        }
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setDate(Calendar.getInstance(), map);
    }

    public void setListeners(View view) {
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        String yearMonth = String.format("%04d%02d", newMonth.get(Calendar.YEAR), newMonth.get(Calendar.MONTH)+1);
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            arr[0].put(Integer.parseInt((child.getTime().getDay())), "current");
        }
        return arr;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDateOnClick(Fragment fragment, View view, String yearMonth, int date) {
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Calendar> time = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        ArrayList<CheckUpEntry> entries = new ArrayList<>();
        for (CheckUpEntry child : monthArray) {
            if (child.getTime().getDay().equals(String.format("%02d", date))) {
                title.add(child.getTitle());
                time.add(child.getTime().toCalendar());
                location.add(child.getClinic());
                comments.add(child.getComment());
                entries.add(child);
            }
        }
        ListView listView = view.findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(view.getContext(), title, location, time, comments);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new SafeItemOnClickListener() {
            @Override
            public void onOneClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(title.get(position));
                Intent i = new Intent(fragment.getActivity(), EditCheckupActivity.class);
                CheckUpEntry target = entries.remove(position);
                new CheckUpMgr().removeCheckUp(target);
                i.putExtra("name", target.getName());
                i.putExtra("comment", target.getComment());
                i.putExtra("title", target.getTitle());
                i.putExtra("clinic", target.getClinic());
                i.putExtra("day", target.getTime().getDay());
                i.putExtra("month", target.getTime().getMonth());
                i.putExtra("year", target.getTime().getYear());
                i.putExtra("minute", target.getTime().getMinute());
                i.putExtra("hour", target.getTime().getHour());
                fragment.startActivity(i);
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> adapterTitle;
        ArrayList<String> adapterLocation;
        ArrayList<Calendar> adapterTime;
        ArrayList<String> adapterComment;
        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> location, ArrayList<Calendar> time, ArrayList<String> comments) {
            super(context, R.layout.calendar_row, title);
            this.context = context;
            this.adapterTitle = title;
            this.adapterLocation = location;
            this.adapterTime = time;
            this.adapterComment = comments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View calendar_row = layoutInflater.inflate(R.layout.calendar_row, parent, false);
            TextView title = calendar_row.findViewById(R.id.title);
            TextView location = calendar_row.findViewById(R.id.location);
            TextView time = calendar_row.findViewById(R.id.time);
            TextView comments = calendar_row.findViewById(R.id.commentCheckupRow);

            String dateStr = adapterTime.get(position).get(Calendar.HOUR) + "(hr)" + adapterTime.get(position).get(Calendar.MINUTE) + "(min)";

            title.setText(adapterTitle.get(position));
            location.setText(calendar_row.getResources().getString(R.string.clinicName) + adapterLocation.get(position));
            time.setText(calendar_row.getResources().getString(R.string.eventTime) + dateStr);
            comments.setText(calendar_row.getResources().getString(R.string.Comment) + adapterComment.get(position));

            return calendar_row;
        }
    }
}
