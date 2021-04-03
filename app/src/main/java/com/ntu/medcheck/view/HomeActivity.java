package com.ntu.medcheck.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntu.medcheck.R;
import com.ntu.medcheck.controller.ScheduleMgr;
import com.ntu.medcheck.controller.UserProfileMgr;
import com.ntu.medcheck.model.User;
import com.ntu.medcheck.view.fragment.CalendarFragment;
import com.ntu.medcheck.view.fragment.MedicationFragment;
import com.ntu.medcheck.view.fragment.CheckupFragment;
import com.ntu.medcheck.view.fragment.UserHomeFragment;

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
    private Fragment lastFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ScheduleMgr scheduleMgr = new ScheduleMgr();
        scheduleMgr.initialize();

        UserProfileMgr userProfileMgr = new UserProfileMgr();
        userProfileMgr.init("fuck", "123", "hbian001@e.ntu.edu.sg", "Male", "12345678");

        navFrame = findViewById(R.id.navigationFrame);
        bottomNavigation = findViewById(R.id.bottomNavigationBar);

        initFragments();
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
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

    private void initFragments() {
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
        lastFragment = calendarFragment;
        setFragment(calendarFragment);
    }

    private void setFragment(Fragment fragment) { // any fragment can be passed in
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(lastFragment);
        fragmentTransaction.show(fragment);
        lastFragment = fragment;
        fragmentTransaction.commit();
    }
}