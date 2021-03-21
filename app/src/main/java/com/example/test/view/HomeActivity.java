package com.example.test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.test.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private FrameLayout navFrame;
    private CalendarFragment calendarFragment;
    private ScheduleFragment scheduleFragment;
    private UserHomeFragment userHomeFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navFrame = (FrameLayout) findViewById(R.id.navigationFrame);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);

        calendarFragment = new CalendarFragment();
        scheduleFragment = new ScheduleFragment();
        userHomeFragment = new UserHomeFragment();
        settingFragment = new SettingFragment();

        setFragment(calendarFragment);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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


                    case R.id.navSetting:
                        setFragment(settingFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) { // any fragment can be passed in
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.navigationFrame, fragment);
        fragmentTransaction.commit();
    }
}