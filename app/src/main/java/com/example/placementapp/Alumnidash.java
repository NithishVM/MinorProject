package com.example.placementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class Alumnidash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnidash);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
    public void viewpd(View view) {
        Intent teachview= new Intent(getApplicationContext(),Teachview.class);
        startActivity(teachview);
    }
    public void addpd(View view)
    {
        Intent addview=new Intent(getApplicationContext(),Adddetails.class);
        startActivity(addview);
    }
}