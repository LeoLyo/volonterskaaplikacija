package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.List;

public class PeopleRecyclerAdapter extends RecyclerView.Adapter<PeopleRecyclerAdapter.PeopleViewHolder > {

    public List<People> peopleList;
    public Context context;

    public PeopleRecyclerAdapter(Context context, List<People> peopleList) {
        this.context=context;
        this.peopleList = peopleList;
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_child_navigation_list_of_people,parent,false);
        return new PeopleViewHolder(rView);
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, int position) {
        holder.bossText.setText(peopleList.get(position).getBoss());
        holder.emailText.setText(peopleList.get(position).getEmail());
        holder.roleText.setText(peopleList.get(position).getRole());

        final String person_id = peopleList.get(position).personId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Person ID: "+person_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView bossText,emailText,roleText;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            bossText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_boss);
            emailText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_email);
            roleText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_role);
        }
    }
}
