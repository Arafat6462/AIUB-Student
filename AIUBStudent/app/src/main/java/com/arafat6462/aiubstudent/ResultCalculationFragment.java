package com.arafat6462.aiubstudent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ResultCalculationFragment extends Fragment {
    // find all image,string and other all elements
    int[] images = {R.drawable.green_progressbar_indicator_dot,R.drawable.login_page_image_submit,R.drawable.blue_progressbar_indicator_dot,
            R.drawable.green_progressbar_indicator_dot,R.drawable.login_page_image_submit,R.drawable.blue_progressbar_indicator_dot};
    String[] title, description;
    ResultCalculationFragmentAdapter resultCalculationFragmentAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.fragment_result_calculation, container, false);

          ///////////
          recyclerView = view.findViewById(R.id.ResultCalculationRecyclerView);
          title = getResources().getStringArray(R.array.country_Name); // added all country name in title array
          description = getResources().getStringArray(R.array.country_desc);

          // send data to adapter class
        resultCalculationFragmentAdapter = new ResultCalculationFragmentAdapter(this.getActivity(),title,description,images);
        //set adapter
        recyclerView.setAdapter(resultCalculationFragmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));



          return view;
    }
}