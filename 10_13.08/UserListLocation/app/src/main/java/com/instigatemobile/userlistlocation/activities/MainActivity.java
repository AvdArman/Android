package com.instigatemobile.userlistlocation.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.instigatemobile.userlistlocation.R;
import com.instigatemobile.userlistlocation.adapters.UserAdapter;
import com.instigatemobile.userlistlocation.api.ApiResponse;
import com.instigatemobile.userlistlocation.api.RetrofitClient;
import com.instigatemobile.userlistlocation.api.RetrofitInterface;
import com.instigatemobile.userlistlocation.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<User> list;
    private UserAdapter adapter;
    private final int countOfUsers = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RetrofitInterface client = RetrofitClient.getClient().create(RetrofitInterface.class);
        client.fetchUsers(countOfUsers).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                list = response.body().getResults();
                adapter = new UserAdapter(getSupportFragmentManager(), MainActivity.this, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Main", t.toString());
            }
        });
    }
}
