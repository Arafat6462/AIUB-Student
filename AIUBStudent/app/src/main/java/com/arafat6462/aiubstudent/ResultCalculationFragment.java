package com.arafat6462.aiubstudent;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
 import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultCalculationFragment extends Fragment {

    // for progressBar
    private ProgressBar progressBarBlue, progressBarGreen, progressBarYellow;
    ObjectAnimator progressAnimatorBlue, progressAnimatorGreen, progressAnimatorYellow;
    RecyclerView recyclerView;
    ImageView imageView55;
    private TextView thisSemesterGpa, totalCgpa, totalCompletedCredit,totalCredit;
    public static TextView calculated_Result_TextView;


    static int[] seekBarValue;
    static int[] seekBarValueRetake;
    static ArrayList<Double> perCourseCredit = new ArrayList<Double>();
    static ArrayList<String> description = new ArrayList<String>();


    // for data collect
    ParseDataFromPortal parseData = new ParseDataFromPortal(null, null, null);
    ResultCalculation resultCalculation = new ResultCalculation();
    ResultCalculationFragmentAdapter resultCalculationFragmentAdapter;










    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_calculation, container, false);        // Inflate the layout for this fragment


        calculated_Result_TextView = view.findViewById(R.id.calculated_Result_TextView);

        //////// add data /////////////
        perCourseCredit = parseData.getCourseCredit();
        description = parseData.getCourseName();
        seekBarValue = new int[parseData.getCourseName().size()];
        seekBarValueRetake = new int[parseData.getCourseName().size()];
        //////// add data /////////////

        ////////////// set all as 0 at initial /////////////////
        for (int i = 0; i < seekBarValue.length; i++) {
            seekBarValue[i] = 0;
            seekBarValueRetake[i] = 0;
        }
        ////////////// set all as 0 at initial /////////////////


        // ------------------- log -----------------------------
        Log.d("new3","credit 1:"+  perCourseCredit.get(0));
        Log.d("new3","credit 2:"+  perCourseCredit.get(1));
        Log.d("new3","credit 3:"+  perCourseCredit.get(2));
        Log.d("new3","credit 4:"+  perCourseCredit.get(3));

        Log.d("new3","COURSE 1:"+ parseData.getCourseName().get(0));
        Log.d("new3","COURSE 2:"+ parseData.getCourseName().get(1));
        Log.d("new3","COURSE 3:"+ parseData.getCourseName().get(2));
        Log.d("new3","COURSE 4:"+ parseData.getCourseName().get(3));
        // ------------------- log -----------------------------



        thisSemesterGpa = view.findViewById(R.id.student_this_semester_cgpa);
        totalCgpa = view.findViewById(R.id.student_current_cgpa);
        totalCompletedCredit = view.findViewById(R.id.total_completed_credit);
        totalCredit = view.findViewById(R.id.total_credit);


        /////////////// progress bar /////////////
        double progressBarBlueProgress = parseData.getCompletedCredit() * 100 / 148, progressBarGreenProgress = parseData.getCgpa() * 100 / 4, progressBarYellowProgress = 0.0 * 100 / 4; // set this value as credit and cgpa

        progressBarBlue = view.findViewById(R.id.progressBarBlue);
        progressBarGreen = view.findViewById(R.id.progressBarGreen);
        progressBarYellow = view.findViewById(R.id.progressBarYellow);

        progressBarBlue.setMax(100 * 100);
        progressBarGreen.setMax(100 * 100);
        progressBarYellow.setMax(100 * 100);
        progressAnimatorBlue = ObjectAnimator.ofInt(progressBarBlue, "progress", progressBarBlue.getProgress(), (int) (progressBarBlueProgress * 100));
        progressAnimatorGreen = ObjectAnimator.ofInt(progressBarGreen, "progress", progressBarGreen.getProgress(), (int) (progressBarGreenProgress * 100));
        progressAnimatorYellow = ObjectAnimator.ofInt(progressBarYellow, "progress", progressBarYellow.getProgress(), (int) (progressBarYellowProgress * 100));
        progressAnimatorBlue.setDuration(1500);
        progressAnimatorGreen.setDuration(1500);
        progressAnimatorYellow.setDuration(1500);
        progressAnimatorBlue.start();
        progressAnimatorGreen.start();
        progressAnimatorYellow.start();

        totalCgpa.setText(String.valueOf(parseData.getCgpa())); // converting double to string.
        totalCompletedCredit.setText(String.valueOf(parseData.getCompletedCredit()));
        thisSemesterGpa.setText(String.valueOf(0.0));
        totalCredit.setText("/"+ParseDataFromPortal.getTotalCredit());
        calculated_Result_TextView.setTextColor(Color.parseColor( "#BCEAC0"));



        ImageView imageView1 = view.findViewById(R.id.calculateCgpaButtonImageView);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resultCalculation.calculateResult(parseData.getCgpa(), parseData.getCompletedCredit(), seekBarValue, seekBarValueRetake, perCourseCredit); // for calculating the result and update the result
                updateResult();
            }
        });

