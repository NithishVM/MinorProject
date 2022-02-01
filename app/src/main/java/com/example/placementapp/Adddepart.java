package com.example.placementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Objects;

public class Adddepart extends AppCompatActivity {
    private EditText phone_up,email_up,student_name,branch,desg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddepart);
        Objects.requireNonNull(getSupportActionBar()).hide();
        phone_up = findViewById(R.id.phone_d);
        email_up = findViewById(R.id.email_d);
        student_name = findViewById(R.id.name_d);
        branch = findViewById(R.id.branch_d);
        desg = findViewById(R.id.desg_d);
        Button sendDatabtn = findViewById(R.id.addata);
        sendDatabtn.setOnClickListener(v -> {
            String name = student_name.getText().toString();
            String email = email_up.getText().toString();
            String branchup = branch.getText().toString();
            String ph = phone_up.getText().toString();
            String designation = desg.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ph) || TextUtils.isEmpty(email) || TextUtils.isEmpty(branchup) || TextUtils.isEmpty(designation)) {
                Toast.makeText(Adddepart.this, "Please add All Fields", Toast.LENGTH_SHORT).show();
            } else {
                if(email.endsWith("@rvce.edu.in"))
                {
                    try {
                        addParsePaced(name, email, branchup, ph,designation);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void addParsePaced(String name1, String email1, String branch1, String phone1,String desg1) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Department");
        ParseObject entity = new ParseObject("Department");

        if(query.whereEqualTo("email",email1).count()== 0)
        {
            entity.put("name", name1);
            entity.put("email", email1);
            entity.put("branch", branch1);
            entity.put("phone", phone1);
            entity.put("desg",desg1);

            entity.saveInBackground(k -> {
                if(k==null)
                {
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), ""+k.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Email already Exits", Toast.LENGTH_SHORT).show();
        }
    }
}