package com.example.placementapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Objects;

public class Addunplaced extends AppCompatActivity {
    private EditText phone_up,email_up,student_name,branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addunplaced);
        Objects.requireNonNull(getSupportActionBar()).hide();

        phone_up = findViewById(R.id.phone_d);
        email_up = findViewById(R.id.email_d);
        student_name = findViewById(R.id.name_d);
        branch = findViewById(R.id.branch_d);

        Button sendDatabtn = findViewById(R.id.addata);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = student_name.getText().toString();
                String email = email_up.getText().toString();
                String branchup = branch.getText().toString();
                String ph = phone_up.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ph) || TextUtils.isEmpty(email) || TextUtils.isEmpty(branchup)) {
                    Toast.makeText(Addunplaced.this, "Please add All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(email.endsWith("@rvce.edu.in"))
                    {
                        try {
                            addParseUnplaced(name, email, branchup, ph);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    void addParseUnplaced(String name1, String email1, String branch1, String phone1) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnplacedStudents");
        ParseObject entity = new ParseObject("UnplacedStudents");

        if(query.whereEqualTo("email",email1).count()== 0)
        {
            entity.put("sname", name1);
            entity.put("email", email1);
            entity.put("branch", branch1);
            entity.put("phone", phone1);

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