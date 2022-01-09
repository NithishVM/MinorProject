package com.example.placementapp;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachview);
        listView = findViewById(R.id.listview);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
        ArrayList sl= new ArrayList<>(Arrays.asList("Hello","Hi","New York"));

        for (int i = 0; i < sl.size(); i++) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("StudentDetails").child(Arrays.asList(sl).toString());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.getKey().equalsIgnoreCase("sname")) {
                            Toast.makeText(getApplicationContext(), snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                            list.add(snapshot1.getValue().toString());
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
//        }
        }
    }
}