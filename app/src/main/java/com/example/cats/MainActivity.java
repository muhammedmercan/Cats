package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView catRecycler;
    public ArrayList<Cat> data;
    public ArrayList<String> catsName;
    public AutoCompleteTextView multiAutoCompleteTextView;
    public ImageView imgGoFavorites;
    public String selectedCat;
    public Call<List<Cat>> call;


    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Cats);
        setContentView(R.layout.activity_main);
        data = new ArrayList<Cat>();
        catsName = new ArrayList<>();


        initViews();
        getDataFromApi();
        setAutoCompleteContent();





        multiAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, multiAutoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                data.clear();
                selectedCat = multiAutoCompleteTextView.getText().toString();
                getDataFromApi();



            }
        });

        imgGoFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Favorites.class);
                startActivity(intent);

            }
        });

        //TODO autocomplate yi düzenle

    }

    public void initViews() {
        multiAutoCompleteTextView = findViewById(R.id.search);
        imgGoFavorites = findViewById(R.id.imgGoFavorites);
    }

    public void setAutoCompleteContent() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,catsName);

        multiAutoCompleteTextView.setThreshold(1);//will start working from first character
        multiAutoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    public void getDataFromApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);



        if (!(selectedCat == null) ) {

            call = jsonPlaceHolderApi.getCats(selectedCat);

        }

        else {
            call = jsonPlaceHolderApi.getCats();
        }
        call.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                //https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg

                List<Cat> cats = response.body();

                ArrayList<Cat> a1 = new ArrayList<Cat>(cats);


                for (Cat cat : a1) {

                    try {

                        if (a1.size() == 1) {
                            data.add(new Cat(cat.getName(),"https://cdn2.thecatapi.com/images/" + cat.getReferenceImageId() + ".jpg", cat.getDescription(), cat.getOrigin(),cat.getLifeSpan(),cat.getDogFriendly(),
                                    cat.getTemperament(),cat.getWikipediaUrl()));
                        }

                        else {
                            data.add(new Cat(cat.getName(),cat.getImage().getUrl(), cat.getDescription(), cat.getOrigin(),cat.getLifeSpan(),cat.getDogFriendly(),
                                    cat.getTemperament(),cat.getWikipediaUrl()));

                        }

                        catsName.add(cat.getName());

                    }
                    catch(Exception e) {
                        System.out.println(e);

                        //  Block of code to handle errors
                    }

                }
                setCatRecycler(data);

            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {

            }
        });

    }

    public void setCatRecycler(ArrayList<Cat> cats) {

        catRecycler = findViewById(R.id.catRecycler);

        CatRecyclerviewAdapter adapter = new CatRecyclerviewAdapter(cats,this);
        adapter.setDepartments(cats);

        catRecycler.setAdapter(adapter);
        catRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}