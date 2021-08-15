package com.example.banksampah.ui.sampah;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    com.example.banksampah.ui.sampah.Adapter adapter;
    ArrayList<Sampah> filterList;

    public CustomFilter(ArrayList<Sampah> filterList, com.example.banksampah.ui.sampah.Adapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Sampah> filteredSampah=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getJenissampah().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredSampah.add(filterList.get(i));
                }
            }

            results.count=filteredSampah.size();
            results.values=filteredSampah;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.sampah= (ArrayList<Sampah>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}