package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;

import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.view.EditCheckupActivity;
import com.ntu.medcheck.model.Schedule;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckUpMgr {

    private Map<String, ArrayList<CheckUpEntry>> entries;

    public CheckUpMgr() {
        entries = new HashMap<>();
    }

    public void init() {

        entries.put("202104", new ArrayList<>());
        entries.put("202105", new ArrayList<>());

        CheckUpEntry c1 = new CheckUpEntry();
        c1.getTime().setTime("202104031220");
        c1.setName("c1!");
        c1.setComment("c1Comment");
        c1.setType("checkup");
        c1.setClinic("cl1");
        c1.setTitle("C1");

        CheckUpEntry c2 = new CheckUpEntry();
        c2.getTime().setTime("202104031221");
        c2.setName("c2!");
        c2.setComment("c2Comment");
        c2.setType("checkup");
        c2.setClinic("cl2");
        c2.setTitle("C2");

        CheckUpEntry c3 = new CheckUpEntry();
        c3.getTime().setTime("202104041220");
        c3.setName("c3!");
        c3.setComment("c3Comment");
        c3.setType("checkup");
        c3.setClinic("cl3");
        c3.setTitle("C3");

        CheckUpEntry c4 = new CheckUpEntry();
        c4.getTime().setTime("202105031220");
        c4.setName("c4!");
        c4.setComment("c4Comment");
        c4.setType("checkup");
        c4.setClinic("cl4");
        c4.setTitle("C4");

        CheckUpEntry c5 = new CheckUpEntry();
        c5.getTime().setTime("202105041220");
        c5.setName("c5!");
        c5.setComment("c5Comment");
        c5.setType("checkup");
        c5.setClinic("cl5");
        c5.setTitle("C5");

        entries.get("202104").add(c1);
        entries.get("202104").add(c2);
        entries.get("202104").add(c3);
        entries.get("202105").add(c4);
        entries.get("202105").add(c5);
    }

    public void save() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference sRef = fDatabase.getReference("Schedules");
        DatabaseReference suRef = sRef.child(fAuth.getCurrentUser().getUid());
        Schedule schedule = Schedule.getInstance();
        schedule.setCheckup(entries);
        schedule.setMedication(new ArrayList<>());

        //////////////////////////////////////////////////////////////////////////////////
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

        suRef.setValue(schedule);
    }

    public void dynamicDisplayCheckup(Fragment fragment, View view) {
        ListView listView;
        ArrayList<String> title = getTitle();
        ArrayList<String> date = getDate();
        ArrayList<String> time = getTime();
        ArrayList<String> location = getLocation();
        ArrayList<String> comment = getComment();

        listView = view.findViewById(R.id.checkupListView);

        if (listView == null) {
            System.out.println("list view is null");
        }

        if (view.getContext() == null) {
            System.out.println("context is null");
        }
        else {
            System.out.println("context not null");
        }

        MyAdapter adapter = new MyAdapter(view.getContext(), title, date, time, location, comment);

        listView.setAdapter(adapter);

        // !!!!!!! on click, view in detail and can edit
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            System.out.println(title.get(position));
            Intent i = new Intent(fragment.getActivity(),EditCheckupActivity.class);
            fragment.startActivity(i);
        });
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

    public ArrayList<String> getTitle() {
        ArrayList<String> title = new ArrayList<>();
        title.add("heart checkup");
        title.add("liver checkup");
        title.add("lung checkup");
        title.add("lung checkup");
        title.add("lung checkup");
        title.add("lung checkup");
        return title;
    }

    public ArrayList<String> getDate() {
        ArrayList<String> date = new ArrayList<>();
        date.add("20210410");
        date.add("20210411");
        date.add("20210415");
        date.add("20210415");
        date.add("20210415");
        date.add("20210415");
        return date;
    }

    public ArrayList<String> getTime() {
        ArrayList<String> time = new ArrayList<>();
        time.add("1230");
        time.add("1330");
        time.add("1530");
        time.add("1530");
        time.add("1530");
        time.add("1530");
        return time;
    }

    public ArrayList<String> getLocation() {
        ArrayList<String> location = new ArrayList<>();
        location.add("loc1");
        location.add("loc2");
        location.add("loc3");
        location.add("loc3");
        location.add("loc3");
        location.add("loc3");
        return location;
    }

    public ArrayList<String> getComment() {
        ArrayList<String> comment = new ArrayList<>();
        comment.add("comment1");
        comment.add("comment2");
        comment.add("comment3");
        comment.add("comment3");
        comment.add("comment3");
        comment.add("comment3");
        return comment;
    }
}
