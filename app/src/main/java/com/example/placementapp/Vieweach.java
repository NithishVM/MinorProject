package com.example.placementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView text1,t2,t3,t4,t5;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieweach);
        text1= findViewById(R.id.datatext);
        t2= findViewById(R.id.datatext2);
        t3= findViewById(R.id.datatext3);
        t4= findViewById(R.id.datatext4);


        Intent i= getIntent();
        String name= i.getStringExtra("stud_name");
        text1.setText(name);

        button= findViewById(R.id.button);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("StudentDetails").child(name);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.getKey().equalsIgnoreCase("sname")) {
                        Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                        text1.setText(snapshot1.getValue().toString());
                    }
                    if (snapshot1.getKey().equalsIgnoreCase("cname")) {
                        Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                        t2.setText(snapshot1.getValue().toString());
                    }
                    if (snapshot1.getKey().equalsIgnoreCase("ctc")) {
                        Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                        t3.setText(snapshot1.getValue().toString());
                    }
                    if (snapshot1.getKey().equalsIgnoreCase("role")) {
                        Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                        t4.setText(snapshot1.getValue().toString());
                    }
                    if(snapshot1.getKey().equalsIgnoreCase("urlid")){
                        String urlIDintent= snapshot1.getValue().toString();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intentPDF=new Intent(Vieweach.this,Pdfactivity.class);
                                intentPDF.putExtra("urlval",urlIDintent);
                                startActivity(intentPDF);
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}