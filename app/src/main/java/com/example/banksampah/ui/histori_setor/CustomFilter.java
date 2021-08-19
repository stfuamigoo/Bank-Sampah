package com.example.banksampah.ui.histori_setor;

import android.widget.Filter;

import com.example.banksampah.ui.transaksi_setor.TransaksiSetor;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    ArrayList<TransaksiSetor> transaksisetorArrayList;
    Adapter adapter;

    public CustomFilter(ArrayList<TransaksiSetor> list, Adapter adapter) {
        this.adapter=adapter;
        this.transaksisetorArrayList=list;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<TransaksiSetor> filteredTransaksiSetor=new ArrayList<>();

            for (int i=0;i<transaksisetorArrayList.size();i++)
            {
                //CHECK
                if(transaksisetorArrayList.get(i).getNama().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredTransaksiSetor.add(transaksisetorArrayList.get(i));
                }
            }

            results.count=filteredTransaksiSetor.size();
            results.values=filteredTransaksiSetor;

        }else
        {
            results.count=transaksisetorArrayList.size();
            results.values=transaksisetorArrayList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.transaksisetor= (ArrayList<TransaksiSetor>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
