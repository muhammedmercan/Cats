package com.example.cats;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class CatRecyclerviewAdapter extends RecyclerView.Adapter<CatRecyclerviewAdapter.MyViewHolder> {

    private ArrayList<Cat> cats;
    private Context context;


    public CatRecyclerviewAdapter(ArrayList<Cat> cats, Context context) {
        this.cats = cats;
        this.context = context;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imageView, imgFavIcon;
        CardView parent;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imageView = itemView.findViewById(R.id.img);
            imgFavIcon = itemView.findViewById(R.id.imgFavIcon);

            imgFavIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    imgFavIcon.
                }
            });




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