package com.example.placementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class UnplacedDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unplaced_dash);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
    public void viewup(View view) {
        Intent teachview= new Intent(getApplicationContext(),Teachview.class);
        startActivity(teachview);
    }
}