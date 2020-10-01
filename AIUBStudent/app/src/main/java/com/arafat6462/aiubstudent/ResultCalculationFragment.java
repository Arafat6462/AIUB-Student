package com.arafat6462.aiubstudent;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;


public class ResultCalculationFragment extends Fragment {

    // for progressBar
    private ProgressBar progressBarBlue, progressBarGreen,progressBarYellow;
    ObjectAnimator progressAnimatorBlue, progressAnimatorGreen,progressAnimatorYellow;


    // find all image,string and other all elements
    int[] images = {R.drawable.green_progressbar_indicator_dot, R.drawable.login_page_image_submit, R.drawable.blue_progressbar_indicator_dot,
            R.drawable.green_progressbar_indicator_dot, R.drawable.login_page_image_submit, R.drawable.blue_progressbar_indicator_dot};
    String[] title, description;
    ResultCalculationFragmentAdapter resultCalculationFragmentAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_calculation, container, false);


        /////////////// progress bar /////////////
        double progressBarBlueProgress = 80 * 100 / 148, progressBarGreenProgress = 3.16 * 100 / 4, progressBarYellowProgress = 3.5 * 100 / 4; // set this value as credit and cgpa

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
/////////////// progress bar end /////////////


        ///////////
        recyclerView = view.findViewById(R.id.ResultCalculationRecyclerView);
        title = getResources().getStringArray(R.array.country_Name); // added all country name in title array
        description = getResources().getStringArray(R.array.country_desc);

        // send data to adapter class
        resultCalculationFragmentAdapter = new ResultCalculationFragmentAdapter(this.getActivity(), title, description, images);
        //set adapter
        recyclerView.setAdapter(resultCalculationFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


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
        return view;
    }

    // set the bottom navigation true before another activity start
    @Override
    public void onPause() {
        ((MainActivity) getActivity()).SetNavigationVisibilityAndBackButton(true);
        super.onPause();
    }
}