package com.ntu.medcheck.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CalendarMgr;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    ListView listView;

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

        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default", defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("present", presentProperty);

        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("absent", absentProperty);

        customCalendar.setMapDescToProp(descHashMap);

        Calendar calendar = Calendar.getInstance();

        CalendarMgr calendarMgr = new CalendarMgr();
        calendarMgr.getMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, view);

        calendarMgr.setListeners(view);

        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {
            String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                    + "/" + (selectedDate.get(Calendar.MONTH) + 1)
                    + "/" + selectedDate.get(Calendar.YEAR);

            TextView date = getActivity().findViewById(R.id.calendarDate);
            date.setText(sDate);

//////////////////////////START/////////////////////////////////////////////////////////////////////

            // dynamic reminder
            ArrayList<String> title = new ArrayList<>();
            title.add("heart checkup");
            title.add("liver checkup");
            title.add("lung checkup");


            ArrayList<Calendar> time = new ArrayList<>();
            Calendar checkupTime1 = Calendar.getInstance();
            Calendar checkupTime2 = Calendar.getInstance();
            Calendar checkupTime3 = Calendar.getInstance();
            checkupTime1.set(Calendar.HOUR, 3);
            checkupTime1.set(Calendar.MINUTE, 4);
            checkupTime2.set(Calendar.HOUR, 1);
            checkupTime2.set(Calendar.MINUTE, 2);
            checkupTime3.set(Calendar.HOUR, 5);
            checkupTime3.set(Calendar.MINUTE, 6);
            time.add(checkupTime1);
            time.add(checkupTime2);
            time.add(checkupTime3);

            ArrayList<String> location = new ArrayList<>();
            location.add("hospital 1");
            location.add("hospital 2");
            location.add("hospital 3");

            ArrayList<String> comments = new ArrayList<>();
            comments.add("helloworld1");
            comments.add("helloworld2");
            comments.add("helloworld3");

            dynamicReminder(title, location, time, comments);

            //System.out.println(checkupTime1.get(Calendar.HOUR));
            //System.out.println(checkupTime2.get(Calendar.HOUR));

            listView = view.findViewById(R.id.listView);
            MyAdapter adapter = new MyAdapter(this.getContext(), title, location, time, comments);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(title.get(position));
                }
            });
/////////////////////////END////////////////////////////////////////////////////////////////////////
        });
        // Inflate the layout for this fragment
        return view;
    }

    // pass in list of title (event eg.heart checkup), list of time, list of comment. get this list from ScheduleMgr.
    public void dynamicReminder(ArrayList<String> title, ArrayList<String> location, ArrayList<Calendar> time, ArrayList<String> comments) {

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> atitle;
        ArrayList<String> alocation;
        ArrayList<Calendar> atime;
        ArrayList<String> acomments;
        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> location, ArrayList<Calendar> time, ArrayList<String> comments) {
            super(context, R.layout.calendar_row, title);
            this.context = context;
            this.atitle = title;
            this.alocation = location;
            this.atime = time;
            this.acomments = comments;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View calendar_row = layoutInflater.inflate(R.layout.calendar_row, parent, false);
            TextView title = calendar_row.findViewById(R.id.title);
            TextView location = calendar_row.findViewById(R.id.location);
            TextView time = calendar_row.findViewById(R.id.time);
            TextView comments = calendar_row.findViewById(R.id.commentCheckupRow);

            String dateStr = atime.get(position).get(Calendar.HOUR) + "(hr)" + atime.get(position).get(Calendar.MINUTE) + "(min)";

            title.setText(atitle.get(position));
            location.setText("Location: " + alocation.get(position));
            time.setText("Time: " + dateStr);
            comments.setText("Comments: " + acomments.get(position));

            return calendar_row;
        }
    }
}