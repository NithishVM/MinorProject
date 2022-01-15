package com.example.placementapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Addunplaced extends AppCompatActivity {
    private EditText phone_up,email_up,student_name,branch;
    private Button sendDatabtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AddUnplacedBean employeeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addunplaced);

        phone_up = findViewById(R.id.phone_p);
        email_up = findViewById(R.id.email_p);
        student_name = findViewById(R.id.stud_name_p);
        branch = findViewById(R.id.branch_p);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UnplacedStudentData");
        employeeInfo = new AddUnplacedBean();

        sendDatabtn = findViewById(R.id.addata);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = student_name.getText().toString();
                String email = email_up.getText().toString();
                String branchup = branch.getText().toString();
                String ph = phone_up.getText().toString();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(ph) && TextUtils.isEmpty(email) && TextUtils.isEmpty(branchup)) {
                    Toast.makeText(Addunplaced.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(name, email, branchup, ph);
                }
            }
        });
    }

    private void addDatatoFirebase(String name1, String email1, String branch1, String phone1) {
        employeeInfo.setNameup(name1);
        employeeInfo.setEmailup(email1);
        employeeInfo.setBranchup(branch1);
        employeeInfo.setPhoneup(phone1);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (name1.isEmpty() || phone1.isEmpty() || branch1.isEmpty() || email1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please add all the Fields", Toast.LENGTH_SHORT).show();
                }
                 else {
                    databaseReference.child(name1).setValue(employeeInfo);
                    Toast.makeText(com.example.placementapp.Addunplaced.this, "Unplaced Student added to Database", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(com.example.placementapp.Addunplaced.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}