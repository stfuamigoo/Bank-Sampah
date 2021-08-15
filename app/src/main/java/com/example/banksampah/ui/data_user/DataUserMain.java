package com.example.banksampah.ui.data_user;

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
import com.example.banksampah.ui.sampah.EditorSampah;
import com.example.banksampah.ui.sampah.MainSampah;
import com.example.banksampah.ui.sampah.Sampah;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataUserMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private Adapter adapter;
    private List<User> userList;
    ApiInterface apiInterface;
    Adapter.RecyclerViewClickListener listener;
    ProgressBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user_main);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        bar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.rvuser);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(DataUserMain.this, EditorUser.class);
                intent.putExtra("id", userList.get(position).getId());
                intent.putExtra("nik", userList.get(position).getNik());
                intent.putExtra("password", userList.get(position).getPassword());
                intent.putExtra("nama", userList.get(position).getNama());
                intent.putExtra("alamat", userList.get(position).getAlamat());
                intent.putExtra("rt", userList.get(position).getRt());
                intent.putExtra("telepon", userList.get(position).getTelepon());
                intent.putExtra("email", userList.get(position).getEmail());
                intent.putExtra("saldo", userList.get(position).getSaldo());
                intent.putExtra("role", userList.get(position).getRole());
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

        Call<List<User>> call = apiInterface.getUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                bar.setVisibility(View.GONE);
                userList = response.body();
                Log.i(DataUserMain.class.getSimpleName(), response.body().toString());
                adapter = new Adapter(userList, DataUserMain.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(DataUserMain.this, "rp :"+
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
