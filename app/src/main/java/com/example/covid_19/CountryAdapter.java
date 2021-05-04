package com.example.covid_19;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    List<CountryDetails> country_list;

    public CountryAdapter(List<CountryDetails> country_list) {
        this.country_list = country_list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalcases, activecases, death, recovered, name, updatetime;
        ImageView flag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalcases = itemView.findViewById(R.id.total_cases_regional);
            activecases = itemView.findViewById(R.id.total_active_regional);
            death = itemView.findViewById(R.id.total_deaths_regional);
            recovered = itemView.findViewById(R.id.total_recovered_regional);
            name = itemView.findViewById(R.id.country_name);
            updatetime = itemView.findViewById(R.id.updated_regional);
            flag = itemView.findViewById(R.id.flag);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryDetails currentItem = country_list.get(position);
        holder.activecases.setText(currentItem.getActive());
        holder.totalcases.setText(currentItem.getCases());
        holder.death.setText(currentItem.getDeaths());
        holder.recovered.setText(currentItem.getRecovered());
        holder.name.setText(currentItem.getName());
        //setting up time
        //ssetting up unix time to local time
        Date dateObject = new Date(currentItem.getUpdated());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy h:mm a");
        String dateToDisplay = dateFormatter.format(dateObject);
        holder.updatetime.setText(dateToDisplay);
        //setting up flag
        ImageView imageView = holder.flag;
        Glide.with(holder.flag.getContext()).load(currentItem.getFlagUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return country_list.size();
    }

    //for filter
    public void filterList(ArrayList<CountryDetails> filteredList) {
        country_list = filteredList;
        notifyDataSetChanged();
    }
}