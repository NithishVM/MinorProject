package com.example.placementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TeacherDash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dash);
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