package com.ntu.medcheck.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements OnNavigationButtonClickedListener{

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toast.makeText(this.getContext(), getString(R.string.testw_str),Toast.LENGTH_SHORT).show();



        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);

        if(customCalendar == null) {
            System.out.println("yes");
        }
        else{
            System.out.println("NOOOOOOOOOOO");
        }





        HashMap<Object, Property> descHashMap = new HashMap<Object, Property>();


        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default", defaultProperty);

        System.out.println("1");

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        System.out.println("2");

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("present", presentProperty);

        System.out.println("3");

        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("absent", absentProperty);

        System.out.println("4");



        System.out.println(descHashMap.get("absent").toString());
        System.out.println(descHashMap.get("present").toString());

        System.out.println(R.id.custom_calendar);

        if(view.findViewById(R.id.custom_calendar) == null) {
            System.out.println("yes");
        }

        customCalendar.setMapDescToProp(descHashMap);

        System.out.println("5");

        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();

        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");
        dateHashMap.put(1, "present");
        dateHashMap.put(2, "absent");
        dateHashMap.put(3, "present");
        dateHashMap.put(4, "absent");
        dateHashMap.put(20, "present");
        dateHashMap.put(30, "absent");

        customCalendar.setDate(calendar, dateHashMap);
/*
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, new OnNavigationButtonClickedListener() {
            @Override
            public Map[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
                Map<Integer, Object>[] arr = new Map[2];
                switch(newMonth.get(Calendar.MONTH)) {
                    case Calendar.AUGUST:
                        arr[0] = new HashMap<>(); //This is the map linking a date to its description
                        arr[0].put(3, "absent");
                        arr[0].put(6, "present");
                        arr[0].put(21, "absent");
                        arr[0].put(24, "present");
                        arr[1] = null; //Optional: This is the map linking a date to its tag.
                        break;
                    case Calendar.JUNE:
                        arr[0] = new HashMap<>();
                        arr[0].put(5, "present");
                        arr[0].put(10, "present");
                        arr[0].put(19, "present");
                        break;
                }


                return arr;
            }
        });
        //customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, (OnNavigationButtonClickedListener) this);

        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {


            customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                    String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                            + "/" + (selectedDate.get(Calendar.MONTH) + 1)
                            + "/" + selectedDate.get(Calendar.YEAR);
                    TextView date = view.findViewById(R.id.calendarDate);
                    date.setText(sDate);

                    if(view.findViewById(R.id.calendarDate) == null) {
                        System.out.println("yes");
                    }
                    else{
                        System.out.println("NOOOOOOOOOOO");
                    }
                }
            });*/////////////////////////////////////////////////////////

            /*
            // get string date
            String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                    + "/" + (selectedDate.get(Calendar.MONTH) + 1)
                    + "/" + selectedDate.get(Calendar.YEAR);

            TextView date = view.findViewById(R.id.calendarDate);
            date.setText(sDate);

            if(view.findViewById(R.id.calendarDate) == null) {
                System.out.println("yes");
            }
            else{
                System.out.println("NOOOOOOOOOOO");
            }
        });  */


/*
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, (whichButton, newMonth) -> {
            System.out.println("1a");
            Map<Integer, Object>[] arr = new Map[2];
            switch(newMonth.get(Calendar.MONTH)) {
                default:
                    arr[0] = new HashMap<>();

            }

        });  */

        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);

        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);

        customCalendar.setDate(calendar, dateHashMap);



        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {
            // get string date
            if(selectedDate == null) {
                System.out.println("is nulllll");
            }
            else {
                System.out.println("is notttttttt");
            }

            String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                    + "/" + (selectedDate.get(Calendar.MONTH) + 1)
                    + "/" + selectedDate.get(Calendar.YEAR);

            TextView date = getActivity().findViewById(R.id.calendarDate);
            date.setText(sDate);
        });




        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr = new Map[2];
        switch(newMonth.get(Calendar.MONTH)) {
            case Calendar.AUGUST:
                arr[0] = new HashMap<>(); //This is the map linking a date to its description
                arr[0].put(3, "absent");
                arr[0].put(6, "absent");
                arr[0].put(21, "absent");
                arr[0].put(24, "absent");
                arr[1] = null; //Optional: This is the map linking a date to its tag.
                break;
            default:
                arr[0] = new HashMap<>();
                arr[0].put(5, "absent");
                arr[0].put(10, "absent");
                arr[0].put(19, "absent");
                break;
        }
        return arr;
    }
}