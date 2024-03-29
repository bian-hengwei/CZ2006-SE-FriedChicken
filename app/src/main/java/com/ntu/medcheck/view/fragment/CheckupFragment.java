package com.ntu.medcheck.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CheckUpMgr;
import com.ntu.medcheck.utils.SafeOnClickListener;
import com.ntu.medcheck.view.AddCheckupActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckupFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Yin Jiarui
 */
public class CheckupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CheckUpMgr checkUpMgr = new CheckUpMgr();

    public CheckupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckupFragment newInstance(String param1, String param2) {
        CheckupFragment fragment = new CheckupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checkup, container, false);
        checkUpMgr.dynamicDisplayCheckup(this, view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton addNewCheckup = view.findViewById(R.id.addNewCheckup);
        addNewCheckup.setOnClickListener(new SafeOnClickListener() {
            @Override
            public void onOneClick(View v) {
                Intent i = new Intent(CheckupFragment.this.getActivity(), AddCheckupActivity.class);
                startActivity(i);
            }
        });
    }
}