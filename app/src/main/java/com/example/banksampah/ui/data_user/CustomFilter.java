package com.example.banksampah.ui.data_user;

import android.widget.Filter;


import java.util.ArrayList;

public class CustomFilter extends Filter {
    ArrayList<User> userArrayList;
    Adapter adapter;

    public CustomFilter(ArrayList<User> list, Adapter adapter) {
        this.adapter=adapter;
        this.userArrayList=list;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<User> filteredUser=new ArrayList<>();

            for (int i=0;i<userArrayList.size();i++)
            {
                //CHECK
                if(userArrayList.get(i).getNik().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredUser.add(userArrayList.get(i));
                }
            }

            results.count=filteredUser.size();
            results.values=filteredUser;

        }else
        {
            results.count=userArrayList.size();
            results.values=userArrayList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.user= (ArrayList<User>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}
