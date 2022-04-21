package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new ArrayList<Cat>();

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
                        data.add(new Cat(cat.getName(), cat.getImage().getUrl(), cat.getDescription(), cat.getOrigin(),cat.getOrigin(),cat.getLifeSpan(),cat.getDogFriendly(),
                                cat.getTemperament(),cat.getWikipediaUrl()));
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

        //System.out.println(data.get(0).getName());


    }

    public void setCatRecycler(ArrayList<Cat> cats) {

        catRecycler = findViewById(R.id.catRecycler);

        CatRecyclerviewAdapter adapter = new CatRecyclerviewAdapter(cats,this);
        adapter.setDepartments(cats);

        catRecycler.setAdapter(adapter);
        catRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}