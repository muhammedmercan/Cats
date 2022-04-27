package com.example.cats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.cats.Cat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CatRecyclerviewAdapter extends RecyclerView.Adapter<CatRecyclerviewAdapter.MyViewHolder> {

    private ArrayList<Cat> cats;
    private Context context;
    private Set<String> favorites;
    SharedPreferences preferences;


    public CatRecyclerviewAdapter(ArrayList<Cat> cats, Context context) {
        this.cats = cats;
        this.context = context;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imageView, imgFavIcon;
        CardView parent;
        String catName;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imageView = itemView.findViewById(R.id.img);
            imgFavIcon = itemView.findViewById(R.id.imgFavIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ShowDetailOfCat.class);
                    intent.putExtra("name",cats.get(getLayoutPosition()).getName());
                    intent.putExtra("url",cats.get(getLayoutPosition()).getUrl());
                    intent.putExtra("description",cats.get(getLayoutPosition()).getDescription());
                    intent.putExtra("origin",cats.get(getLayoutPosition()).getOrigin());
                    intent.putExtra("lifeSpan",cats.get(getLayoutPosition()).getLifeSpan());
                    intent.putExtra("dogFriendly",cats.get(getLayoutPosition()).getDogFriendly());
                    intent.putExtra("temperament",cats.get(getLayoutPosition()).getTemperament());
                    intent.putExtra("wikipediaUrl",cats.get(getLayoutPosition()).getWikipediaUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CatRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  CatRecyclerviewAdapter.MyViewHolder holder, int position) {
        holder.txtName.setText(cats.get(position).getName());
        Glide.with(context).load(cats.get(position).getUrl()).into(holder.imageView);
        context = holder.imgFavIcon.getContext();

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        favorites = preferences.getStringSet("favorites", new HashSet<String>());

        if (favorites.contains(cats.get(position).getName())) {

            Glide.with(context).load(android.R.drawable.btn_star_big_on).into(holder.imgFavIcon);
        }

        else {
            Glide.with(context).load(android.R.drawable.star_off).into(holder.imgFavIcon);
        }

        holder.imgFavIcon.setOnClickListener(null);

        holder.imgFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favorites.contains(cats.get(holder.getAdapterPosition()).getName())) {

                    holder.imgFavIcon.setImageResource(android.R.drawable.star_off);
                    favorites.remove(cats.get(holder.getAdapterPosition()).getName());


                    preferences = PreferenceManager.getDefaultSharedPreferences(context);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putStringSet("favorites",favorites);
                    editor.apply();

                }
                else {
                    holder.imgFavIcon.setImageResource(android.R.drawable.btn_star_big_on);
                    favorites.add(cats.get(holder.getAdapterPosition()).getName());

                    preferences = PreferenceManager.getDefaultSharedPreferences(context);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putStringSet("favorites",favorites);
                    editor.apply();


                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void setDepartments(ArrayList<Cat> departments) {
        this.cats = departments;
        notifyDataSetChanged();
    }
}