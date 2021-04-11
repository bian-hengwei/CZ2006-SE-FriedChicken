package com.ntu.medcheck.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
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
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.utils.SafeItemOnClickListener;
import com.ntu.medcheck.view.EditCheckupActivity;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * CalendarManager class that
 * deals with all logic in CalendarFragment.
 * Implements OnNavigationButtonClickedListener from the calendar package
 * to override onNavigationButtonClicked and deal with on click of calendar elements.
 * @author Bian Hengwei
 */
public class CalendarMgr implements OnNavigationButtonClickedListener {

    /**
     * Log tag for debugging.
     */
    private static final String TAG = "CalendarManager";

    /**
     * Default constructor
     * called by CalendarFragment.
     */
    public CalendarMgr() {
        Log.d(TAG, "CalendarMgr: constructor called");
    }

    /**
     * Gets checkup for current month and
     * builds a map out of these checkups.
     * Builds the calendar with the map.
     * @param year Current year, used to find checkup array.
     * @param month Current month, used to find checkup array.
     * @param view View, used to find calendar and set calendar.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getMonth(int year, int month, View view) {
        /*
         * The map is in format {Date : view tag}
         * where view tag is what the special dates would look like.
         * In this app we only use "current" which is blue.
         */
        Log.d(TAG, "getMonth: Initializing calendar");
        Map<Integer, Object> map = new HashMap<>();
        String yearMonth = String.format("%04d%02d", year, month);
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            Log.d(TAG, "getMonth: Checkup found on " + yearMonth);
            map.put(Integer.parseInt((child.getTime().getDay())), "current");
        }
        // initialize the calendar
        Log.d(TAG, "getMonth: Setting up calendar");
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setDate(Calendar.getInstance(), map);
    }

    /**
     * Sets listeners for arrow buttons that changes month. 
     * @param view View, used to find calendar and set button listeners.
     */
    public void setListeners(View view) {
        Log.d(TAG, "setListeners: Setting listeners for calendar navigation buttons");
        CustomCalendar customCalendar = view.findViewById(R.id.custom_calendar);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);
    }

    /**
     * Overrides the interface method to set listener for navigation buttons.
     * On navigation button clicked
     * tries to build the target month map.
     * @param whichButton Previous or next button clicked. Not used.
     * @param newMonth Calendar object that gives the new year and month for building map.
     * @return Array of maps, where arr[0] is the same as getMonth.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        String yearMonth = String.format("%04d%02d", newMonth.get(Calendar.YEAR), newMonth.get(Calendar.MONTH)+1);
        Log.d(TAG, "onNavigationButtonClicked: Navigation button clicked");
        Log.d(TAG, "onNavigationButtonClicked: Initializing " + yearMonth);
        Map<Integer, Object>[] arr = new Map[2];
        arr[0] = new HashMap<>();
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        for (CheckUpEntry child : monthArray) {
            arr[0].put(Integer.parseInt((child.getTime().getDay())), "current");
        }
        return arr;
    }

    /**
     * Set onclick for calendar dates.
     * Gets all checkups on that date and display dynamically.
     * Set onclick of the checkup views to be EditCheckupActivity.
     * @param fragment Calendar Fragment, used to start activity.
     * @param view View, used to get items.
     * @param yearMonth Current year and month, used to get checkup array.
     * @param date Current date, used to get today's checkups.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDateOnClick(Fragment fragment, View view, String yearMonth, int date) {
        Log.d(TAG, "setDateOnClick: " + date + " is clicked");
        ArrayList<CheckUpEntry> monthArray = Schedule.getInstance().getCheckup().getOrDefault(yearMonth, new ArrayList<>());
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Calendar> time = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        ArrayList<CheckUpEntry> entries = new ArrayList<>();
        for (CheckUpEntry child : monthArray) {
            if (child.getTime().getDay().equals(String.format("%02d", date))) {
                title.add(child.getTitle());
                time.add(child.getTime().toCalendar());
                location.add(child.getClinic());
                comments.add(child.getComment());
                entries.add(child);
            }
        }
        ListView listView = view.findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(view.getContext(), title, location, time, comments);
        listView.setAdapter(adapter);
        // set onclick for subviews
        listView.setOnItemClickListener(new SafeItemOnClickListener() {
            @Override
            public void onOneClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onOneClick: clicked calendar checkup" + position);
                Intent i = new Intent(fragment.getActivity(), EditCheckupActivity.class);
                CheckUpEntry target = entries.remove(position);
                new CheckUpMgr().removeCheckUp(target);
                i.putExtra("name", target.getName());
                i.putExtra("comment", target.getComment());
                i.putExtra("title", target.getTitle());
                i.putExtra("clinic", target.getClinic());
                i.putExtra("day", target.getTime().getDay());
                i.putExtra("month", target.getTime().getMonth());
                i.putExtra("year", target.getTime().getYear());
                i.putExtra("minute", target.getTime().getMinute());
                i.putExtra("hour", target.getTime().getHour());
                fragment.startActivity(i);
            }
        });
    }

    /**
     * Private class used to draw the dynamic checkup displays.
     */
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> adapterTitle;
        ArrayList<String> adapterLocation;
        ArrayList<Calendar> adapterTime;
        ArrayList<String> adapterComment;

        /**
         * Constructor of MyAdapter.
         * @param context Context to initialize.
         * @param title Titles array.
         * @param location Locations array.
         * @param time Times array.
         * @param comments Comments array.
         */
        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> location, ArrayList<Calendar> time, ArrayList<String> comments) {
            super(context, R.layout.calendar_row, title);
            this.context = context;
            this.adapterTitle = title;
            this.adapterLocation = location;
            this.adapterTime = time;
            this.adapterComment = comments;
        }

        /**
         * Called to get the dynamic checkup view.
         * @param position Subview position.
         * @param convertView Convert view not used.
         * @param parent Parent view.
         * @return Checkup view that contains checkup details.
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View calendar_row = layoutInflater.inflate(R.layout.calendar_row, parent, false);
            TextView title = calendar_row.findViewById(R.id.title);
            TextView location = calendar_row.findViewById(R.id.location);
            TextView time = calendar_row.findViewById(R.id.time);
            TextView comments = calendar_row.findViewById(R.id.commentCheckupRow);

            String dateStr = adapterTime.get(position).get(Calendar.HOUR) + "(hr)" + adapterTime.get(position).get(Calendar.MINUTE) + "(min)";
            title.setText(adapterTitle.get(position));
            location.setText(calendar_row.getResources().getString(R.string.clinicName) + adapterLocation.get(position));
            time.setText(calendar_row.getResources().getString(R.string.eventTime) + dateStr);
            comments.setText(calendar_row.getResources().getString(R.string.Comment) + adapterComment.get(position));

            return calendar_row;
        }
    }
}
