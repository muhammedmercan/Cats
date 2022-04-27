package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Favorites extends AppCompatActivity {

    public RecyclerView favoritesRecyclerview;
    public ArrayList<Cat> data;
    public ArrayList<String> catsName;
    public AutoCompleteTextView multiAutoCompleteTextView;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        data = new ArrayList<Cat>();
        catsName = new ArrayList<>();


        getDataFromSharedPreferences();


        //TODO autocomplate yi düzenle
        //TODO material design a komple geçiş yap


    }


    public void getDataFromSharedPreferences() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Cat>> call = jsonPlaceHolderApi.getCats();


        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {

                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());

                    return;
                }
                List<Cat> cats = response.body();

                ArrayList<Cat> a1 = new ArrayList<Cat>(cats);

                for (Cat cat : a1) {

                    try {

                        preferences = PreferenceManager.getDefaultSharedPreferences(Favorites.this);

                        Set<String> favorites = preferences.getStringSet("favorites", new HashSet<String>());

                        if (favorites.contains(cat.getName())) {

                            data.add(new Cat(cat.getName(), cat.getImage().getUrl(), cat.getDescription(), cat.getOrigin(), cat.getLifeSpan(), cat.getDogFriendly(),
                                    cat.getTemperament(), cat.getWikipediaUrl()));

                            catsName.add(cat.getName());

                        }

                    }
                    catch(Exception e) {
                        //  Block of code to handle errors
                    }

                    //textViewResult.append(content);
                }
                setCatRecycler(data);

            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                //textViewResult.setText(t.getMessage());

            }
        });

    }

    public void setCatRecycler(ArrayList<Cat> cats) {

        favoritesRecyclerview = findViewById(R.id.favoritesRecyclerview);

        CatRecyclerviewAdapter adapter = new CatRecyclerviewAdapter(cats,this);
        adapter.setDepartments(cats);

        favoritesRecyclerview.setAdapter(adapter);
        favoritesRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    }
    }
