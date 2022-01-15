package com.example.placementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);
    }
    public void addupdata(View view)
    {
        Intent intentfg=new Intent(getApplicationContext(),Addunplaced.class);
        startActivity(intentfg);
    }
    public void AddPlaced(View view)
    {
        Intent AddUnplaced=new Intent(AdminSettings.this,Addplaced.class);
        startActivity(AddUnplaced);
    }
}