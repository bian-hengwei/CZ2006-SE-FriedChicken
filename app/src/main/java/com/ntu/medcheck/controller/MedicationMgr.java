package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.view.EditCheckupActivity;
import com.ntu.medcheck.view.EditMedicationActivity;


public class MedicationMgr {

    Schedule schedule = Schedule.getInstance();
    ArrayList<MedicationEntry> medicationEntryArrayList = schedule.getMedication();
    int clickedPosition;

    private MedicationMgr() {}

    private static MedicationMgr medicationMgrInstance = new MedicationMgr();

    public static MedicationMgr getInstance() {
        return medicationMgrInstance;
    }

    public void dynamicDisplayMedication(Fragment fragment, View view) {
        ListView listView;
        ArrayList<String> title = getTitle();
        ArrayList<String> time = getTime();
        ArrayList<String> dosage = getDosage();
        ArrayList<String> frequency = getFrequence();  // Take once every x days
        ArrayList<String> comment = getComment();



        MyAdapter adapter = new MyAdapter(view.getContext(), title, time, dosage, frequency, comment);
        listView = view.findViewById(R.id.medicationListView);
        listView.setAdapter(adapter);

        // !!!!!!! on click, view in detail and can edit
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            System.out.println(title.get(position));
            storeVariable(position);
            Intent i = new Intent(fragment.getActivity(), EditMedicationActivity.class);
            fragment.startActivity(i);
        });
    }

    public void storeVariable(int position) {
        this.clickedPosition = position;
    }

    public MedicationEntry getClickedMedication() {
        return medicationEntryArrayList.get(clickedPosition);
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ need to set time, time is on
    public void displayEditMedication(AppCompatActivity aca) {
        MedicationEntry info = medicationEntryArrayList.get(clickedPosition);
        EditText name = aca.findViewById(R.id.editMedicationName);
        EditText dosage = aca.findViewById(R.id.editDosageIntEditMed);
        EditText frequency = aca.findViewById(R.id.repeatIntEditMed);
        EditText comment = aca.findViewById(R.id.commentMedicationEditMed);

        name.setText(info.getName());
        dosage.setText(info.getDosage());
        frequency.setText(info.getFrequency());
        comment.setText(info.getComment());
        dynamicAddTimeEditMedication(aca, info);

    }

    public void dynamicAddTimeEditMedication(AppCompatActivity aca, MedicationEntry info) {
        ArrayList<Time> timeArrayList= info.getTime();
        ArrayList<String> index = new ArrayList<>();
        ArrayList<String> hour = new ArrayList<>();
        ArrayList<String> minute = new ArrayList<>();
        Integer i = 0;
        for(Time t : timeArrayList) {
            index.add(Integer.toString(i+1));
            i += 1;

            hour.add(t.getHour());

            minute.add(t.getMinute());
        }

        MyAddMedicationEditAdapter arrayAdapter = new MyAddMedicationEditAdapter(aca.getApplicationContext(), index, hour, minute);

        ListView listView = aca.findViewById(R.id.addMedicationListViewEditMed);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(index.get(position)));
    }

    class MyAddMedicationEditAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> index;
        ArrayList<String> hour;
        ArrayList<String> minute;

        public MyAddMedicationEditAdapter(@NonNull Context context, ArrayList<String> index, ArrayList<String> hour, ArrayList<String> minute) {
            super(context, R.layout.add_medication_row, index);
            System.out.println("4");
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
            EditText hourText = add_medication_row.findViewById(R.id.addMedicationHour);
            hourText.setText(hour.get(position));
            EditText minuteText = add_medication_row.findViewById(R.id.addMedicationMinute);
            minuteText.setText(minute.get(position));
            return add_medication_row;
        }

    }


    public void dynamicAddTime(AppCompatActivity aca, ArrayList<String> indexIn) {
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@ this index need to change. change into many arraylists with different info
        ArrayList<String> index = indexIn;
        MyAddMedicationAdapter arrayAdapter = new MyAddMedicationAdapter(aca.getApplicationContext(), index);

        ListView listView = aca.findViewById(R.id.addMedicationListView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(index.get(position)));
    }



    class MyAddMedicationAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> index;

        public MyAddMedicationAdapter(@NonNull Context context, ArrayList<String> index) {
            super(context, R.layout.add_medication_row, index);
            System.out.println("4");
            this.context = context;
            this.index = index;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View add_medication_row = layoutInflater.inflate(R.layout.add_medication_row, parent, false);
            TextView indexText = add_medication_row.findViewById(R.id.addMedicationTimeIndex);
            indexText.setText(index.get(position));
            return add_medication_row;
        }

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> atitle;
        ArrayList<String> atime;
        ArrayList<String> adosage;
        ArrayList<String> afrequency;
        ArrayList<String> acomment;

        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> time, ArrayList<String> dosage, ArrayList<String> frequency, ArrayList<String> comments) {
            super(context, R.layout.checkup_row, title);
            this.context = context;
            this.atitle = title;
            this.atime = time;
            this.adosage = dosage;
            this.afrequency = frequency;
            this.acomment = comments;
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

            title.setText(atitle.get(position));
            time.setText("Time: " + atime.get(position));
            dosage.setText("Dosage: " + adosage.get(position));
            frequency.setText("Frequency: " + afrequency.get(position));
            comments.setText("Comments: " + acomment.get(position));
            return medication_row;
        }

    }





    public ArrayList<String> getComment() {
        ArrayList<String> commentArrayList = new ArrayList<>();
        String comment;
        for(MedicationEntry medicationEntry : medicationEntryArrayList) {
            comment = medicationEntry.getComment();
            commentArrayList.add(comment);
        }
       /* commentArrayList.add("comment1");
        commentArrayList.add("comment2");
        commentArrayList.add("comment3");
        commentArrayList.add("comment3");
        commentArrayList.add("comment3");
        commentArrayList.add("comment3");
        */
        return commentArrayList;
    }

    public ArrayList<String> getFrequence() {
        ArrayList<String> frequencyArrayList = new ArrayList<>();
        String frequency;
        for(MedicationEntry medicationEntry : medicationEntryArrayList) {
            frequency = medicationEntry.getFrequency();
            frequencyArrayList.add(frequency);
        }

        /*frequencyArrayList.add("1");
        frequencyArrayList.add("2");
        frequencyArrayList.add("3");
        frequencyArrayList.add("2");
        frequencyArrayList.add("1");
        frequencyArrayList.add("2");*/
        return frequencyArrayList;
    }

    public ArrayList<String> getDosage() {
        ArrayList<String> dosageArrayList = new ArrayList<>();
        String dosage;
        for(MedicationEntry medicationEntry : medicationEntryArrayList) {
            dosage = medicationEntry.getDosage();
            dosageArrayList.add(dosage);
        }

        /*dosage.add("1230");
        dosage.add("1330");
        dosage.add("1530");
        dosage.add("1530");
        dosage.add("1530");
        dosage.add("1530");*/
        return dosageArrayList;
    }

    public ArrayList<String> getTime() {
        ArrayList<String> timeArrayList = new ArrayList<>();
        ArrayList<Time> timeTempList= new ArrayList<>();

        String time = "";

        for(MedicationEntry medicationEntry : medicationEntryArrayList) {
            timeTempList = medicationEntry.getTime();
            time = "";
            for(Time t : timeTempList) {
                time += t.getHour() + ":" + t.getMinute() + " | ";
            }
            timeArrayList.add(time);
        }

        /*time.add("1230");
        time.add("1330");
        time.add("1530");
        time.add("1530");
        time.add("1530");
        time.add("1530");*/
        return timeArrayList;
    }

    public ArrayList<String> getTitle() {
        ArrayList<String> titleArrayList = new ArrayList<>();

        for(MedicationEntry medicationEntry : medicationEntryArrayList) {
            titleArrayList.add(medicationEntry.getName());
        }
        /*title.add("heart medicine");
        title.add("liver medicine");
        title.add("lung medicine");
        title.add("lung medicine");
        title.add("lung medicine");
        title.add("lung medicine");*/
        return titleArrayList;
    }
}
