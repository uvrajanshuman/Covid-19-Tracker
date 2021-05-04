package com.example.covid_19;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IndiaAdapter extends RecyclerView.Adapter<IndiaAdapter.ViewHolder> {
    private ArrayList<IndianState> state_list;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public IndiaAdapter(ArrayList<IndianState> state_list) {
        this.state_list = state_list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView confirmed,active,recovered,death,deltaconfirm,
                deltaactive,deltadeath,deltarecovered,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            confirmed = itemView.findViewById(R.id.state_confirmed);
            active = itemView.findViewById(R.id.state_active);
            recovered = itemView.findViewById(R.id.state_recovered);
            death = itemView.findViewById(R.id.state_death);
            deltaconfirm = itemView.findViewById(R.id.delta_confirmed);
            deltaactive = itemView.findViewById(R.id.delta_active);
            deltadeath = itemView.findViewById(R.id.delta_death);
            deltarecovered = itemView.findViewById(R.id.delta_recovered);
            name = itemView.findViewById(R.id.state_name);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.india_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;}
     else if (viewType == TYPE_HEADER) {
        // Here Inflating your header view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.india_list_header, parent, false);
        return new ViewHolder(itemView);
    }
       else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position!=0){
        IndianState currentItem = state_list.get(position);
        holder.confirmed.setText(currentItem.getConfirmed());
        holder.name.setText(currentItem.getName());
        holder.active.setText(currentItem.getActive());
        holder.recovered.setText(currentItem.getRecovered());
        holder.death.setText(currentItem.getDeath());
        holder.deltaactive.setText("↑"+currentItem.getDeltaConfirmed());
        holder.deltaconfirm.setText("↑"+currentItem.getDeltaConfirmed());
        holder.deltarecovered.setText("↑"+currentItem.getDeltaRecovered());
        holder.deltadeath.setText("↑"+currentItem.getDeltaDeath());}
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return state_list.size();
    }

}
