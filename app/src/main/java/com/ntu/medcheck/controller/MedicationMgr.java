package com.ntu.medcheck.controller;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.utils.SafeItemOnClickListener;
import com.ntu.medcheck.view.EditMedicationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class MedicationMgr {

    private static Context context;

    Schedule schedule = Schedule.getInstance();
    ArrayList<MedicationEntry> medicationEntryArrayList;
    int clickedPosition;

    private MedicationMgr() {}

    public static void setContext(Context context) {
        MedicationMgr.context = context;
    }

    public static Context getContext() {
        return context;
    }

    private static MedicationMgr medicationMgrInstance = new MedicationMgr();

    public static MedicationMgr getInstance() {
        return medicationMgrInstance;
    }

    public void dynamicDisplayMedication(Fragment fragment, View view) {
        medicationEntryArrayList = Schedule.getInstance().getMedication();
        ListView listView;
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> dosage = new ArrayList<>();
        ArrayList<String> frequency = new ArrayList<>();
        ArrayList<String> comment = new ArrayList<>();
        ArrayList<MedicationEntry> entries = new ArrayList<>();
        listView = view.findViewById(R.id.medicationListView);

        for (MedicationEntry entry : medicationEntryArrayList) {
            title.add(entry.getName());
            dosage.add(entry.getDosage());
            comment.add(entry.getComment());
            frequency.add(entry.getFrequency());
            entries.add(entry);
            ArrayList<Time> timeTempList = entry.getTime();
            String tStr = "";
            for(Time t : timeTempList) {
                tStr += t.getHour() + ":" + t.getMinute() + " | ";
            }
            time.add(tStr);
        }

        MyAdapter adapter = new MyAdapter(view.getContext(), title, time, dosage, frequency, comment);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new SafeItemOnClickListener() {
            @Override
            public void onOneClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(title.get(position));
                storeVariable(position);
                Intent i = new Intent(fragment.getActivity(), EditMedicationActivity.class);
                fragment.startActivity(i);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void destroy(AppCompatActivity aca, boolean save, boolean delete) throws ParseException {
        if (save) {
            removeMedication();
            new ScheduleMgr().save();
            addMedication(aca);
        }
        else if (delete) {
            removeMedication();
            new ScheduleMgr().save();
        }
    }

    public void removeMedication() {
        MedicationEntry entry = Schedule.getInstance().getMedication().remove(clickedPosition);
        for (Time time : entry.getTime()) {
            new NotificationScheduler().cancelNotification(context, time.getId());
        }
    }

    public void storeVariable(int position) {
        this.clickedPosition = position;
    }

    public ArrayList<String> displayEditMedication(AppCompatActivity aca) {
        medicationEntryArrayList = Schedule.getInstance().getMedication();
        Log.d("IndexPos", Integer.toString(clickedPosition));
        MedicationEntry info = medicationEntryArrayList.get(clickedPosition);
        EditText name = aca.findViewById(R.id.editMedicationName);
        EditText dosage = aca.findViewById(R.id.editDosageInt);
        EditText frequency = aca.findViewById(R.id.repeatInt);
        EditText comment = aca.findViewById(R.id.commentMedication);

        name.setText(info.getName());
        dosage.setText(info.getDosage());
        frequency.setText(info.getFrequency());
        comment.setText(info.getComment());
        return dynamicAddTimeEditMedication(aca, info);
    }

    public ArrayList<String> dynamicAddTimeEditMedication(AppCompatActivity aca, MedicationEntry info) {
        ArrayList<Time> timeArrayList= info.getTime();
        ArrayList<String> index = new ArrayList<>();
        ArrayList<String> hour = new ArrayList<>();
        ArrayList<String> minute = new ArrayList<>();
        Integer i = 0;
        for (Time t : timeArrayList) {
            index.add(Integer.toString(i+1));
            i += 1;

            hour.add(t.getHour());

            minute.add(t.getMinute());
        }

        MyAddMedicationAdapter arrayAdapter = new MyAddMedicationAdapter(aca.getApplicationContext(), index, hour, minute);

        ListView listView = aca.findViewById(R.id.addMedicationListView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(index.get(position)));
        return index;
    }

    public boolean checkStatus(AppCompatActivity aca) {
        EditText name = aca.findViewById(R.id.editMedicationName);
        EditText dosage = aca.findViewById(R.id.editDosageInt);
        if (name.getText().toString() == null || name.getText().toString().isEmpty() ||
                dosage.getText().toString() == null || dosage.getText().toString().isEmpty()) {
            Toast.makeText(aca, R.string.emptyMedication, Toast.LENGTH_SHORT).show();
            return false;
        }
        ListView listView = aca.findViewById(R.id.addMedicationListView);
        for (int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            EditText hour = v.findViewById(R.id.addMedicationHour);
            EditText minute = v.findViewById(R.id.addMedicationMinute);
            String h = hour.getText().toString();
            String m = minute.getText().toString();
            if (h.isEmpty() || m.isEmpty() ||
                    Integer.parseInt(h) > 23 || Integer.parseInt(h) < 0 ||
                    Integer.parseInt(m) > 60 || Integer.parseInt(m) < 0) {
                Toast.makeText(aca, R.string.emptyMedication, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean addMedication(AppCompatActivity aca) throws ParseException {
        EditText name = aca.findViewById(R.id.editMedicationName);
        EditText dosage = aca.findViewById(R.id.editDosageInt);
        TextView comment = aca.findViewById(R.id.commentMedication);
        if (name.getText().toString() == null || name.getText().toString().isEmpty() ||
            dosage.getText().toString() == null || dosage.getText().toString().isEmpty()) {
            Toast.makeText(aca, R.string.emptyMedication, Toast.LENGTH_SHORT).show();
            return false;
        }
        MedicationEntry medication = new MedicationEntry();

        medication.setName(name.getText().toString().trim());
        medication.setDosage(dosage.getText().toString().trim());
        medication.setComment(comment.getText().toString().trim());
        medication.setType("medication");

        ListView listView = aca.findViewById(R.id.addMedicationListView);
        for (int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            EditText hour = v.findViewById(R.id.addMedicationHour);
            EditText minute = v.findViewById(R.id.addMedicationMinute);
            String h = hour.getText().toString();
            String m = minute.getText().toString();

            if (h.isEmpty() || m.isEmpty() ||
                    Integer.parseInt(h) > 23 || Integer.parseInt(h) < 0 ||
                    Integer.parseInt(m) > 60 || Integer.parseInt(m) < 0) {
                Toast.makeText(aca, R.string.emptyMedication, Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                int id = new Random().nextInt(100000);
                Time newTime = new Time(String.format("%02d%02d", Integer.parseInt(h), Integer.parseInt(m)));
                newTime.setId(id);
                medication.getTime().add(newTime);
                setNotification(medication, newTime);
            }
        }

        schedule = Schedule.getInstance();
        schedule.getMedication().add(medication);

        new ScheduleMgr().save();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNotification(MedicationEntry entry, Time time) throws ParseException {
        LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        String h = time.getHour();
        String m = time.getMinute();

        // if time is later than current time, schedule today, if time is earlier than current, schedule tmr
        String dateString = String.format("%02d-%d-%04d %02d:%02d:%02d", day, month, year, Integer.parseInt(h), Integer.parseInt(m), 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date1 = sdf.parse(dateString);
        long future1 = date1.getTime();
        Date now1 = new Date();
        long current = now1.getTime();

        String title = entry.getName() + " at " + h + ":" + m;
        String content = entry.getDosage() + " " + entry.getComment();

        long milliSecond;

        if (!(future1 > current)) {
            // schedule tmr
            dateString = String.format("%02d-%d-%04d %02d:%02d:%02d", day+1 , month, year, Integer.parseInt(h), Integer.parseInt(m), 0);
            date1 = sdf.parse(dateString);
            future1 = date1.getTime();
        }

        milliSecond = future1 - current;
        NotificationScheduler notificationScheduler = new NotificationScheduler();
        Notification notification = notificationScheduler.getNotification(content, title , context, time.getId());
        notificationScheduler.scheduleNotification(notification, milliSecond, context, true, time.getId());
    }

    public void dynamicAddTime(AppCompatActivity aca, ArrayList<String> indexIn) {
        ArrayList<String> index = indexIn;
        ArrayList<String> hour = new ArrayList<>();
        ArrayList<String> minute = new ArrayList<>();

        ListView listView = aca.findViewById(R.id.addMedicationListView);
        for (int i = 0; i < index.size(); i++) {
            if (listView.getChildAt(i) == null) {
                hour.add("");
                minute.add("");
                continue;
            }
            View v = listView.getChildAt(i);
            if (v.findViewById(R.id.addMedicationHour) != null) {
                EditText hourText = v.findViewById(R.id.addMedicationHour);
                hour.add(hourText.getText().toString().trim());
            }
            if (v.findViewById(R.id.addMedicationMinute) != null) {
                EditText minuteText = v.findViewById(R.id.addMedicationMinute);
                minute.add(minuteText.getText().toString().trim());
            }
        }

        MyAddMedicationAdapter arrayAdapter = new MyAddMedicationAdapter(aca.getApplicationContext(), index, hour, minute);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(index.get(position)));
    }

    class MyAddMedicationAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> index;
        ArrayList<String> hour;
        ArrayList<String> minute;

        public MyAddMedicationAdapter(@NonNull Context context, ArrayList<String> index, ArrayList<String> hour, ArrayList<String> minute) {
            super(context, R.layout.add_medication_row, index);
            this.context = context;
            this.index = index;
            this.hour = hour;
            this.minute = minute;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View add_medication_row = layoutInflater.inflate(R.layout.add_medication_row, parent, false);
            TextView indexText = add_medication_row.findViewById(R.id.addMedicationTimeIndex);
            indexText.setText(index.get(position));
            if (add_medication_row.findViewById(R.id.addMedicationHour) != null) {
                EditText hourText = add_medication_row.findViewById(R.id.addMedicationHour);
                hourText.setText(hour.get(position));
            }
            if (add_medication_row.findViewById(R.id.addMedicationMinute) != null) {
                EditText minuteText = add_medication_row.findViewById(R.id.addMedicationMinute);
                minuteText.setText(minute.get(position));
            }
            return add_medication_row;
        }
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> adapterTitle;
        ArrayList<String> adapterTime;
        ArrayList<String> adapterDosage;
        ArrayList<String> adapterFrequency;
        ArrayList<String> adapterComment;

        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> time, ArrayList<String> dosage, ArrayList<String> frequency, ArrayList<String> comments) {
            super(context, R.layout.checkup_row, title);
            this.context = context;
            this.adapterTitle = title;
            this.adapterTime = time;
            this.adapterDosage = dosage;
            this.adapterFrequency = frequency;
            this.adapterComment = comments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View medication_row = layoutInflater.inflate(R.layout.medication_row, parent, false);
            TextView title = medication_row.findViewById(R.id.titleMedicationRow);
            TextView time = medication_row.findViewById(R.id.timeMedicationRow);
            TextView dosage = medication_row.findViewById(R.id.dosageMedicationRow);
            TextView frequency = medication_row.findViewById(R.id.frequencyMedicationRow);
            TextView comments = medication_row.findViewById(R.id.commentMedicationRow);

            title.setText(adapterTitle.get(position));
            time.setText(medication_row.getResources().getString(R.string.eventTime) + adapterTime.get(position));
            dosage.setText(medication_row.getResources().getString(R.string.newMedDosageText) + adapterDosage.get(position));
            frequency.setText(medication_row.getResources().getString(R.string.eventFreq) + adapterFrequency.get(position));
            comments.setText(medication_row.getResources().getString(R.string.Comment) + adapterComment.get(position));
            return medication_row;
        }
    }
}
