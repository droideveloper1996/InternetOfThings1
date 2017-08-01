package iotmaster.com.internetofthings.UserInterface;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import iotmaster.com.internetofthings.Fragments.fragment_four;
import iotmaster.com.internetofthings.Fragments.fragment_one;
import iotmaster.com.internetofthings.Fragments.fragment_three;
import iotmaster.com.internetofthings.Fragments.fragment_two;
import iotmaster.com.internetofthings.R;

public class AlarmActivity extends AppCompatActivity {

    public static final String TAG = "AlarmActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_alarm_add_white_24dp,
            R.drawable.ic_alarm_white_24dp,
            R.drawable.ic_alarm_on_white_24dp,
            R.drawable.ic_alarm_off_white_24dp

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new fragment_one();
                case 1:
                    return new fragment_two();
                case 2:
                    return new fragment_three();
                case 3:
                    return new fragment_four();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add";
                case 1:
                    return "Two";
                case 2:
                    return "Three";
                case 3:
                    return "Delete";
            }
            return null;
        }
    }

}