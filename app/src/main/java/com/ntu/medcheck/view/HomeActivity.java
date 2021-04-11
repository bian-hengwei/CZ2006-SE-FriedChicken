package com.ntu.medcheck.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CheckUpMgr;
import com.ntu.medcheck.controller.MedicationMgr;
import com.ntu.medcheck.controller.ScheduleMgr;
import com.ntu.medcheck.controller.UserProfileMgr;
import com.ntu.medcheck.model.CheckUpEntry;
import com.ntu.medcheck.view.fragment.CalendarFragment;
import com.ntu.medcheck.view.fragment.CheckupFragment;
import com.ntu.medcheck.view.fragment.MedicationFragment;
import com.ntu.medcheck.view.fragment.UserHomeFragment;

import java.text.ParseException;

/**
 * This activity calls different fragments like MedicationFragment, CheckupFragment, CalendarFragment and UserHomeFragment
 * It also displays the navigation bars
 * @author Wang Xuege
 */
public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private FrameLayout navFrame;
    private Fragment calendarFragment;
    private Fragment scheduleFragment;
    private Fragment userHomeFragment;
    private Fragment medicineFragment;
    private String lastFragment;
    private FragmentTransaction fragmentTransaction;
    private boolean focus;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        focus = true;

        UserProfileMgr userProfileMgr = new UserProfileMgr();
        userProfileMgr.initialize(this);

        ScheduleMgr scheduleMgr = new ScheduleMgr();
        scheduleMgr.initialize(this);
        try {
            scheduleMgr.setNotifications();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CheckUpMgr.setContext(getApplicationContext());
        MedicationMgr.setContext(getApplicationContext());

        navFrame = findViewById(R.id.navigationFrame);
        bottomNavigation = findViewById(R.id.bottomNavigationBar);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (calendarFragment == null) {
                Toast.makeText(this.getApplicationContext(), "please wait", Toast.LENGTH_SHORT).show();
                return false;
            }
            switch (item.getItemId()) {
                case R.id.navCalendar:
                    setFragment(calendarFragment);
                    return true;

                case R.id.navSchedule:
                    setFragment(scheduleFragment);
                    return true;

                case R.id.navHome:
                    setFragment(userHomeFragment);
                    return true;

                case R.id.navMedicine:
                    setFragment(medicineFragment);
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        focus = true;
        initFragments();
    }

    @Override
    public void onPause() {
        super.onPause();
        focus = false;
    }

    /**
     * This method initializes the four fragments
     */
    public void initFragments() {
        Log.d("initFragments", "initFragments: start");
        if (!focus) {
            Log.d("initFragments", "initFragments: not focused");
            return;
        }

        if (calendarFragment != null)
            getSupportFragmentManager().beginTransaction().remove(calendarFragment).commit();
        if (scheduleFragment != null)
            getSupportFragmentManager().beginTransaction().remove(scheduleFragment).commit();
        if (userHomeFragment != null)
            getSupportFragmentManager().beginTransaction().remove(userHomeFragment).commit();
        if (medicineFragment != null)
            getSupportFragmentManager().beginTransaction().remove(medicineFragment).commit();

        Log.d("initFragments", "initFragments: new");
        calendarFragment = new CalendarFragment();
        scheduleFragment = new CheckupFragment();
        userHomeFragment = new UserHomeFragment();
        medicineFragment = new MedicationFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("initFragments", "initFragments: begin transaction");
        fragmentTransaction.add(R.id.navigationFrame, calendarFragment);
        fragmentTransaction.hide(calendarFragment);
        fragmentTransaction.add(R.id.navigationFrame, scheduleFragment);
        fragmentTransaction.hide(scheduleFragment);
        fragmentTransaction.add(R.id.navigationFrame, userHomeFragment);
        fragmentTransaction.hide(userHomeFragment);
        fragmentTransaction.add(R.id.navigationFrame, medicineFragment);
        fragmentTransaction.hide(medicineFragment);
        fragmentTransaction.commit();

        if (lastFragment == null) {
            Log.d("initFragments", "initFragments: null last fragment");
            lastFragment = "calendarFragment";
            setFragment(calendarFragment);
        }
        else {
            Log.d("initFragments", "initFragments: set last fragment");
            setLastFragment();
        }
    }

    /**
     * This method is called to set a fragment when a fragment is chosen on the bottom navigation bar
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        Log.d("setFragments", "setFragments: start");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("setFragments", "setFragments: begin");
        Fragment hideFragment = getLast();
        fragmentTransaction.hide(hideFragment);
        fragmentTransaction.show(fragment);
        if (userHomeFragment.equals(fragment)) {
            lastFragment = "userHomeFragment";
        } else if (scheduleFragment.equals(fragment)) {
            lastFragment = "scheduleFragment";
        } else if (medicineFragment.equals(fragment)) {
            lastFragment = "medicineFragment";
        } else {
            lastFragment = "calendarFragment";
        }
        fragmentTransaction.commit();
    }

    /**
     * This method is called to set the last fragment
     */
    private void setLastFragment() {
        Log.d("setLastFragments", "setLastFragments: start");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("setLastFragments", "setLastFragments: begin");
        Fragment showFragment = getLast();
        fragmentTransaction.show(showFragment);
        Log.d("setLastFragments", "setLastFragments: show");
        fragmentTransaction.commit();
    }

    /**
     * this method is called to get the last fragment
     * @return last fragment
     */
    private Fragment getLast() {
        Fragment last;
        switch (lastFragment) {
            case "userHomeFragment":
                last = userHomeFragment;
                break;
            case "scheduleFragment":
                last = scheduleFragment;
                break;
            case "medicineFragment":
                last = medicineFragment;
                break;
            default:
                last = calendarFragment;
        }
        return last;
    }

}
