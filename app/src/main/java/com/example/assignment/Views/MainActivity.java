package com.example.assignment.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.assignment.Adapter.CardViewAdapter;
import com.example.assignment.Model.DataItem;
import com.example.assignment.Model.ModelClass;
import com.example.assignment.R;
import com.example.assignment.Services.Retrofit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardViewAdapter cardViewAdapter;
    private ArrayList<DataItem> modelClasses;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        recyclerView = findViewById(R.id.recyclerview);
        modelClasses=new ArrayList<>();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        cardViewAdapter = new CardViewAdapter(this, modelClasses);
        recyclerView.setAdapter(cardViewAdapter);
        getUserData();
    }

    public void getUserData() {
        Call<ModelClass> call = Retrofit.getApi_services().getUsersList(1);
        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {
                if (response.code() == 200) {

                    if (response.body() != null) {
                        modelClasses.addAll(response.body().getData());
                    }

                    cardViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {
                Log.e("Api", "Not Working");
            }
        });
    }

}
