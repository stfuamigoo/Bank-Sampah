package com.example.banksampah.ui.transaksi_setor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.banksampah.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiSetorMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterSetor adapter;
    private List<TransaksiSetor> transaksiSetorList;
    ApiInterfaceSetor apiInterface;
    AdapterSetor.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_setor_main);

        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView_setor);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterSetor.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(TransaksiSetorMain.this, EditorTransaksiSetor.class);
                intent.putExtra("id",transaksiSetorList.get(position).getId());
                intent.putExtra("tanggalsetor",transaksiSetorList.get(position).getTanggalsetor());
                intent.putExtra("id_user",transaksiSetorList.get(position).getId_user());
                intent.putExtra("nama",transaksiSetorList.get(position).getNama());
                intent.putExtra("saldo_user",transaksiSetorList.get(position).getSaldo_user());
                intent.putExtra("id_sampah",transaksiSetorList.get(position).getId_sampah());
                intent.putExtra("jenissampah",transaksiSetorList.get(position).getJenissampah());
                intent.putExtra("satuan",transaksiSetorList.get(position).getSatuan());
                intent.putExtra("harga",transaksiSetorList.get(position).getHarga());
                intent.putExtra("jumlah",transaksiSetorList.get(position).getJumlah());
                intent.putExtra("total",transaksiSetorList.get(position).getTotal());
                intent.putExtra("keterangan",transaksiSetorList.get(position).getKeterangan());
                startActivity(intent);

            }

        };

        Button but = findViewById(R.id.but_setor);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransaksiSetorMain.this, TambahTransaksiSetor.class));
            }
        });
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
        searchView.setQueryHint("Search Setor...");
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

    public void getSetor(){

        Call<List<TransaksiSetor>> call = apiInterface.getTransaksiSetor();
        call.enqueue(new Callback<List<TransaksiSetor>>() {
            @Override
            public void onResponse(Call<List<TransaksiSetor>> call, Response<List<TransaksiSetor>> response) {
                progressBar.setVisibility(View.GONE);
                transaksiSetorList = response.body();
                Log.i(TransaksiSetorMain.class.getSimpleName(), response.body().toString());
                adapter = new AdapterSetor(transaksiSetorList, TransaksiSetorMain.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiSetor>> call, Throwable t) {
                Toast.makeText(TransaksiSetorMain.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSetor();
    }
}