package com.example.christhu.cube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Lis extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis);

        alName = new ArrayList<>(Arrays.asList("Biriyani", "Crispy Chicken ", "Fizzy...", "Cool...", "Softy...", "Fruity...", "Fresh...", "Sticky..."));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.bi, R.drawable.ch, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a));

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.resend_code);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(Lis.this, alName, alImage);
        mRecyclerView.setAdapter(mAdapter);

    }


}