/////////////// progress bar end /////////////


        ///////////
        recyclerView = view.findViewById(R.id.ResultCalculationRecyclerView);
        // description = getResources().getStringArray(R.array.country_desc);

        // send data to adapter class by default constructor
        resultCalculationFragmentAdapter = new ResultCalculationFragmentAdapter(this.getActivity(), description);
        //set adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(resultCalculationFragmentAdapter);

        // Decoration the recycler view divided row
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);


        // hide the bottom navigation & top status bar in calculate cgpa fragment from main activity.
        ((MainActivity) getActivity()).SetNavigationVisibilityAndBackButton(false);

        // back button
        ImageView imageView = view.findViewById(R.id.back_button_on_calculate_result);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).SetNavigationVisibilityAndBackButton(true);
            }
        });
        imageView55 = view.findViewById(R.id.calculateCgpaButtonImageView);

        return view;
    }

    public void updateResult() {


        /////////////// progress bar update /////////////
        double progressBarBlueProgress = resultCalculation.totalCredit * 100 / 148,
                progressBarGreenProgress = resultCalculation.totalCgpa * 100 / 4,
                progressBarYellowProgress = resultCalculation.currentSemesterResult * 100 / 4; // set this value as credit and cgpa

        progressBarBlue.setMax(100 * 100);
        progressBarGreen.setMax(100 * 100);
        progressBarYellow.setMax(100 * 100);
        progressAnimatorBlue = ObjectAnimator.ofInt(progressBarBlue, "progress", progressBarBlue.getProgress(), (int) (progressBarBlueProgress * 100));
        progressAnimatorGreen = ObjectAnimator.ofInt(progressBarGreen, "progress", progressBarGreen.getProgress(), (int) (progressBarGreenProgress * 100));
        progressAnimatorYellow = ObjectAnimator.ofInt(progressBarYellow, "progress", progressBarYellow.getProgress(), (int) (progressBarYellowProgress * 100));
        progressAnimatorBlue.setDuration(1500);
        progressAnimatorGreen.setDuration(1500);
        progressAnimatorYellow.setDuration(1500);
        progressAnimatorBlue.start();
        progressAnimatorGreen.start();
        progressAnimatorYellow.start();

        totalCgpa.setText(String.valueOf(resultCalculation.totalCgpa)); // converting double to string.
        totalCompletedCredit.setText(String.valueOf(resultCalculation.totalCredit));
        thisSemesterGpa.setText(String.valueOf(resultCalculation.currentSemesterResult));
        calculated_Result_TextView.setTextColor(Color.parseColor( "#BCEAC0"));

/////////////// progress bar update end /////////////
    }

    ////////////  get value of seekBar from adapter /////////////
    public void setSeekBarValue(Context context, int position, int value) {
        seekBarValue[position] = value;

//        Log.d("value", "seekBarValue : "+ seekBarValue[position]+ " position : "+ position);
//         Log.d("value1", "cgpa ...: m..."+ seekBarValue[0]);
//        Log.d("value1", "cgpa ...: m..."+ seekBarValue[1]);
//        Log.d("value1", "cgpa ...: m..."+ seekBarValue[2]);

        calculated_Result_TextView.setTextColor(Color.parseColor( "#4CB050")); //set green color when user change the seekBar

    }


    public void setSeekBarValueRetake(Context context, int position, int value) {
        seekBarValueRetake[position] = value;
        Log.d("value", "seekBarValueRetake : " + seekBarValueRetake[position]);

        calculated_Result_TextView.setTextColor(Color.parseColor( "#4CB050"));//set green color when user change the seekBar



    }


    ////////////  get value of seekBar from adapter /////////////


//    // set the bottom navigation true before another activity start
    // move code to onBackpress

//    @Override
//    public void onPause() {
//        ((MainActivity) getActivity()).SetNavigationVisibilityAndBackButton(true);
//        super.onPause();
//    }
}