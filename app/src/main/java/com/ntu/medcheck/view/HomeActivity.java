package com.ntu.medcheck.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.CheckUpMgr;
import com.ntu.medcheck.controller.ScheduleMgr;
import com.ntu.medcheck.controller.UserProfileMgr;
import com.ntu.medcheck.model.User;
import com.ntu.medcheck.view.fragment.CalendarFragment;
import com.ntu.medcheck.view.fragment.CheckupFragment;
import com.ntu.medcheck.view.fragment.MedicationFragment;
import com.ntu.medcheck.view.fragment.UserHomeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * This activity calls different fragments like MedicineFragment, ScheduleFragment, MapFragment
 * It also displays the navigation bars
 */
public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private FrameLayout navFrame;
    private Fragment calendarFragment;
    private Fragment scheduleFragment;
    private Fragment userHomeFragment;
    private Fragment medicineFragment;
    private String lastFragment;
    FragmentTransaction fragmentTransaction;
    private boolean focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        focus = true;

        CheckUpMgr checkUpMgr = new CheckUpMgr();
        // for testing only
        checkUpMgr.save();

        UserProfileMgr userProfileMgr = new UserProfileMgr();
        userProfileMgr.initialize(this);

        ScheduleMgr scheduleMgr = new ScheduleMgr();
        scheduleMgr.initialize(this);

        navFrame = findViewById(R.id.navigationFrame);
        bottomNavigation = findViewById(R.id.bottomNavigationBar);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (calendarFragment == null) {
                Toast.makeText(this.getApplicationContext(), "please wait", Toast.LENGTH_SHORT).show();
                return false;
            }
            switch (item.getItemId()) {
                case R.id.navCalendar:
                    // set colour
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

                default:
                    return false;
            }
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

    private void setLastFragment() {
        Log.d("setLastFragments", "setLastFragments: start");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("setLastFragments", "setLastFragments: begin");
        Fragment showFragment = getLast();
        fragmentTransaction.show(showFragment);
        Log.d("setLastFragments", "setLastFragments: show");
        fragmentTransaction.commit();
    }

    public Fragment getLast() {
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
