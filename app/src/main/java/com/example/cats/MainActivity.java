package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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


    //TODO tanımlamalarda private public koymaya gerek var mı diye araştır
    private RecyclerView catRecycler;
    public ArrayList<Cat> data;
    public ArrayList<String> catsName;
    public AutoCompleteTextView multiAutoCompleteTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new ArrayList<Cat>();
        catsName = new ArrayList<>();

        multiAutoCompleteTextView = findViewById(R.id.search);

        getDataFromApi();
        setAutoCompleteContent();


        multiAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, multiAutoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });





        //TODO autocomplate yi düzenle
        //TODO material design a komple geçiş yap


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
                //setCatRecycler(a1);

                for (Cat cat : a1) {

                    try {
                        data.add(new Cat(cat.getName(), cat.getImage().getUrl(), cat.getDescription(), cat.getOrigin(),cat.getLifeSpan(),cat.getDogFriendly(),
                                cat.getTemperament(),cat.getWikipediaUrl()));

                        catsName.add(cat.getName());



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

        catRecycler = findViewById(R.id.catRecycler);

        CatRecyclerviewAdapter adapter = new CatRecyclerviewAdapter(cats,this);
        adapter.setDepartments(cats);

        catRecycler.setAdapter(adapter);
        catRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}