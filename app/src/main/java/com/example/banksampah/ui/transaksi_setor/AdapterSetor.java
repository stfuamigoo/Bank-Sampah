package com.example.banksampah.ui.transaksi_setor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banksampah.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSetor extends RecyclerView.Adapter<AdapterSetor.ViewHolder> implements Filterable {
    List<TransaksiSetor> transaksisetor, transaksisetorFilter;
    private Context context;
    private AdapterSetor.RecyclerViewClickListener mListener;
    CustomFilterSetor filter;

    public AdapterSetor(List<TransaksiSetor> transaksisetor, Context context, AdapterSetor.RecyclerViewClickListener listener){
        this.transaksisetor = transaksisetor;
        this.context = context;
        this.mListener = listener;
        this.transaksisetorFilter = transaksisetor;
    }

    @NonNull
    @Override
    public AdapterSetor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_setor, parent, false);
        return new AdapterSetor.ViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull AdapterSetor.ViewHolder holder, int position) {
        holder.txt_nama.setText(transaksisetor.get(position).getNama());
        holder.txt_jenissampah.setText(transaksisetor.get(position).getJenissampah());
        holder.txt_total.setText(transaksisetor.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return transaksisetor.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilterSetor((ArrayList<TransaksiSetor>) transaksisetorFilter, this);
        }
        return filter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterSetor.RecyclerViewClickListener mListener;
        private TextView txt_nama, txt_jenissampah, txt_total;
        private RelativeLayout mRowContainer;

        public ViewHolder(View itemView, AdapterSetor.RecyclerViewClickListener listener) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.nama);
            txt_jenissampah = itemView.findViewById(R.id.jenissampah);
            txt_total = itemView.findViewById(R.id.total);
            mRowContainer = itemView.findViewById(R.id.row_container_setor);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container_setor:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener{
        void onRowClick(View view, int position);
    }
}
