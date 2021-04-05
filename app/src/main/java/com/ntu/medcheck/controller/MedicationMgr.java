package com.ntu.medcheck.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.ntu.medcheck.R;
import com.ntu.medcheck.model.MedicationEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;


public class MedicationMgr {

    Schedule schedule = Schedule.getInstance();
    ArrayList<MedicationEntry> medicationEntryArrayList = schedule.getMedication();

    public void dynamicDisplayMedication(View view) {
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
        listView.setOnItemClickListener((parent, view1, position, id) -> System.out.println(title.get(position)));
    }

    public void dynamicAddTime(AppCompatActivity aca, ArrayList<String> indexIn) {
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

    public ArrayList<String> getIndex() {
        ArrayList<String> index = new ArrayList<>();
        index.add("1");
        index.add("2");
        return index;
    }

}
