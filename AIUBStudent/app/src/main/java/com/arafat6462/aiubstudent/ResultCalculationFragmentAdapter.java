package com.arafat6462.aiubstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by arafat on 9/29/20 at 12:29 PM.
 */
public class ResultCalculationFragmentAdapter extends RecyclerView.Adapter<ResultCalculationFragmentAdapter.ResultCalculationFragmentViewHolder> {

    // for receive date from fragment layout class
    Context context;
    String[] title, description;
    int[] images;
    ////////////////  show hide retake //////////////
    LinearLayout linearLayout;
    CheckBox checkBox;
    ////////////////  show hide retake //////////////

    // for receive date from fragment layout class
    public ResultCalculationFragmentAdapter(Context context, String[] title, String[] description, int[] images) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.images = images;
    }

    // find the template layout-view from layout
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
    @Override
    public void onBindViewHolder(@NonNull ResultCalculationFragmentViewHolder holder, int position) {
        // holder is the object of ResultCalculationFragmentViewHolder & carry all the information.

//        holder.titleTextView.setText(title[position]);
     //   holder.descriptionTextView.setText(String.valueOf(position)); // find the position and set the recycler view layout height according to position
        holder.descriptionTextView.setText(description[position]);
//        holder.logoImageView.setImageResource(images[position]);


    }

    //this getItemCount is work for how many copy of template are made.
    @Override
    public int getItemCount() {
        return images.length;
    }


    //////////////////////
    // managing the row and keep track
    public class ResultCalculationFragmentViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView logoImageView;

        // now all layout file convert to  view. and  this view came to ViewHolder class and now it is in itemView.
        // itemView is now your row template layout.
        public ResultCalculationFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            ////////////////  show hide retake //////////////
            checkBox = itemView.findViewById(R.id.checkBox2);
            linearLayout = itemView.findViewById(R.id.retake_LinearLayout_part);

            if (checkBox.isChecked()) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
            ////////////////  show hide retake //////////////


 //           titleTextView = itemView.findViewById(R.id.titleInTemplate);
            descriptionTextView = itemView.findViewById(R.id.descriptionInTemplate);
 //            logoImageView= itemView.findViewById(R.id.appLogoInTemplate);
        }
    }
}
