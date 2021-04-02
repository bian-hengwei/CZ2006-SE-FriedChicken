package com.ntu.medcheck.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ntu.medcheck.R;
import com.ntu.medcheck.view.fragment.*;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navFrame = (FrameLayout) findViewById(R.id.navigationFrame);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);

        calendarFragment = new CalendarFragment();
        scheduleFragment = new ScheduleFragment();
        userHomeFragment = new UserHomeFragment();
        medicineFragment = new MedicineFragment();

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


                    case R.id.navMedicine:
                        setFragment(medicineFragment);
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