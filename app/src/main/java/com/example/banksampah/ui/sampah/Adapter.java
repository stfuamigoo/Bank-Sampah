package com.example.banksampah.ui.sampah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.banksampah.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.banksampah.ui.sampah.Adapter.MyViewHolder> implements Filterable {

    List<com.example.banksampah.ui.sampah.Sampah> sampah, sampahFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    com.example.banksampah.ui.sampah.CustomFilter filter;

    public Adapter(List<com.example.banksampah.ui.sampah.Sampah> sampah, Context context, RecyclerViewClickListener listener) {
        this.sampah = sampah;
        this.sampahFilter = sampah;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mJenissampah.setText(sampah.get(position).getJenissampah());
        holder.mSatuan.setText(sampah.get(position).getSatuan());
        holder.mHarga.setText(sampah.get(position).getHarga());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.bg);
        requestOptions.error(R.drawable.bg);

        Glide.with(context)
                .load(sampah.get(position).getPicture())
                .apply(requestOptions)
                .into(holder.mPicture);

//        final Boolean love = sampah.get(position).getLove();
//
//        if (love){
//            holder.mLove.setImageResource(R.drawable.likeon);
//        } else {
//            holder.mLove.setImageResource(R.drawable.likeof);
//        }

    }

    @Override
    public int getItemCount() {
        return sampah.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new com.example.banksampah.ui.sampah.CustomFilter((ArrayList<com.example.banksampah.ui.sampah.Sampah>) sampahFilter,this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private ImageView mPicture;
        private ImageView mLove;
        private TextView mJenissampah, mSatuan, mHarga, mKeterangan;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mJenissampah = itemView.findViewById(R.id.jenissampah);
            mSatuan = itemView.findViewById(R.id.satuan);
            mHarga = itemView.findViewById(R.id.harga);
//            mKeterangan = itemView.findViewById(R.id.keterangan);
//            mType = itemView.findViewById(R.id.type);
//            mLove = itemView.findViewById(R.id.love);
//            mDate = itemView.findViewById(R.id.date);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
//            mLove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
//                case R.id.love:
//                    mListener.onLoveClick(mLove, getAdapterPosition());
//                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
//        void onLoveClick(View view, int position);
    }

}
