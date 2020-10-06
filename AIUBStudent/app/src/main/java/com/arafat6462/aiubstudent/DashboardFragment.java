package com.arafat6462.aiubstudent;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    ImageView calculateCgpaButton;
    // for progressBar
    private ProgressBar progressBarBlue, progressBarGreen;
    ObjectAnimator progressAnimatorBlue, progressAnimatorGreen;

    // for radar chart
    private Button RadarButtonCredit, RadarButtonCgpa;
    private boolean RadarButtonCreditBoolean = false, RadarButtonCgpaBoolean = true;
    public static final float MAX_CREDIT = 20, MIN_CREDIT = 0, MAX_CGPA = 4, MIN_CGPA = 0;
    public static final int CreditStepMinToMax = 8, CgpaStepMinToMax = 5;
    private RadarChart chart;
    public TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ////////////////////      radarChart init     /////////////////////
        chart = view.findViewById(R.id.chart);
        RadarButtonCgpa = view.findViewById(R.id.RadarButtonCgpa);
        RadarButtonCredit = view.findViewById(R.id.RadarButtonCredit);

/////////////// progress bar /////////////
        double progressBarBlueProgress = 80 * 100 / 148, progressBarGreenProgress = 3.16 * 100 / 4; // set this value as credit and cgpa

        progressBarBlue = view.findViewById(R.id.progressBarBlue);
        progressBarGreen = view.findViewById(R.id.progressBarGreen);

        progressBarBlue.setMax(100 * 100);
        progressBarGreen.setMax(100 * 100);
        progressAnimatorBlue = ObjectAnimator.ofInt(progressBarBlue, "progress", progressBarBlue.getProgress(), (int) (progressBarBlueProgress * 100));
        progressAnimatorGreen = ObjectAnimator.ofInt(progressBarGreen, "progress", progressBarGreen.getProgress(), (int) (progressBarGreenProgress * 100));
        progressAnimatorBlue.setDuration(1500);
        progressAnimatorGreen.setDuration(1500);
        progressAnimatorBlue.start();
        progressAnimatorGreen.start();
/////////////// progress bar end /////////////

        // we configure the radar chart
        //   chart.setBackgroundColor(Color.rgb(96,125,140)); //background
        chart.setBackgroundResource(R.drawable.radarchart_background_color); // set background from drawable instead of direct set
        chart.getDescription().setEnabled(false); // below title description
        chart.setWebLineWidth(1f); // main border line
        // useful to export your graph
        chart.setWebColor(Color.BLACK); // main border color
        chart.setWebLineWidth(1f); //border width
        chart.setWebColorInner(Color.BLACK); //circular net or sub border color
        chart.setWebAlpha(100); // main border appacity


        radarChartButtonCheck();
        radarChartSetData();
        if (RadarButtonCgpaBoolean) {
            radarChartPostCgpa();
        }
        if (RadarButtonCreditBoolean) {
            radarChartPostCredit();
        }


        ////////////////////      radarChart     /////////////////////


        ///////////////////////////// calculate cgpa //////////////
        calculateCgpaButton = view.findViewById(R.id.calculateCgpaButtonImageView);
        calculateCgpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changing the fragment
                ResultCalculationFragment resultCalculationFragment = new ResultCalculationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,resultCalculationFragment,"findThisFragment").addToBackStack(null).commit();

                // this will also work for changing fragment
 //               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, new ResultCalculationFragment());
//                fragmentTransaction.commit();


            }
        });
