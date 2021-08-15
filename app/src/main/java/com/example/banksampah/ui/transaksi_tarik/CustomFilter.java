package com.example.banksampah.ui.transaksi_tarik;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    ArrayList<TransaksiTarik> transaksitarikArrayList;
    Adapter adapter;

    public CustomFilter(ArrayList<TransaksiTarik> list, Adapter adapter) {
        this.adapter=adapter;
        this.transaksitarikArrayList=list;
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
            ArrayList<TransaksiTarik> filteredTransaksiTarik=new ArrayList<>();

            for (int i=0;i<transaksitarikArrayList.size();i++)
            {
                //CHECK
                if(transaksitarikArrayList.get(i).getNama_user().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredTransaksiTarik.add(transaksitarikArrayList.get(i));
                }
            }

            results.count=filteredTransaksiTarik.size();
            results.values=filteredTransaksiTarik;

        }else
        {
            results.count=transaksitarikArrayList.size();
            results.values=transaksitarikArrayList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.transaksitarik= (ArrayList<TransaksiTarik>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
