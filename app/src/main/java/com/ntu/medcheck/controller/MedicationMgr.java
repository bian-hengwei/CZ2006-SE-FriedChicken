package com.ntu.medcheck.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import com.ntu.medcheck.R;


public class MedicationMgr {

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // !!!!!!! on click, view in detail and can edit
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(title.get(position));
            }
        });
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
        ArrayList<String> Comment = new ArrayList<>();
        Comment.add("comment1");
        Comment.add("comment2");
        Comment.add("comment3");
        Comment.add("comment3");
        Comment.add("comment3");
        Comment.add("comment3");
        return Comment;
    }

    public ArrayList<String> getFrequence() {
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("1");
        frequency.add("2");
        frequency.add("3");
        frequency.add("2");
        frequency.add("1");
        frequency.add("2");
        return frequency;
    }

    public ArrayList<String> getDosage() {
        ArrayList<String> dosage = new ArrayList<>();
        dosage.add("1230");
        dosage.add("1330");
        dosage.add("1530");
        dosage.add("1530");
        dosage.add("1530");
        dosage.add("1530");
        return dosage;
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

    public ArrayList<String> getTitle() {
        ArrayList<String> title = new ArrayList<>();
        title.add("heart medicine");
        title.add("liver medicine");
        title.add("lung medicine");
        title.add("lung medicine");
        title.add("lung medicine");
        title.add("lung medicine");
        return title;
    }

}
