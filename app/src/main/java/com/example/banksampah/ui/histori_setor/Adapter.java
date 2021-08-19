package com.example.banksampah.ui.histori_setor;

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
import com.example.banksampah.ui.transaksi_setor.TransaksiSetor;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    List<TransaksiSetor> transaksisetor, transaksisetorFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilter filter;

    public Adapter(List<TransaksiSetor> transaksisetor, Context context, RecyclerViewClickListener listener){
        this.transaksisetor = transaksisetor;
        this.context = context;
        this.mListener = listener;
        this.transaksisetorFilter = transaksisetor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_histori_setor, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_tanggal_tarik.setText(transaksisetor.get(position).getTanggalsetor());
        holder.txt_jumlah_tarik.setText(transaksisetor.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return transaksisetor.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter((ArrayList<TransaksiSetor>) transaksisetorFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener{
        void onRowClick(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private TextView txt_tanggal_tarik, txt_jumlah_tarik;
        private RelativeLayout mRowContainer;

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            txt_tanggal_tarik = itemView.findViewById(R.id.tanggal_tarik);
            txt_jumlah_tarik = itemView.findViewById(R.id.jumlah_tarik);
            mRowContainer = itemView.findViewById(R.id.row_histori_setor);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_histori_setor:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
}
