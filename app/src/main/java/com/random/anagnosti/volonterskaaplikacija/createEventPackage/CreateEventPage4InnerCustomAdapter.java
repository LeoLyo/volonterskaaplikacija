package com.random.anagnosti.volonterskaaplikacija.createEventPackage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.random.anagnosti.volonterskaaplikacija.R;

import java.util.List;

/**
 * Adapter za listu sledbenika unutar popUpDialoga za kreiranje uloge u cetvrtom fragmentu CreateEvent dela aplikacije.
 */
public class CreateEventPage4InnerCustomAdapter extends BaseAdapter {

    Activity activity;
    List<EventRole> allRoles;
    LayoutInflater inflater;

    public CreateEventPage4InnerCustomAdapter() {
    }

    public CreateEventPage4InnerCustomAdapter(Activity activity) {
        this.activity = activity;
    }

    /**
     * Konstruktor koji preuzima Activity i listu svih uloga sledbenika.
     */
    public CreateEventPage4InnerCustomAdapter(Activity activity, List<EventRole> allRoles) {
        this.activity = activity;
        this.allRoles = allRoles;

        inflater = activity.getLayoutInflater();
    }

    /**
     * Vraca se broj svih uloga iz liste.
     */
    @Override
    public int getCount() {
        return allRoles.size();
    }

    /**
     * Vraca se specificna uloga iz liste.
     */
    @Override
    public Object getItem(int i) {
        return allRoles.get(i);
    }

    /**
     * Vraca se id odredjene uloge iz liste.
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Ako je trenutna uloga selektovana, to jest promenljiva isChecked je stavljena na true, onda se slika vezana za ovu ulogu postavlja u Checked sliku. U suprotnom, postavlja se u Unchecked.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        InnerViewHolder holder = null;

        if(view==null){
            view=inflater.inflate(R.layout.fragment_child_createevent_page4_role_list_checkbox_pattern,viewGroup,false);

            holder = new InnerViewHolder();

            holder.irvlistitem=view.findViewById(R.id.irvlistitem);
            holder.irvcheckbox=view.findViewById(R.id.irvcheckbox);

            view.setTag(holder);
        }else{
            holder=(InnerViewHolder)view.getTag();
        }
        EventRole currentRole =allRoles.get(i);

        holder.irvlistitem.setText(currentRole.getName());

        if(currentRole.isChecked()){
            holder.irvcheckbox.setBackgroundResource(R.drawable.checked);
        }else{
            holder.irvcheckbox.setBackgroundResource(R.drawable.check);
        }

        return view;
    }

    /**
     * Azurira se lista svih uloga.
     */
    public void updateRecords(List<EventRole> allRoles){
        this.allRoles = allRoles;
        notifyDataSetChanged();
    }

    class InnerViewHolder{
        TextView irvlistitem;
        ImageView irvcheckbox;
    }
}
