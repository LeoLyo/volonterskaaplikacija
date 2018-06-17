package com.random.anagnosti.volonterskaaplikacija;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class CreateEventPage4RecyclerAdapter extends RecyclerView.Adapter<CreateEventPage4RecyclerAdapter.CreateEventPage4RecyclerViewHolder>{

    private Context recyclerContext;
    private List<EventRole> eventRolesList;


    @Override
    public CreateEventPage4RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerContext);
        View rView = inflater.inflate(R.layout.fragment_child_createevent_page4,null);
        return new CreateEventPage4RecyclerViewHolder(rView);
    }

    @Override
    public void onBindViewHolder(CreateEventPage4RecyclerViewHolder holder, int position) {
        EventRole tempEventRole = eventRolesList.get(position);
        holder.titleTextView.setText(tempEventRole.getName());
        ArrayAdapter<EventRole> eventRoleAdapter = new ArrayAdapter<EventRole>(recyclerContext,android.R.layout.simple_spinner_item,eventRolesList);
        eventRoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerPage4.setAdapter(eventRoleAdapter);

    }

    @Override
    public int getItemCount() {
        return eventRolesList.size();
    }


    class CreateEventPage4RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        LinearLayout subordinatesLinearLayout;
        Button editButtonPage4;
        Spinner spinnerPage4;

        public CreateEventPage4RecyclerViewHolder(View itemView) {
            super(itemView);

            titleTextView=itemView.findViewById(R.id.createeventchildpage4title);
            subordinatesLinearLayout = itemView.findViewById(R.id.createeventchildpage4linearlayout);
            editButtonPage4 = itemView.findViewById(R.id.createeventchildpage4editbutton);
            spinnerPage4 = itemView.findViewById(R.id.createeventchildpage4spinner);
        }
    }

}
