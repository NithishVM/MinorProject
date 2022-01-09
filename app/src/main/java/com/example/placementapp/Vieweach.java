package com.example.placementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vieweach extends AppCompatActivity {
    TextView editText,textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieweach);
        editText= findViewById(R.id.datatext);
        textView= findViewById(R.id.textView4);
        Intent i= getIntent();
        String name= i.getStringExtra("stud_name");
        editText.setText(name);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("StudentDetails");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.getKey().equalsIgnoreCase("sname")) {
                        Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                        textView.setText(snapshot1.getValue().toString());
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}