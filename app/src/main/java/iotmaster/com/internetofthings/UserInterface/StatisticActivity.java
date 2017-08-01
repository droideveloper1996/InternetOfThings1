package iotmaster.com.internetofthings.UserInterface;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iotmaster.com.internetofthings.Fragments.StatsFragment.BarFragment;
import iotmaster.com.internetofthings.Fragments.StatsFragment.LineChart;
import iotmaster.com.internetofthings.Fragments.StatsFragment.PieFragment;
import iotmaster.com.internetofthings.R;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout gt = (TabLayout) findViewById(R.id.tablayout);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        gt.setupWithViewPager(viewPager);

    }


    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BarFragment();
                case 1:
                    return new LineChart();
                case 2:
                    return new PieFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Bar";

                case 1:
                    return "Line";
                case 2:
                    return "Pie";
            }
            return null;
        }
    }
}