/////////////////////////////  calculate cgpa //////////////

        return view;
    }


    private void radarChartButtonCheck() {

        RadarButtonCgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadarButtonCgpaBoolean = true;
                RadarButtonCreditBoolean = false;
                RadarButtonCgpa.setBackgroundResource(R.drawable.radarchart_button_pressed);
                RadarButtonCredit.setBackgroundResource(R.drawable.radarchart_button_default);
                radarChartPostCgpa();
                radarChartSetData();


            }
        });

        RadarButtonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadarButtonCreditBoolean = true;
                RadarButtonCgpaBoolean = false;
                RadarButtonCredit.setBackgroundResource(R.drawable.radarchart_button_pressed);
                RadarButtonCgpa.setBackgroundResource(R.drawable.radarchart_button_default);
                radarChartPostCredit();
                radarChartSetData();

            }
        });
    }

    private void radarChartSetData() {


        ArrayList<RadarEntry> AllSemesterCGPA = new ArrayList<>();
        ArrayList<RadarEntry> AllSemesterCREDIT = new ArrayList<>();

        // we generate random values for the qualities of employees measured between 1 and 2.
//        for (int i=0; i<NB_QUALITIES; i++){
//
//            float val1 = 3.8f;   //(int)(Math.random()*MAX)+MIN;
//            employee1.add(new RadarEntry(val1));
//
//            float val2 = 3.01f;  //(int)(Math.random()*MAX)+MIN;
//            employee2.add(new RadarEntry(val2));
//        }

        //added later instead of above for loop random generator
        AllSemesterCGPA.add(new RadarEntry(3.01f));
        AllSemesterCGPA.add(new RadarEntry(3.2f));
        AllSemesterCGPA.add(new RadarEntry(2.5f));
        AllSemesterCGPA.add(new RadarEntry(3.81f));
        AllSemesterCGPA.add(new RadarEntry(3.41f));


        AllSemesterCREDIT.add(new RadarEntry(13));
        AllSemesterCREDIT.add(new RadarEntry(14));
        AllSemesterCREDIT.add(new RadarEntry(12));
        AllSemesterCREDIT.add(new RadarEntry(15));
        AllSemesterCREDIT.add(new RadarEntry(16));


        // we create two radar data sets objects with these data
        RadarDataSet set1 = new RadarDataSet(AllSemesterCGPA, "All semester CGPA");
        set1.setColor(Color.argb(255, 0, 220, 80));// category one border color and fill color
        set1.setFillColor(Color.argb(100, 0, 200, 100));
        set1.setDrawFilled(true); // fill the circle of radar
        set1.setFillAlpha(100); // fill  alpha between 255
        set1.setLineWidth(2f);  // border line width
        set1.setDrawHighlightIndicators(true); // mark in x,y axis when touch a point
        set1.setDrawHighlightCircleEnabled(true);


        RadarDataSet set2 = new RadarDataSet(AllSemesterCREDIT, "All semester CREDIT");
        set2.setColor(Color.argb(255, 40, 160, 220));
        set2.setFillColor(Color.argb(100, 40, 160, 220));
        set2.setDrawFilled(true);
        set2.setFillAlpha(100);
        set2.setLineWidth(2f);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawHighlightCircleEnabled(true);

        ArrayList<RadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        // we create Radar Data object which will be added to the radar chart for rendering
        // load radar graph based on user choice
        RadarData data = new RadarData();
        if (RadarButtonCgpaBoolean) {
            data.addDataSet(set1);
        }
        if (RadarButtonCreditBoolean) {
            data.addDataSet(set2);
        }


        data.setValueTextSize(10f);
        data.setDrawValues(true); // decide toggle value hide or show
        data.setValueTextColor(Color.argb(255, 0, 25, 40)); // toggle value text color

        chart.setData(data);
        chart.invalidate();

    }


    private void radarChartPostCgpa() {

        // animate the chart when first shown.
        chart.animateXY(1200, 2200, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        // we define axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            // we will compare two employees on a radar chart
            // so we define qualities to compare
            // you can added 15,20 semester and code will automatic keep value from beginning
            private String[] qualities = new String[]{"semester-1", "semester-2", "semester-3", " semester-4", "semester-5", "semester-6", "semester-7",
                    "semester-8", " semester-9", "semester-10", "semester-11", "semester-12", "semester-13", " semester-14", "semester-15", "semester-16",
                    "semester-17", "semester-18", " semester-19", "semester-20", "semester-21", "semester-22", "semester-23", " semester-24", "semester-25",
                    "semester-26", "semester-27", "semester-28", " semester-29", "semester-30"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return qualities[(int) value % qualities.length];
            }
        });

        xAxis.setTextColor(Color.GRAY); // axis text color like "2017-2018,summer"

        // Y axis
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(CgpaStepMinToMax, false);
        yAxis.setTextSize(10f);
        yAxis.setAxisMinimum(MIN_CGPA);// we define min and max for axis
        yAxis.setAxisMaximum(MAX_CGPA);
        yAxis.setDrawLabels(true); //marking on the main border like cgpa 2.5, 3, 3.5, 4

        // we configure legend for our radar chart
        Legend l = chart.getLegend();  // indicator sign text like "red for credit" and "green for cgpa"
        l.setTextSize(8);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true); //will increase radar space
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

    }


    private void radarChartPostCredit() {

        // animate the chart when first shown.
        chart.animateXY(1200, 2200, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        // we define axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            // we will compare two employees on a radar chart
            // so we define qualities to compare
            // you can added 15,20 semester and code will automatic keep value from beginning
            private String[] qualities = new String[]{"semester-1", "semester-2", "semester-3", " semester-4", "semester-5", "semester-6", "semester-7",
                    "semester-8", " semester-9", "semester-10", "semester-11", "semester-12", "semester-13", " semester-14", "semester-15", "semester-16",
                    "semester-17", "semester-18", " semester-19", "semester-20", "semester-21", "semester-22", "semester-23", " semester-24", "semester-25",
                    "semester-26", "semester-27", "semester-28", " semester-29", "semester-30"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return qualities[(int) value % qualities.length];
            }
        });

        xAxis.setTextColor(Color.GRAY); // axis text color like "2017-2018,summer"

        // Y axis
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(CreditStepMinToMax, false);
        yAxis.setTextSize(10f);
        yAxis.setAxisMinimum(MIN_CREDIT);// we define min and max for axis
        yAxis.setAxisMaximum(MAX_CREDIT);
        yAxis.setDrawLabels(true); //marking on the main border like cgpa 2.5, 3, 3.5, 4

        // we configure legend for our radar chart
        Legend l = chart.getLegend();  // indicator sign text like "red for credit" and "green for cgpa"
        l.setTextSize(8);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true); //will increase radar space
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

    }

}