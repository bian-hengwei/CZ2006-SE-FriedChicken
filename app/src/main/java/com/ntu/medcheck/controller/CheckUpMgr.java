package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.utils.SafeItemOnClickListener;
import com.ntu.medcheck.view.EditCheckupActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class CheckUpMgr {

    public CheckUpMgr() {
    }

    public void dynamicDisplayCheckup(Fragment fragment, View view) {
        ListView listView;
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        ArrayList<String> comment = new ArrayList<>();
        ArrayList<CheckUpEntry> entries = new ArrayList<>();
        Log.d("dyn dis che", "dynamicDisplayCheckup: ");

        listView = view.findViewById(R.id.checkupListView);
        Map<String, ArrayList<CheckUpEntry>> checkup = Schedule.getInstance().getCheckup();
        ArrayList<String> sortedKeys = new ArrayList<>(checkup.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            ArrayList<CheckUpEntry> arr = checkup.get(key);
            Collections.sort(arr, (o1, o2) -> {
                if (o1.getTime().toCalendar().after(o2.getTime().toCalendar())) {
                    return 1;
                }
                return -1;
            });
            for (CheckUpEntry entry : arr) {
                if (entry.getTime().toCalendar().after(Calendar.getInstance())) {
                    Log.d("future entry", "dynamicDisplayCheckup: ");
                    title.add(entry.getTitle());
                    date.add(entry.getTime().getYear() + entry.getTime().getMonth() + entry.getTime().getDay());
                    time.add(entry.getTime().getHour() + entry.getTime().getMinute());
                    location.add(entry.getClinic());
                    comment.add(entry.getComment());
                    entries.add(entry);
                }
            }
        }
        MyAdapter adapter = new MyAdapter(view.getContext(), title, date, time, location, comment);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new SafeItemOnClickListener() {
            @Override
            public void onOneClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(title.get(position));
                Intent i = new Intent(fragment.getActivity(), EditCheckupActivity.class);
                CheckUpEntry target = entries.remove(position);
                Schedule.getInstance().remove(target);
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
                new ScheduleMgr().save();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void display(AppCompatActivity aca, Intent intent) {
        String name = intent.getStringExtra("name");
        String comment = intent.getStringExtra("comment");
        String title = intent.getStringExtra("title");
        String clinic = intent.getStringExtra("clinic");
        String day = intent.getStringExtra("day");
        String month = intent.getStringExtra("month");
        String year = intent.getStringExtra("year");
        String minute = intent.getStringExtra("minute");
        String hour = intent.getStringExtra("hour");
        TextView checkUpType = aca.findViewById(R.id.confirmed_checkup_type);
        checkUpType.setText(title);
        TextView clinicView = aca.findViewById(R.id.confirmed_clinic_name);
        clinicView.setText(clinic);
        EditText commentBox = aca.findViewById(R.id.commentBox);
        commentBox.setText(comment);
        DatePicker date = aca.findViewById(R.id.datePickerAddCheckup);
        date.init(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), null);
        TimePicker time = aca.findViewById(R.id.timePickerAddCheckup);
        time.setHour(Integer.parseInt(hour));
        time.setMinute(Integer.parseInt(minute));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean addCheckUp(AppCompatActivity aca) {
        TextView type = aca.findViewById(R.id.confirmed_checkup_type);
        TextView clinic = aca.findViewById(R.id.confirmed_clinic_name);
        DatePicker date = aca.findViewById(R.id.datePickerAddCheckup);
        TimePicker time = aca.findViewById(R.id.timePickerAddCheckup);
        TextView comment = aca.findViewById(R.id.commentBox);
        if (type.getText().toString() == null || type.getText().toString().isEmpty() ||
            clinic.getText().toString() == null || clinic.getText().toString().isEmpty()) {
            Toast.makeText(aca, R.string.AddCheckupEmptyType, Toast.LENGTH_SHORT).show();
            return false;
        }
        CheckUpEntry checkup = new CheckUpEntry();
        checkup.setName(type.getText().toString());
        checkup.setType("checkup");
        checkup.setComment(comment.getText().toString());
        checkup.setTitle(type.getText().toString());
        checkup.setClinic(clinic.getText().toString());
        Time t = new Time();
        int day = date.getDayOfMonth();
        int month = date.getMonth() + 1;
        int year = date.getYear();
        t.setTime(String.format("%04d%02d%02d", year, month, day));
        int minute = time.getMinute();
        int hour = time.getHour();
        t.setMinute(String.format("%02d", minute));
        t.setHour(String.format("%02d", hour));
        checkup.setTime(t);
        Schedule schedule = Schedule.getInstance();
        if (schedule.getCheckup().containsKey(String.format("%02d%02d", year, month))) {
            schedule.getCheckup().get(String.format("%02d%02d", year, month)).add(checkup);
        }
        else {
            ArrayList<CheckUpEntry> arr = new ArrayList<>();
            arr.add(checkup);
            schedule.getCheckup().put(String.format("%02d%02d", year, month), arr);
        }

        // Send notification
        //dd-M-yyyy hh:mm:ss

        String notifDay = String.format("%d/%d/%d", day, month, year);
        String notifTime = String.format("%d:%d", hour, minute);
        String title = "";
        String content = notifTime;
        title = type.getText().toString() + " at " + clinic.getText().toString() + ", " + notifDay;
        content += " " + comment.getText().toString();

        //dd-M-yyyy hh:mm:ss   get time in millisecond
        String dateString = String.format("%02d-%d-%04d %02d:%02d:%02d", day, month, year, hour, minute, 0);
        long milliSecond = getMillisecond(dateString);

        Random random = new Random();
        int randomId = random.nextInt(100000);

        NotificationScheduler notificationScheduler = new NotificationScheduler();
        notificationScheduler.scheduleNotification(notificationScheduler.getNotification(content, title , aca, randomId), milliSecond, aca, false, randomId);


        new ScheduleMgr().save();
        return true;
    }

    // adapter class
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> adapterTitle;
        ArrayList<String> adapterDate;
        ArrayList<String> adapterTime;
        ArrayList<String> adapterLocation;
        ArrayList<String> adapterComment;

        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> time, ArrayList<String> location, ArrayList<String> comments) {
            super(context, R.layout.checkup_row, title);
            this.context = context;
            this.adapterTitle = title;
            this.adapterDate = date;
            this.adapterTime = time;
            this.adapterLocation = location;
            this.adapterComment = comments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View checkup_row = layoutInflater.inflate(R.layout.checkup_row, parent, false);
            TextView title = checkup_row.findViewById(R.id.titleCheckupRow);
            TextView date = checkup_row.findViewById(R.id.dateCheckupRow);
            TextView time = checkup_row.findViewById(R.id.timeCheckupRow);
            TextView location = checkup_row.findViewById(R.id.locationCheckupRow);
            TextView comments = checkup_row.findViewById(R.id.commentCheckupRow);

            title.setText(adapterTitle.get(position));
            date.setText(checkup_row.getResources().getString(R.string.eventDate) + adapterDate.get(position).substring(6, 8) + " / " + adapterDate.get(position).substring(4, 6) + " / " + adapterDate.get(position).substring(0, 4));
            time.setText(checkup_row.getResources().getString(R.string.eventTime) + adapterTime.get(position).substring(0, 2) + " : " + adapterTime.get(position).substring(2, 4));
            location.setText(checkup_row.getResources().getString(R.string.clinicName) + adapterLocation.get(position));
            comments.setText(checkup_row.getResources().getString(R.string.Comment) + adapterComment.get(position));

            return checkup_row;
        }
    }

    public long getMillisecond(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try{
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(dateString);
            long future = date.getTime();
            Date now = new Date();
            long current = now.getTime();
            System.out.println("Given Time in milliseconds : "+future);
            System.out.println("Current in milliseconds : "+current);

            long time = future - current;
            return time;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
}
