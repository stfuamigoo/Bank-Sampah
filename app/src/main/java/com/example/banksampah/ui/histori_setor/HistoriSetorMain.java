package com.example.banksampah.ui.histori_setor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banksampah.R;
import com.example.banksampah.SessionManager;
import com.example.banksampah.ui.transaksi_setor.TransaksiSetor;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriSetorMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private Adapter adapter;
    private List<TransaksiSetor> transaksiSetorList;
    ApiInterface apiInterface;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar bar;
    SessionManager session;
    String sessionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_setor);

        session = new SessionManager(this);
        session.checkloggin();
        HashMap<String, String> user = session.getUserDetail();
        sessionId = user.get(session.ID);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        bar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.rvhistorisetor);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(HistoriSetorMain.this, DetailHistoriSetor.class);
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
        Log.e("Id user adalah ", sessionId);
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
        searchView.setQueryHint("Search Histori...");
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

    public void getUser(String id){
        id = sessionId;
        Call<List<TransaksiSetor>> call = apiInterface.getHistoriSetor(id);
        call.enqueue(new Callback<List<TransaksiSetor>>() {
            @Override
            public void onResponse(Call<List<TransaksiSetor>> call, Response<List<TransaksiSetor>> response) {
                bar.setVisibility(View.GONE);
                transaksiSetorList = response.body();
                Log.i(HistoriSetorMain.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(transaksiSetorList, HistoriSetorMain.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiSetor>> call, Throwable t) {
                Toast.makeText(HistoriSetorMain.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String id;
        id = sessionId;
        getUser(id);
    }
}
