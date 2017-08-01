package iotmaster.com.internetofthings.Fragments.StatsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class LineChart extends Fragment {

    GraphView graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_three, container, false);
        graph = (GraphView) view.findViewById(R.id.graph);
        makeLineChart();
        return view;

    }

    public void makeLineChart() {


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 1),
                new DataPoint(6, 25)

        });
        graph.addSeries(series);

    }
}
