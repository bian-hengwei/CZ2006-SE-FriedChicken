package com.ntu.medcheck.view;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntu.medcheck.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UserProfileMgr userProfileMgr = new UserProfileMgr();
        userProfileMgr.initialize(this);

        ScheduleMgr scheduleMgr = new ScheduleMgr();
        scheduleMgr.initialize();

        navFrame = findViewById(R.id.navigationFrame);
        bottomNavigation = findViewById(R.id.bottomNavigationBar);

        //initFragments();
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

    public void initFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        calendarFragment = new CalendarFragment();
        scheduleFragment = new CheckupFragment();
        userHomeFragment = new UserHomeFragment();
        medicineFragment = new MedicationFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
            lastFragment = "calendarFragment";
            setFragment(calendarFragment);
        }
        else {
            setLastFragment();
        }
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment hideFragment;
        switch (lastFragment) {
            case "userHomeFragment":
                hideFragment = userHomeFragment;
                break;
            case "scheduleFragment":
                hideFragment = scheduleFragment;
                break;
            case "medicineFragment":
                hideFragment = medicineFragment;
                break;
            default:
                hideFragment = calendarFragment;
        }
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
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment showFragment;
        switch (lastFragment) {
            case "userHomeFragment":
                showFragment = userHomeFragment;
                break;
            case "scheduleFragment":
                showFragment = scheduleFragment;
                break;
            case "medicineFragment":
                showFragment = medicineFragment;
                break;
            default:
                showFragment = calendarFragment;
        }
        fragmentTransaction.show(showFragment);
        fragmentTransaction.commit();
    }

}