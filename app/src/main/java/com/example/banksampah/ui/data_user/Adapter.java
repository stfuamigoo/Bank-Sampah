package com.example.banksampah.ui.data_user;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.banksampah.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private Context context;
    private RecyclerViewClickListener listener;
    CustomFilter filter;
    List<User> user, userFilter;

    public Adapter(List<User> user, Context context, RecyclerViewClickListener listener){
        this.context = context;
        this.user = user;
        this.userFilter = user;
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilter((ArrayList<User>) userFilter,this);

        }
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new ViewHolder(view, listener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.txt_nik.setText(user.get(position).getNik());
        holder.txt_nama.setText(user.get(position).getNama());
        holder.txt_role.setText(user.get(position).getRole());
        Glide.with(context)
                .load(R.drawable.datauser)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
//        void onLoveClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        private TextView txt_nik, txt_nama, txt_role;
        private RelativeLayout rv;
        private ImageView imageView;

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
            txt_nik = itemView.findViewById(R.id.nikuser);
            txt_nama = itemView.findViewById(R.id.namauser);
            txt_role = itemView.findViewById(R.id.roleuser);
            rv = itemView.findViewById(R.id.row_user);
            mListener = listener;
            rv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_user:
                    mListener.onRowClick(rv, getAdapterPosition());
                    break;
//                case R.id.love:
//                    mListener.onLoveClick(mLove, getAdapterPosition());
//                    break;
                default:
                    break;
            }
        }
    }
}
