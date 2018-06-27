package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;

public class CreateEventPage5RecyclerAdapter extends RecyclerView.Adapter<CreateEventPage5RecyclerAdapter.CreateEventPage5RecyclerViewHolder> {

    public ArrayList<EventPerson> eventPeople;


    public CreateEventPage5RecyclerAdapter() {
    }

    public CreateEventPage5RecyclerAdapter(ArrayList<EventPerson> eventPeople) {
        this.eventPeople = eventPeople;
    }

    @Override
    public CreateEventPage5RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_child_createevent_page5, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CreateEventPage5RecyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CreateEventPage5RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CreateEventPage5RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView specificRoleTextView, specificEmailTextView;
        ImageView addPersonImageView, moreOptionsImageView;

        public CreateEventPage5RecyclerViewHolder(View itemView) {
            super(itemView);

            specificRoleTextView = itemView.findViewById(R.id.createeventchildpage5choseneventrole);
            specificEmailTextView = itemView.findViewById(R.id.createeventchildpage5email);
            addPersonImageView = itemView.findViewById(R.id.createeventchildpage5addsomethingimage);
            moreOptionsImageView = itemView.findViewById(R.id.createeventchildpage5moreoptionsimage);
        }
    }
}
