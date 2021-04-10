package com.ntu.medcheck.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CalendarMgr;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);

        HashMap<Object, Property> descHashMap = new HashMap<>();

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        customCalendar.setMapDescToProp(descHashMap);

        Calendar calendar = Calendar.getInstance();

        CalendarMgr calendarMgr = new CalendarMgr();
        calendarMgr.getMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, view);

        calendarMgr.setListeners(view);

        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {
            String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)  + "/" +
                            (selectedDate.get(Calendar.MONTH) + 1)  + "/" +
                            selectedDate.get(Calendar.YEAR);

            String yearMonth = String.format("%04d%02d", selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH) + 1);

            TextView date = getActivity().findViewById(R.id.calendarDate);
            date.setText(sDate);

            calendarMgr.setDateOnClick(this, view, yearMonth, selectedDate.get(Calendar.DAY_OF_MONTH));
        });
        return view;
    }
}