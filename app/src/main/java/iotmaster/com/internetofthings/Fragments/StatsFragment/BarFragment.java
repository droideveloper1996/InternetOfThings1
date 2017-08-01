package iotmaster.com.internetofthings.Fragments.StatsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class BarFragment extends Fragment {
    BarChart chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_one, container, false);
        chart = (BarChart) view.findViewById(R.id.bar);

        createBarChart();
        return view;
    }

    void createBarChart() {
        ArrayList<BarEntry> arrayList = new ArrayList<>();
        arrayList.add(new BarEntry(44f, 0));
        arrayList.add(new BarEntry(90f, 1));
        arrayList.add(new BarEntry(86f, 2));
        arrayList.add(new BarEntry(41f, 3));
        arrayList.add(new BarEntry(10f, 4));
        arrayList.add(new BarEntry(44f, 5));

        BarDataSet barDataSet = new BarDataSet(arrayList, "Daily Stats");

        ArrayList<String> month = new ArrayList<>();
        month.add("Jan");
        month.add("Feb");
        month.add("Mar");
        month.add("Apr");
        month.add("May");
        month.add("Jun");

       // BarDataSet barDataSet1=new BarDataSet(month);
        BarData barData = new BarData(month, barDataSet);

        chart.setData(barData);
        chart.setTouchEnabled(true);
        chart.animateX(1000);
        chart.setDrawBarShadow(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.animateY(2000);




    }
}
