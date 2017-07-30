package iotmaster.com.internetofthings.Fragments.StatsFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class PieFragment extends Fragment {

    int rainfall[] = {98, 10, 105, 144, 108, 108, 100, 7, 12, 14, 14, 189};
    String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chart_four, container, false);

        pieChart = (PieChart) v.findViewById(R.id.chart);
        pieChart.animateY(1000);
        pieChart.setDrawHoleEnabled(false);
        setupPie();
        return v;


    }

    private void setupPie() {

       /* List<PieEntry> list = new ArrayList<>();
        for (int i = 0; i < rainfall.length; i++) {
            list.add(new PieEntry(rainfall[i], month[i]));
        }
        PieDataSet dataset = new PieDataSet(list, "Rainfall India");

        PieData piedata = new PieData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(piedata);
        pieChart.invalidate();*/

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(8f, 0));
        yvalues.add(new Entry(15f, 1));
        yvalues.add(new Entry(12f, 2));
        yvalues.add(new Entry(25f, 3));
        yvalues.add(new Entry(23f, 4));
        yvalues.add(new Entry(17f, 5));
        yvalues.add(new Entry(47f, 6));
        yvalues.add(new Entry(20f, 7));
        yvalues.add(new Entry(78f, 8));
        yvalues.add(new Entry(1f, 9));
        yvalues.add(new Entry(25f, 10));
        yvalues.add(new Entry(9f, 11));


        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Jan");
        xVals.add("Feb");
        xVals.add("Mar");
        xVals.add("Apr");
        xVals.add("May");
        xVals.add("Jun");
        xVals.add("Jul");
        xVals.add("Aug");
        xVals.add("Sep");
        xVals.add("Oct");
        xVals.add("Nov");
        xVals.add("Dec");


        PieData data = new PieData(xVals, dataSet);
        // In Percentage
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("This is Pie Chart");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);

        data.setValueTextSize(15f);
        data.setValueTextColor(Color.DKGRAY);

        pieChart.setHoleRadius(25f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
