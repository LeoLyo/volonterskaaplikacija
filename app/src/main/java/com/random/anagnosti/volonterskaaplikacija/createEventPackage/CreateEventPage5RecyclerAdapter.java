package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateEventPage5RecyclerAdapter extends RecyclerView.Adapter<CreateEventPage5RecyclerAdapter.CreateEventPage5RecyclerViewHolder> {

    private ArrayList<EventPerson> mEventPeople;
    private Context mContext;
    private Activity mActivity;
    private RecyclerView bloodyRecyclerView;


    private RecyclerView.LayoutManager rLayoutManager;
    private CreateEventPage5RecyclerAdapter eRoleAdapter;


    public CreateEventPage5RecyclerAdapter(Activity activity, Context context, ArrayList<EventPerson> mEventPeople) {
        this.mActivity = activity;
        this.mContext = context;
        this.mEventPeople = mEventPeople;
    }


    @Override
    public CreateEventPage5RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_child_createevent_page5, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CreateEventPage5RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CreateEventPage5RecyclerViewHolder holder, int position) {
        final EventPerson tempEventPerson = mEventPeople.get(holder.getAdapterPosition());
        holder.specificRoleTextView.setText(tempEventPerson.getRoleOfIndividual().getName());
        holder.specificEmailTextView.setText(tempEventPerson.getEmail());
        holder.parentRoleTextView.setText("Parent email:");
        holder.parentEmailTextView.setText(tempEventPerson.getParentOfIndividual().getEmail());

        final Singleton singleton = Singleton.Instance();
        mEventPeople= singleton.mEventPeople;
        /*
        rLayoutManager = new LinearLayoutManager(mActivity);
        holder.peopleSubsRecyclerView.setLayoutManager(rLayoutManager);
        eRoleAdapter = new CreateEventPage5RecyclerAdapter(mActivity, mContext, tempEventPerson.getSubordinates());
        holder.peopleSubsRecyclerView.setAdapter(eRoleAdapter);
        bloodyRecyclerView = holder.peopleSubsRecyclerView;*/

        final int holdersPosition = holder.getAdapterPosition();

        final PopupMenu popupMenu = new PopupMenu(mActivity,holder.moreOptionsImageView);

        holder.moreOptionsImageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                popupMenu.getMenuInflater().inflate(R.menu.create_event_page_5_more_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    Dialog popUpDialog = new Dialog(mContext);
                    Button editConfirmButton;
                    EditText editEmailText;
                    Spinner editRoleSpinner, editParentSpinner;

                    Button removeButton;
                    TextView personTV, messageText;

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("EDIT")){

                            popUpDialog.setContentView(R.layout.fragment_child_createevent_page5_edit);
                            editEmailText = popUpDialog.findViewById(R.id.createeventchildpage5editedittext);
                            editRoleSpinner = popUpDialog.findViewById(R.id.createeventchildpage5editspinner);
                            editConfirmButton = popUpDialog.findViewById(R.id.createeventchildpage5editbutton);
                            editParentSpinner = popUpDialog.findViewById(R.id.createeventchildpage5editparentspinner);
                            editEmailText.setText(tempEventPerson.getEmail());

                            ArrayAdapter<EventRole> spinnerAdapter = new ArrayAdapter<EventRole>(mContext,android.R.layout.simple_spinner_item,singleton.mEventRoles);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            editRoleSpinner.setAdapter(spinnerAdapter);
                            int spinnerPosition = spinnerAdapter.getPosition(tempEventPerson.getRoleOfIndividual());
                            editRoleSpinner.setSelection(spinnerPosition);


                            editRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    EventRole spinnerRole = (EventRole) editRoleSpinner.getSelectedItem();
                                    ArrayList<EventPerson> spinnerListOfPeople = new ArrayList<>();
                                    spinnerListOfPeople.add(new EventPerson("nobody@nonono.com",new EventRole("Nothing",-8),0));
                                    for(int k=0;k<singleton.mEventRoles.size();k++){
                                        for(int z=0;z<singleton.mEventRoles.get(k).getSubordinates().size();z++){
                                            if(singleton.mEventRoles.get(k).getSubordinates().get(z).equals(spinnerRole)){
                                                for(int t=0;t<mEventPeople.size();t++){
                                                    if(mEventPeople.get(t).getRoleOfIndividual().equals(singleton.mEventRoles.get(k)))
                                                        spinnerListOfPeople.add(mEventPeople.get(t));

                                                }
                                            }
                                        }
                                    }
                                    if(spinnerListOfPeople.isEmpty()){

                                        ArrayList<String> nothing = new ArrayList<>();
                                        nothing.add("No parent.");
                                        ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,nothing);
                                        newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        editParentSpinner.setAdapter(newSpinnerAdapter);
                                    }else{

                                        ArrayAdapter<EventPerson> newSpinnerAdapter = new ArrayAdapter<EventPerson>(mContext,android.R.layout.simple_spinner_item,spinnerListOfPeople);
                                        newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        editParentSpinner.setAdapter(newSpinnerAdapter);
                                        int parentSpinnerPosition = newSpinnerAdapter.getPosition(tempEventPerson.getParentOfIndividual());
                                        editParentSpinner.setSelection(parentSpinnerPosition);

                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            editConfirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String emailCheck = editEmailText.getText().toString();
                                    if (emailCheck.matches("")) {
                                        Toast.makeText(mContext, "Email is empty. Please enter an email.", Toast.LENGTH_SHORT).show();
                                    }else if(!isEmailValid(emailCheck)){
                                        Toast.makeText(mContext, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        boolean ifExists = false;
                                        for (int i = 0; i < singleton.mUsedEmails.size(); i++) {
                                            if (emailCheck.matches(singleton.mUsedEmails.get(i))) {
                                                ifExists = true;
                                                break;
                                            }
                                        }
                                        if (ifExists) {
                                            Toast.makeText(mContext, "This email has already been entered in the list of people.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            singleton.mEventPeople.get(holdersPosition).setEmail(emailCheck);
                                            singleton.mEventPeople.get(holdersPosition).setRoleOfIndividual((EventRole)editRoleSpinner.getSelectedItem());
                                            singleton.mEventPeople.get(holdersPosition).setParentOfIndividual((EventPerson)editParentSpinner.getSelectedItem());

                                            bloodyRecyclerView = mActivity.findViewById(R.id.createEventPage5RecyclerView);
                                            rLayoutManager = new LinearLayoutManager(mActivity);
                                            bloodyRecyclerView.setLayoutManager(rLayoutManager);
                                            eRoleAdapter = new CreateEventPage5RecyclerAdapter(mActivity,mContext,mEventPeople);
                                            bloodyRecyclerView.setAdapter(eRoleAdapter);
                                            popUpDialog.dismiss();

                                        }
                                    }

                                }
                            });
                            Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            popUpDialog.show();

                        }
                        else if(menuItem.getTitle().equals("DELETE")){

                            popUpDialog.setContentView(R.layout.fragment_child_createevent_page5_remove);
                            removeButton=popUpDialog.findViewById(R.id.createeventchildpage5removebutton);
                            messageText=popUpDialog.findViewById(R.id.createeventchildpage5removemessage);
                            personTV=popUpDialog.findViewById(R.id.createeventchildpage5removepersonview);
                            personTV.setText(singleton.mEventPeople.get(holdersPosition).getEmail());
                            String message="Are you sure you want to remove the following person?";
                            messageText.setText(message);
                            removeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EventPerson removedPerson = singleton.mEventPeople.get(holdersPosition);
                                    singleton.mEventPeople.remove(holdersPosition);
                                    singleton.mUsedEmails.remove(removedPerson.getEmail());
                                    for(int i=0;i<singleton.mEventPeople.size();i++){
                                        if(singleton.mEventPeople.get(i).getParentOfIndividual().equals(removedPerson)){
                                            singleton.mEventPeople.get(i).setParentOfIndividual(new EventPerson("nobody@nonono.com",new EventRole("Nothing",-8),0));
                                        }
                                    }
                                    mEventPeople=singleton.mEventPeople;

                                    bloodyRecyclerView = mActivity.findViewById(R.id.createEventPage5RecyclerView);
                                    rLayoutManager = new LinearLayoutManager(mActivity);
                                    bloodyRecyclerView.setLayoutManager(rLayoutManager);
                                    eRoleAdapter = new CreateEventPage5RecyclerAdapter(mActivity,mContext,mEventPeople);
                                    bloodyRecyclerView.setAdapter(eRoleAdapter);
                                    popUpDialog.dismiss();

                                }
                            });
                            Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            popUpDialog.show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        /*holder.addPersonImageView.setOnClickListener(new View.OnClickListener() {

            Dialog popUpDialog = new Dialog(mContext);
            EditText emailEdit;
            Spinner roleSpinner;
            Button confirmEditButton;
            Singleton singleton = Singleton.Instance();


            @Override
            public void onClick(View view) {
                popUpDialog.setContentView(R.layout.fragment_child_createevent_page5_edit);

                emailEdit = popUpDialog.findViewById(R.id.createeventchildpage5editedittext);
                roleSpinner = popUpDialog.findViewById(R.id.createeventchildpage5editspinner);
                confirmEditButton = popUpDialog.findViewById(R.id.createeventchildpage5editbutton);


                ArrayAdapter<EventRole> spinnerAdapter = new ArrayAdapter<EventRole>(mContext,android.R.layout.simple_spinner_item,tempEventPerson.getRoleOfIndividual().getSubordinates());
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roleSpinner.setAdapter(spinnerAdapter);

                confirmEditButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String emailCheck=emailEdit.getText().toString();
                        if(emailCheck.matches("")){
                            Toast.makeText(mContext, "Email is empty. Please enter an email.", Toast.LENGTH_SHORT).show();
                        }/*else if(!isEmailValid(emailCheck)){
                            Toast.makeText(mContext, "Please enter a valid email.", Toast.LENGTH_SHORT).show();

                        }*//*
                        else{
                            boolean ifExists = false;
                            for(int i=0;i<singleton.mUsedEmails.size();i++){
                                if(emailCheck.matches(singleton.mUsedEmails.get(i))){
                                    ifExists=true;
                                    break;
                                }
                            }
                            if(ifExists){
                                Toast.makeText(mContext, "This email has already been entered in the list of people.", Toast.LENGTH_SHORT).show();
                            }else{

                                int newDepth = tempEventPerson.getDepth()+1;
                                ArrayList<Integer> positionsOfParents = tempEventPerson.getPreviousPositions();
                                positionsOfParents.add(holdersPosition);
                                EventRole selectedRole = (EventRole) roleSpinner.getSelectedItem();
                                ArrayList<EventPerson> soonToBeSingletonList = singleton.mEventPeople;

                                for(int i=0;i<tempEventPerson.getDepth();i++){
                                    soonToBeSingletonList=soonToBeSingletonList.get(tempEventPerson.getPreviousPositions().get(i)).getSubordinates();
                                }
                                EventPerson newPerson = new EventPerson(emailCheck,selectedRole,newDepth,positionsOfParents);
                                newPerson.darkenBy(newDepth*8);
                                soonToBeSingletonList.add(newPerson);
                                singleton.mUsedEmails.add(emailCheck);
                                mEventPeople=soonToBeSingletonList;

                                eRoleAdapter = new CreateEventPage5RecyclerAdapter(mActivity,mContext,mEventPeople);
                                bloodyRecyclerView.setAdapter(eRoleAdapter);
                                popUpDialog.dismiss();
                            }
                        }
                    }
                });
                Objects.requireNonNull(popUpDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popUpDialog.show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mEventPeople.size();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    class CreateEventPage5RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView specificRoleTextView, specificEmailTextView,parentRoleTextView,parentEmailTextView;
        ImageView addPersonImageView, moreOptionsImageView;
        RecyclerView peopleSubsRecyclerView;
        CardView personCardView;

        public CreateEventPage5RecyclerViewHolder(View itemView) {
            super(itemView);

            specificRoleTextView = itemView.findViewById(R.id.createeventchildpage5choseneventrole);
            specificEmailTextView = itemView.findViewById(R.id.createeventchildpage5email);
            addPersonImageView = itemView.findViewById(R.id.createeventchildpage5addsomethingimage);
            moreOptionsImageView = itemView.findViewById(R.id.createeventchildpage5moreoptionsimage);
            peopleSubsRecyclerView = itemView.findViewById(R.id.createeventchildpage5recyclerview);
            personCardView = itemView.findViewById(R.id.createeventchildpage5cardview);
            parentRoleTextView = itemView.findViewById(R.id.createeventchildpage5chosenparentrole);
            parentEmailTextView = itemView.findViewById(R.id.createeventchildpage5chosenparentemail);
        }


    }
}
