package com.ntu.medcheck.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ntu.medcheck.model.CheckUpEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckUpMgr {

    private Map<String, ArrayList<CheckUpEntry>> entries;

    public CheckUpMgr() {
        entries = new HashMap<>();
    }

    public void init() {

        entries.put("202104", new ArrayList<CheckUpEntry>());
        entries.put("202105", new ArrayList<CheckUpEntry>());

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
        suRef.child("checkup").setValue(entries);
    }

}
