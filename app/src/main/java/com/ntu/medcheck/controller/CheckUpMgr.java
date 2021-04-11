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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ntu.medcheck.R;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.model.Schedule;
import com.ntu.medcheck.model.Time;
import com.ntu.medcheck.utils.SafeItemOnClickListener;
import com.ntu.medcheck.view.EditCheckupActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * CheckUpManager class deals with all issues of adding,
 * editing, and deleting checkups.
 * Used by CheckupFragment, AddCheckupActivity, EditCheckupActivity.
 */
public class CheckUpMgr {

    /**
     * Log tag for debugging.
     */
    private static final String TAG = "CheckUpManager";

    /**
     * Static context object initialized on HomeActivity onCreate.
     * Used for setting and cancelling notifications.
     */
    private static Context context;

    /**
     * Default constructor.
     */
    public CheckUpMgr() {
    }

    /**
     * Setter for static context called by HomeActivity.
     * @param context HomeActivity context.
     */
    public static void setContext(Context context) {
        Log.d(TAG, "setContext: context set successfully by HomeActivity");
        CheckUpMgr.context = context;
    }

    /**
     * Called when setting or cancelling notifications.
     * @return HomeActivity context.
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Displays checkups in the checkup page.
     * Called by CheckupFragment.
     * Displays all upcoming checkups and set onclick for them.
     * @param fragment Fragment, used to set onclick.
     * @param view View, used to find listView object.
     */
    public void dynamicDisplayCheckup(Fragment fragment, View view) {
        Log.d(TAG, "dynamicDisplayCheckup: trying to display all upcoming checkups");
        ListView listView;
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        ArrayList<String> comment = new ArrayList<>();
        ArrayList<CheckUpEntry> entries = new ArrayList<>();

        // draw checkups
        listView = view.findViewById(R.id.checkupListView);
        Map<String, ArrayList<CheckUpEntry>> checkup = Schedule.getInstance().getCheckup();
        ArrayList<String> sortedKeys = new ArrayList<>(checkup.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            ArrayList<CheckUpEntry> arr = checkup.get(key);
            // sort array to make all checkups display in a correct order
            Collections.sort(arr, (o1, o2) -> {
                if (o1.getTime().toCalendar().after(o2.getTime().toCalendar())) {
                    return 1;
                }
                return -1;
            });
            for (CheckUpEntry entry : arr) {
                if (entry.getTime().toCalendar().after(Calendar.getInstance())) {
                    title.add(entry.getTitle());
                    date.add(entry.getTime().getYear() + entry.getTime().getMonth() + entry.getTime().getDay());
                    time.add(entry.getTime().getHour() + entry.getTime().getMinute());
                    location.add(entry.getClinic());
                    comment.add(entry.getComment());
                    entries.add(entry);
                }
            }
        }

        // set onclick for checkups
        MyAdapter adapter = new MyAdapter(view.getContext(), title, date, time, location, comment);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new SafeItemOnClickListener() {
            @Override
            public void onOneClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                 * Once clicked any position
                 * delete the clicked checkup and go to EditCheckupActivity
                 */
                Log.d(TAG, "onOneClick: " + position + " item clicked");
                Intent i = new Intent(fragment.getActivity(), EditCheckupActivity.class);
                CheckUpEntry target = entries.remove(position);
                removeCheckUp(target);
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
     * Remove given checkup and cancel notification for that checkup.
     * @param checkup Checkup to be removed.
     */
    public void removeCheckUp(CheckUpEntry checkup) {
        Log.d(TAG, "removeCheckUp: " + checkup.getName());
        Schedule.getInstance().remove(checkup);
        new NotificationScheduler().cancelNotification(context, checkup.getTime().getId());
        new ScheduleMgr().save();
    }

    /**
     * Displays EditCheckupActivity according to intent that is passed in.
     * @param aca EditCheckupActivity.
     * @param intent Intent containing information.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void display(AppCompatActivity aca, Intent intent) {
        Log.d(TAG, "display: adding checkup information to EditCheckupActivity");
        String name = intent.getStringExtra("name");
        String comment = intent.getStringExtra("comment");
        String title = intent.getStringExtra("title");
        String clinic = intent.getStringExtra("clinic");
        String day = intent.getStringExtra("day");
        String month = intent.getStringExtra("month");
        String year = intent.getStringExtra("year");
        String minute = intent.getStringExtra("minute");
        String hour = intent.getStringExtra("hour");
        TextView checkUpType = aca.findViewById(R.id.confirmed_checkup_type);
        checkUpType.setText(title);
        TextView clinicView = aca.findViewById(R.id.confirmed_clinic_name);
        clinicView.setText(clinic);
        EditText commentBox = aca.findViewById(R.id.commentBox);
        commentBox.setText(comment);
        DatePicker date = aca.findViewById(R.id.datePickerAddCheckup);
        date.init(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), null);
        TimePicker time = aca.findViewById(R.id.timePickerAddCheckup);
        time.setHour(Integer.parseInt(hour));
        time.setMinute(Integer.parseInt(minute));
    }

    /**
     * Try to add checkup to schedule and set notification for it.
     * @param aca Activity that calls addCheckUp.
     * @return If successful.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean addCheckUp(AppCompatActivity aca) {
        Log.d(TAG, "addCheckUp: starting add checkup");
        TextView type = aca.findViewById(R.id.confirmed_checkup_type);
        TextView clinic = aca.findViewById(R.id.confirmed_clinic_name);
        DatePicker date = aca.findViewById(R.id.datePickerAddCheckup);
        TimePicker time = aca.findViewById(R.id.timePickerAddCheckup);
        TextView comment = aca.findViewById(R.id.commentBox);
        type.getText().toString();
        if (type.getText().toString().isEmpty() || clinic.getText().toString().isEmpty()) {
            Log.d(TAG, "addCheckUp: empty field found, add checkup failed");
            Toast.makeText(aca, R.string.AddCheckupEmptyType, Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.d(TAG, "addCheckUp: no empty field found");
        CheckUpEntry checkup = new CheckUpEntry();
        checkup.setName(type.getText().toString());
        checkup.setType("checkup");
        checkup.setComment(comment.getText().toString());
        checkup.setTitle(type.getText().toString());
        checkup.setClinic(clinic.getText().toString());
        Time t = new Time();
        int day = date.getDayOfMonth();
        int month = date.getMonth() + 1;
        int year = date.getYear();
        t.setTime(String.format("%04d%02d%02d", year, month, day));
        int minute = time.getMinute();
        int hour = time.getHour();
        t.setMinute(String.format("%02d", minute));
        t.setHour(String.format("%02d", hour));
        checkup.setTime(t);

        Schedule schedule = Schedule.getInstance();
        if (schedule.getCheckup().containsKey(String.format("%02d%02d", year, month))) {
            schedule.getCheckup().get(String.format("%02d%02d", year, month)).add(checkup);
        }
        else {
            ArrayList<CheckUpEntry> arr = new ArrayList<>();
            arr.add(checkup);
            schedule.getCheckup().put(String.format("%02d%02d", year, month), arr);
        }

        // set a random id to this new checkup
        // the random id is used to book notification
        int id = new Random().nextInt(100000);
        checkup.getTime().setId(id);
        setNotification(checkup);

        new ScheduleMgr().save();
        Log.d(TAG, "addCheckUp: successful");
        return true;
    }

    /**
     * Sets notification for a checkup.
     * @param checkup Checkup to set notification.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setNotification(CheckUpEntry checkup) {
        Log.d(TAG, "setNotification: setting notification");
        // Format: dd-M-yyyy hh:mm:ss
        int day = Integer.parseInt(checkup.getTime().getDay());
        int month = Integer.parseInt(checkup.getTime().getMonth());
        int year = Integer.parseInt(checkup.getTime().getYear());
        String notificationDay = String.format("%d/%d/%d", day, month, year);
        int hour = Integer.parseInt(checkup.getTime().getHour());
        int minute = Integer.parseInt(checkup.getTime().getMinute());
        String notificationTime = String.format("%d:%d", hour, minute);
        String content = notificationTime;
        String name = checkup.getName();
        String clinic = checkup.getClinic();
        String comment = checkup.getComment();
        String title = name + " at " + clinic + ", " + notificationDay;
        content += " " + comment;

        //dd-M-yyyy hh:mm:ss   get time in millisecond
        String dateString = String.format("%02d-%d-%04d %02d:%02d:%02d", day, month, year, hour, minute, 0);
        Log.d("DateString", "setNotification: " + dateString);
        long milliSecond = getMillisecond(dateString);
        Log.d("Millisecond", "setNotification: " + milliSecond);

        int id = checkup.getTime().getId();
        // sets notification using context, id, and texts
        NotificationScheduler notificationScheduler = new NotificationScheduler();
        Notification notification = notificationScheduler.getNotification(content, title , context, id);
        notificationScheduler.scheduleNotification(notification, milliSecond, context, false, id);
    }

    /**
     * Gets millisecond from dateString to now.
     * @param dateString DateString containing time to compute time difference.
     * @return Milliseconds of time difference.
     */
    public long getMillisecond(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(dateString);
            long future = date.getTime();
            Date now = new Date();
            long current = now.getTime();
            Log.d(TAG, "getMillisecond: given time in ms " + future);
            Log.d(TAG, "getMillisecond: current time in ms" + current);
            long time = future - current;
            return time;
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Private class used to draw the dynamic checkup displays.
     */
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> adapterTitle;
        ArrayList<String> adapterDate;
        ArrayList<String> adapterTime;
        ArrayList<String> adapterLocation;
        ArrayList<String> adapterComment;

        /**
         * Constructor of MyAdapter.
         * @param context Context to initialize.
         * @param title Titles array.
         * @param date Dates array.
         * @param time Times array.
         * @param location Locations array.
         * @param comments Comments array.
         */
        MyAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> time, ArrayList<String> location, ArrayList<String> comments) {
            super(context, R.layout.checkup_row, title);
            this.context = context;
            this.adapterTitle = title;
            this.adapterDate = date;
            this.adapterTime = time;
            this.adapterLocation = location;
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
            View checkup_row = layoutInflater.inflate(R.layout.checkup_row, parent, false);
            TextView title = checkup_row.findViewById(R.id.titleCheckupRow);
            TextView date = checkup_row.findViewById(R.id.dateCheckupRow);
            TextView time = checkup_row.findViewById(R.id.timeCheckupRow);
            TextView location = checkup_row.findViewById(R.id.locationCheckupRow);
            TextView comments = checkup_row.findViewById(R.id.commentCheckupRow);

            title.setText(adapterTitle.get(position));
            date.setText(checkup_row.getResources().getString(R.string.eventDate) + adapterDate.get(position).substring(6, 8) + " / " + adapterDate.get(position).substring(4, 6) + " / " + adapterDate.get(position).substring(0, 4));
            time.setText(checkup_row.getResources().getString(R.string.eventTime) + adapterTime.get(position).substring(0, 2) + " : " + adapterTime.get(position).substring(2, 4));
            location.setText(checkup_row.getResources().getString(R.string.clinicName) + adapterLocation.get(position));
            comments.setText(checkup_row.getResources().getString(R.string.Comment) + adapterComment.get(position));

            return checkup_row;
        }
    }
}
