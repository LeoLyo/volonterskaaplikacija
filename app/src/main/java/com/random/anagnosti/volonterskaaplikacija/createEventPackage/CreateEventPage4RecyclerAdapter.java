package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateEventPage4RecyclerAdapter extends RecyclerView.Adapter<CreateEventPage4RecyclerAdapter.CreateEventPage4RecyclerViewHolder> {

    private Context recyclerContext;
    private Activity recyclerActivity;
    private List<EventRole> eventRolesList;
    private ArrayList<EventRole> eRoleList;
    private CreateEventPage4RecyclerAdapter eRoleAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutManager;

    private Button tempButton;
    private RecyclerView iRecyclerView;
    private RecyclerView.LayoutManager irLayoutManager;
    // private CreateEventPage4InnerRecyclerAdapter ieRoleAdapter;

    //private static LayoutInflater inflater = null;




    public CreateEventPage4RecyclerAdapter(Activity activity, Context context, ArrayList<EventRole> eventRoles) {
        this.recyclerActivity = activity;
        this.recyclerContext = context;
        this.eventRolesList = eventRoles;
    }

    @Override
    public CreateEventPage4RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_child_createevent_page4, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CreateEventPage4RecyclerViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final CreateEventPage4RecyclerViewHolder holder, int position) {


        EventRole tempEventRole = eventRolesList.get(position);
        holder.titleTextView.setText(tempEventRole.getName());
        holder.descriptionTextView.setText(tempEventRole.getDescription());
        List<EventRole> checkedSubordinates = tempEventRole.getSubordinates();
        holder.suboridnatesTextview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Singleton singleton = Singleton.Instance();
                String tempString = singleton.mEventRoles.get(holder.getAdapterPosition()).getAllSubordinatesInString();
                Toast.makeText(recyclerContext, tempString, Toast.LENGTH_SHORT).show();

            }
        });


        final Singleton singleton = Singleton.Instance();
        eRoleList = singleton.mEventRoles;

        holder.editButtonPage4.setOnClickListener(new View.OnClickListener(){
            Dialog popUpDialog = new Dialog(recyclerContext);
            Button confirmEditButton;
            EditText titleEdit, descriptionEdit;
            ListView listOfAvailableRoles;

            @Override
            public void onClick(View view) {
                popUpDialog.setContentView(R.layout.fragment_child_createevent_page4_edit);

                confirmEditButton = popUpDialog.findViewById(R.id.createeventchildpage4confirmbutton);
                titleEdit = popUpDialog.findViewById(R.id.createeventchildpage4titleedit);
                descriptionEdit = popUpDialog.findViewById(R.id.createeventchildpage4descriptionedit);
                listOfAvailableRoles = popUpDialog.findViewById(R.id.createeventchildpage4listviewedit);
                ArrayList<EventRole> checkedRoles = singleton.mEventRoles.get(holder.getAdapterPosition()).getSubordinates();
                for(int i=0;i<checkedRoles.size();i++){
                    for(int j=0;j<eRoleList.size();j++){
                        if(checkedRoles.get(i).getName().matches(eRoleList.get(j).getName())){
                            eRoleList.get(j).setChecked(true);
                        }
                    }
                }
                final CreateEventPage4InnerCustomAdapter cAdapter = new CreateEventPage4InnerCustomAdapter(recyclerActivity, eRoleList);
                titleEdit.setText(singleton.mEventRoles.get(holder.getAdapterPosition()).getName());
                descriptionEdit.setText(singleton.mEventRoles.get(holder.getAdapterPosition()).getDescription());
                listOfAvailableRoles.setAdapter(cAdapter);
                if (listOfAvailableRoles.getScaleY() > 200) {
                    listOfAvailableRoles.setScaleY(200);
                }
                listOfAvailableRoles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        EventRole tempRole = eRoleList.get(i);

                        if (tempRole.isChecked()) {
                            tempRole.setChecked(false);
                        } else {
                            tempRole.setChecked(true);
                        }
                        eRoleList.set(i, tempRole);

                        cAdapter.updateRecords(eRoleList);
                    }
                });

                confirmEditButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String titleCheck = titleEdit.getText().toString();
                        if (titleCheck.matches("")) {
                            Toast.makeText(recyclerContext, "Title is empty. Please enter title.", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean ifExists = false;
                            if(!titleCheck.matches(singleton.mEventRoles.get(holder.getAdapterPosition()).getName())){
                                for (int i = 0; i < eRoleList.size(); i++) {
                                    if (titleCheck.matches(eRoleList.get(i).getName())) {
                                        ifExists = true;
                                        break;
                                    }
                                }
                            }

                            if (ifExists) {
                                Toast.makeText(recyclerContext, "A position with this title already exists. Please select another title.", Toast.LENGTH_SHORT).show();
                            } else {

                                ArrayList<EventRole> selectedSubs = new ArrayList<EventRole>();
                                for (int j = 0; j < eRoleList.size(); j++) {
                                    if (eRoleList.get(j).isChecked()) {
                                        selectedSubs.add(eRoleList.get(j));
                                    }
                                }
                                singleton.mEventRoles.get(holder.getAdapterPosition()).setName(titleCheck);
                                singleton.mEventRoles.get(holder.getAdapterPosition()).setDescription(descriptionEdit.getText().toString());
                                singleton.mEventRoles.get(holder.getAdapterPosition()).setSubordinates(selectedSubs);

                                recyclerView=recyclerActivity.findViewById(R.id.createEventPage4RecyclerView);
                                rLayoutManager = new LinearLayoutManager(recyclerActivity);
                                recyclerView.setLayoutManager(rLayoutManager);
                                eRoleAdapter = new CreateEventPage4RecyclerAdapter(recyclerActivity, recyclerContext, eRoleList);
                                recyclerView.setAdapter(eRoleAdapter);
                                refreshRoleList(eRoleList);
                                popUpDialog.dismiss();
                            }
                        }
                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
            }

        });

        holder.mainCardView.setOnLongClickListener(new View.OnLongClickListener() {

            Dialog popUpDialog = new Dialog(recyclerContext);
            Button removeButton;
            TextView roleTV, messageText;

            @Override
            public boolean onLongClick(View view) {
                popUpDialog.setContentView(R.layout.fragment_child_createevent_page4_remove);
                removeButton=popUpDialog.findViewById(R.id.createeventchildpage4removebutton);
                messageText=popUpDialog.findViewById(R.id.createeventchildpage4removemessage);
                roleTV=popUpDialog.findViewById(R.id.createeventchildpage4removeroleview);
                roleTV.setText(singleton.mEventRoles.get(holder.getAdapterPosition()).getName());
                String message="Are you sure you want to remove the following role?";
                messageText.setText(message);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventRole removedRole = singleton.mEventRoles.get(holder.getAdapterPosition());
                        singleton.mEventRoles.remove(holder.getAdapterPosition());
                        for(int i=0;i<singleton.mEventRoles.size();i++){
                            for(int j=0;j<singleton.mEventRoles.get(i).getSubordinates().size();j++){
                                if (singleton.mEventRoles.get(i).getSubordinates().get(j).equals(removedRole)){
                                    singleton.mEventRoles.get(i).getSubordinates().remove(j);
                                }
                            }
                        }
                        eRoleList=singleton.mEventRoles;

                        recyclerView=recyclerActivity.findViewById(R.id.createEventPage4RecyclerView);
                        rLayoutManager = new LinearLayoutManager(recyclerActivity);
                        recyclerView.setLayoutManager(rLayoutManager);
                        eRoleAdapter = new CreateEventPage4RecyclerAdapter(recyclerActivity,recyclerContext,eRoleList);
                        recyclerView.setAdapter(eRoleAdapter);
                        popUpDialog.dismiss();
                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
                return true;
            }
        });

    }


    private void refreshRoleList(ArrayList<EventRole> roleListForRefreshing) {
        for (int i = 0; i < roleListForRefreshing.size(); i++) {
            if (roleListForRefreshing.get(i).isChecked()) {
                roleListForRefreshing.get(i).setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return eventRolesList.size();
    }


    class CreateEventPage4RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, descriptionTextView, suboridnatesTextview;
        Button editButtonPage4;
        CardView mainCardView;

        public CreateEventPage4RecyclerViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.createeventchildpage4title);
            descriptionTextView = itemView.findViewById(R.id.createeventchildpage4description);
            suboridnatesTextview = itemView.findViewById(R.id.createeventchildpage4subordinates);
            editButtonPage4 = itemView.findViewById(R.id.createeventchildpage4editbutton);
            mainCardView = itemView.findViewById(R.id.createeventchildpage4cardview);
        }
    }

}
