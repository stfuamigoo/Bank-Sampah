package com.example.banksampah.ui.transaksi_tarik;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banksampah.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiTarikMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private Adapter adapter;
    private List<TransaksiTarik> transaksiTarikList;
    ApiInterfaceTarik apiInterfaceTarik;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar bar;
    Button btn_tambah;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_tarik);

        btn_tambah = findViewById(R.id.but);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransaksiTarikMain.this, TambahTransaksiTarik.class));
            }
        });

        apiInterfaceTarik = ApiClientTarik.getApiClient().create(ApiInterfaceTarik.class);

        bar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.rvtransaksitarik);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(TransaksiTarikMain.this, EditorTransaksiTarik.class);
                intent.putExtra("id", transaksiTarikList.get(position).getId());
                intent.putExtra("tanggal_tarik", transaksiTarikList.get(position).getTanggal_tarik());
                intent.putExtra("id_user", transaksiTarikList.get(position).getId_user());
                intent.putExtra("nama_user", transaksiTarikList.get(position).getNama_user());
                intent.putExtra("saldo_user", transaksiTarikList.get(position).getSaldo_user());
                intent.putExtra("jumlah_tarik", transaksiTarikList.get(position).getJumlah_tarik());
                intent.putExtra("keterangan", transaksiTarikList.get(position).getKeterangan());
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search User...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getUser(){

        Call<List<TransaksiTarik>> call = apiInterfaceTarik.getTransaksiTarik();
        call.enqueue(new Callback<List<TransaksiTarik>>() {
            @Override
            public void onResponse(Call<List<TransaksiTarik>> call, Response<List<TransaksiTarik>> response) {
                bar.setVisibility(View.GONE);
                transaksiTarikList = response.body();
                Log.i(TransaksiTarikMain.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(transaksiTarikList, TransaksiTarikMain.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiTarik>> call, Throwable t) {
                Toast.makeText(TransaksiTarikMain.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }
}
