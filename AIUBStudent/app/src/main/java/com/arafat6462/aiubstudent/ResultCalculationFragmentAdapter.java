package com.arafat6462.aiubstudent;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by arafat on 9/29/20 at 12:29 PM.
 */
public class ResultCalculationFragmentAdapter extends RecyclerView.Adapter<ResultCalculationFragmentAdapter.ResultCalculationFragmentViewHolder> {

    // for receive date from fragment layout class
    Context context;
    static ArrayList<String> courseNameOfThisSemester = new ArrayList<String>();
    ResultCalculationFragment resultCalculationFragment = new ResultCalculationFragment();


    // for receive date from fragment layout class
    public ResultCalculationFragmentAdapter(Context context, ArrayList<String> courseNameOfThisSemester) {
        this.context = context;
        ResultCalculationFragmentAdapter.courseNameOfThisSemester = courseNameOfThisSemester;
    }


    // find the template layout-view from layout
    // where to get the single card as view holder object
    // what you want to show multiple time
    @NonNull
    @Override
    public ResultCalculationFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // convert layout to view
        LayoutInflater layoutInflater = LayoutInflater.from(context); // find context of design layout
        View view = layoutInflater.inflate(R.layout.result_calculation_fragment_template_layout, parent, false);

        // return view from top view to bottom viewHolder class
        return new ResultCalculationFragmentViewHolder(view);
    }


    // this onBindViewHolder method take data and set on template layout/row.
    // if i found template card view then, what i need to do.
    @Override
    public void onBindViewHolder(@NonNull ResultCalculationFragmentViewHolder holder, int position) {
        // holder is the object of ResultCalculationFragmentViewHolder & carry all the information.

//        holder.titleTextView.setText(title[position]);
        //   holder.descriptionTextView.setText(String.valueOf(position)); // find the position and set the recycler view layout height according to position
        holder.descriptionTextView.setText(courseNameOfThisSemester.get(position));
//        holder.logoImageView.setImageResource(images[position]);
    }


    //this getItemCount is work for how many copy of template are made.
    @Override
    public int getItemCount() {
        return courseNameOfThisSemester.size();
    }


    //////////////////////
    // managing the row and keep track
    public static class ResultCalculationFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, descriptionTextView;
        ImageView logoImageView;

        ////////////////  show hide retake //////////////
        LinearLayout linearLayout;
        CheckBox checkBox;
        SeekBar seekBar, seekBar2;
        ////////////////  show hide retake //////////////

        // now all layout file convert to  view. and  this view came to ViewHolder class and now it is in itemView.
        // itemView is now your row template layout.
        public ResultCalculationFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ////////////////  show hide retake and seekBar,checkBox //////////////

            // itemView.setOnClickListener(this);
            checkBox = itemView.findViewById(R.id.retake_checkBox);
            linearLayout = itemView.findViewById(R.id.retake_LinearLayout_part);
            seekBar = itemView.findViewById(R.id.seekBar);
            seekBar2 = itemView.findViewById(R.id.seekBar2);

            linearLayout.setVisibility(View.GONE); // initial all gone
            seekBar.setProgress(0);


            checkBox.setOnClickListener(this); // add click listener
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    // Log.d("seekbar", "onProgressChanged at : " + getAdapterPosition() + " = " + i);
                    // resultCalculationFragment.setSeekBarValue(context, getAdapterPosition(), i);

                    ResultCalculationFragment.seekBarValue[getAdapterPosition()] = i; // set seekbar value of ResultCalculationFragment
                    ResultCalculationFragment.calculated_Result_TextView.setTextColor(Color.parseColor("#4CB050")); //set green color when user change the seekBar


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    // Log.d("seekbar", "onProgressChanged at : " + getAdapterPosition() + " = " + i);
                    // resultCalculationFragment.setSeekBarValueRetake(context, getAdapterPosition(), i);

                    ResultCalculationFragment.seekBarValueRetake[getAdapterPosition()] = i;
                    ResultCalculationFragment.calculated_Result_TextView.setTextColor(Color.parseColor("#4CB050"));//set green color when user change the seekBar

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            ////////////////  show hide retake //////////////


            //           titleTextView = itemView.findViewById(R.id.titleInTemplate);
            descriptionTextView = itemView.findViewById(R.id.descriptionInTemplate);
            //            logoImageView= itemView.findViewById(R.id.appLogoInTemplate);
        }

        @Override
        public void onClick(View view) {
            if (checkBox.isChecked()) {
                linearLayout.setVisibility(View.VISIBLE);

            } else {
                linearLayout.setVisibility(View.GONE);
                seekBar2.setProgress(0); // progress will 0 when close the retake option
            }

            //  Log.d("click from view holder", "onClick: ");
        }
    }
}
