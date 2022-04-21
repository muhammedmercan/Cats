package com.example.cats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowDetailOfCat extends AppCompatActivity {

    TextView txtTitleDetail, txtDescriptionDetail;
    ImageView imgDetail;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_of_cat);

        intent = getIntent();

        initViews();
        setData();
    }


    public void initViews() {
        txtTitleDetail = findViewById(R.id.txtTitleDetail);
        txtDescriptionDetail = findViewById(R.id.txtDescriptionDetail);
        imgDetail = findViewById(R.id.imgDetail);

    }

    public void setData() {

        txtTitleDetail.setText(intent.getStringExtra("name"));
        txtDescriptionDetail.setText(intent.getStringExtra("description"));
        Glide.with(this).load(intent.getStringExtra("url")).into(imgDetail);





    }

}