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

/**
 * Adapter za RecyclerView navigacionog prozora ovog dela aplikacije, to jest dela za prikazivanje aktivnog eventa.
 */
public class PeopleRecyclerAdapter extends RecyclerView.Adapter<PeopleRecyclerAdapter.PeopleViewHolder > {

    public List<People> peopleList;
    public Context context;

    /**
     * Konstruktor za preuzimanje konteksta i liste ljudi.
     */
    public PeopleRecyclerAdapter(Context context, List<People> peopleList) {
        this.context=context;
        this.peopleList = peopleList;
    }

    /**
     * Odgovarajuc View se poziva i prikazuje.
     */
    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_child_navigation_list_of_people,parent,false);
        return new PeopleViewHolder(rView);
    }

    /**
     * Nakon referenciranja svih vizuelnih elemenata, na pritisak celog View-a se ispisuje tekst sa id-em osobe koja je prikazana u ovom View-u.
     */
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

    /**
     * Vraca se broj liste ljudi.
     */
    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView bossText,emailText,roleText;

        /**
         * Referenciraju se odgovarajuci vizuelni elementi ovog Holdera liste.
         */
        public PeopleViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            bossText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_boss);
            emailText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_email);
            roleText=mView.findViewById(R.id.fragment_child_navigation_list_of_people_role);
        }
    }
}
