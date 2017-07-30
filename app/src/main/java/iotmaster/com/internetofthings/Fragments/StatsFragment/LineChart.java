package iotmaster.com.internetofthings.Fragments.StatsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class LineChart extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_three, container, false);
        return v;
    }
}
