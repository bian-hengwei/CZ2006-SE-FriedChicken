package com.ntu.medcheck.controller;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;

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
            map.put(Integer.parseInt((child.getTime().getDay())), "absent");
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
            arr[0].put(Integer.parseInt((child.getTime().getDay())), "absent");
        }
        return arr;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDateOnClick(View view, String yearMonth, int date) {
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Calendar> time = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        for (CheckUpEntry child : monthArray) {
            if (child.getTime().getDay().equals(String.format("%02d", date))) {
                title.add(child.getTitle());
                time.add(child.getTime().toCalendar());
                location.add(child.getClinic());
                comments.add(child.getComment());
            }
        }
        ListView listView = view.findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(view.getContext(), title, location, time, comments);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(title.get(position)));
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> atitle;
        ArrayList<String> alocation;
        ArrayList<Calendar> atime;
        ArrayList<String> acomments;
        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> location, ArrayList<Calendar> time, ArrayList<String> comments) {
            super(context, R.layout.calendar_row, title);
            this.context = context;
            this.atitle = title;
            this.alocation = location;
            this.atime = time;
            this.acomments = comments;
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

            String dateStr = atime.get(position).get(Calendar.HOUR) + "(hr)" + atime.get(position).get(Calendar.MINUTE) + "(min)";

            title.setText(atitle.get(position));
            location.setText(calendar_row.getResources().getString(R.string.clinicName) + alocation.get(position));
            time.setText(calendar_row.getResources().getString(R.string.eventTime) + dateStr);
            comments.setText(calendar_row.getResources().getString(R.string.Comment) + acomments.get(position));

            return calendar_row;
        }
    }
}
