package com.arafat6462.aiubstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by arafat on 9/29/20 at 12:29 PM.
 */
public class ResultCalculationFragmentAdapter extends RecyclerView.Adapter<ResultCalculationFragmentAdapter.ResultCalculationFragmentViewHolder> {

    // for receive date from fragment layout class
    Context context;
    String[] title,description ;
    int[] images;

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
         LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.result_calculation_fragment_template_layout,parent,false);

       // return to viewHolder class in bottom
        return new ResultCalculationFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultCalculationFragmentViewHolder holder, int position) {
        // holder is the object of ResultCalculationFragmentViewHolder & carry all the information.

        holder.titleTextView.setText(title[position]);
        holder.descriptionTextView.setText(description[position]);
        holder.logoImageView.setImageResource(images[position]);

    }

    @Override
    public int getItemCount()
    {
        return images.length; // how many copy of template are made.
    }




//////////////////////
    public class ResultCalculationFragmentViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView,descriptionTextView;
        ImageView logoImageView;
// now all layout file convert to  view. and  this view came to ViewHolder class and now it is in itemView.
        public ResultCalculationFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleInTemplate);
            descriptionTextView = itemView.findViewById(R.id.descriptionInTemplate);
            logoImageView= itemView.findViewById(R.id.appLogoInTemplate);
        }
    }
}
