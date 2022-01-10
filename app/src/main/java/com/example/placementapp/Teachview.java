package com.example.placementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Teachview extends AppCompatActivity {

    ListView listView;
    ArrayList<String> names;
    ArrayList<String> list;
    ArrayList<String> name_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachview);
        ProgressDialog load= new ProgressDialog(this);
        load.setMessage("Loading");
        load.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                load.dismiss();
            }
        }, 3000);
        listView = findViewById(R.id.listview);

        list = new ArrayList<>();
        name_list = new ArrayList<>();
        names = new ArrayList<>();

        DatabaseReference referencen = FirebaseDatabase.getInstance().getReference("StudentDetails");

        referencen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snaps : snapshot.getChildren()) {
                        name_list.add(snaps.getKey());
                }
                getInData();
                names=name_list;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), Vieweach.class);
                String testAd= adapterView.getItemAtPosition(i).toString();
                intent.putExtra("stud_name", testAd);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Clicked on "+testAd,Toast.LENGTH_SHORT).show();
            }
        });
        }

        public  void  getInData()
        {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            for(int i=0;i< name_list.size(); i++) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("StudentDetails").child(name_list.get(i));
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            {
                                if(snapshot1.getKey().equalsIgnoreCase("sname"))
                                list.add(snapshot1.getValue().toString());
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }
