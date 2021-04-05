package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.view.EditCheckupActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

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

        listView = view.findViewById(R.id.checkupListView);
        Map<String, ArrayList<CheckUpEntry>> checkup = Schedule.getInstance().getCheckup();
        ArrayList<String> sortedKeys = new ArrayList<>(checkup.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            ArrayList<CheckUpEntry> arr = checkup.get(key);
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
        listView.setOnItemClickListener((parent, view1, position, id) -> {
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
            Toast.makeText(aca, R.string.AddCheckupEmptyType, Toast.LENGTH_SHORT);
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
        new ScheduleMgr().save();
        return true;
    }

    // adapter class
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> atitle;
        ArrayList<String> adate;
        ArrayList<String> atime;
        ArrayList<String> alocation;
        ArrayList<String> acomment;

        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> time, ArrayList<String> location, ArrayList<String> comments) {
            super(context, R.layout.checkup_row, title);
            this.context = context;
            this.atitle = title;
            this.adate = date;
            this.atime = time;
            this.alocation = location;
            this.acomment = comments;
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

            title.setText(atitle.get(position));
            date.setText("Date: " + adate.get(position).substring(6, 8) + " / " + adate.get(position).substring(4, 6) + " / " + adate.get(position).substring(0, 4));
            time.setText("Time: " + atime.get(position).substring(0, 2) + " : " + atime.get(position).substring(2, 4));
            location.setText("Location: " + alocation.get(position));
            comments.setText("Comments: " + acomment.get(position));

            return checkup_row;
        }
    }
    public void save() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference sRef = fDatabase.getReference("Schedules");
        DatabaseReference suRef = sRef.child(fAuth.getCurrentUser().getUid());
        Schedule schedule = Schedule.getInstance();
        schedule.setMedication(new ArrayList<>());

        MedicationEntry med1 = new MedicationEntry("med1");
        med1.setDosage("1");
        med1.setType("type1");
        med1.setComment("comment1");
        med1.setUnit("ml");
        med1.setFrequency("1f");
        ArrayList<Time> t = new ArrayList<>();
        Time med1Time = new Time("1020");
        t.add(med1Time);
        Time med1Time2 = new Time("1130");
        t.add(med1Time2);
        med1.setTime(t);
        System.out.println(med1.getTime().get(0).getMinute());


        MedicationEntry med2 = new MedicationEntry("med2");
        med2.setDosage("2");
        med2.setType("type2");
        med2.setComment("comment2");
        med2.setUnit("ml2");
        med2.setFrequency("2f");
        Time med2Time = new Time("1520");
        ArrayList<Time> t2 = new ArrayList<>();
        t2.add(med2Time);
        Time med2Time2 = new Time("1830");
        t2.add(med2Time2);
        med2.setTime(t2);

        schedule.getMedication().add(med1);
        schedule.getMedication().add(med2);

        suRef.child("medication").setValue(schedule.getMedication());
    }
}
