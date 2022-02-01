package com.example.placementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    Button login,signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);

        login.setOnClickListener(v -> {
            String name1= editText1.getText().toString();
            String pl=editText2.getText().toString();
            ParseUser.logInInBackground(name1, pl, (user, e) -> {
                if(user != null)
                {
                    Toast.makeText(MainActivity.this, "Welcome to the App", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,TeacherDash.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        signup.setOnClickListener(view -> {
            if(editText1.getText().toString().endsWith("@rvce.edu.in"))
            {
                try {
                    getSignUp();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getSignUp() throws ParseException {
        ParseQuery<ParseObject> q1 = ParseQuery.getQuery("UnplacedStudents");
        ParseQuery<ParseObject> q2 = ParseQuery.getQuery("PlacedStudentData");
        ParseQuery<ParseObject> q3 = ParseQuery.getQuery("Department");
        ParseQuery<ParseObject> q4 = ParseQuery.getQuery("Alumni");
        String emlS = "email";
        String emlL=editText1.getText().toString();
        if(q1.whereEqualTo(emlS,emlL).count() == 1)
        {
            q1.getFirstInBackground((object, e) -> callSignUp(object.get("sname").toString()));
        }
        else if(q2.whereEqualTo(emlS,emlL).count() == 1)
        {
            q2.getFirstInBackground((object, e) -> callSignUp(object.get("sname").toString()));
        }
        else if(q4.whereEqualTo(emlS,emlL).count() == 1)
        {
            q4.getFirstInBackground((object, e) -> callSignUp(object.get("sname").toString()));
        }
        else if(q3.whereEqualTo(emlS,emlL).count() == 1)
        {
            q3.getFirstInBackground((object, e) -> callSignUp(object.get("name").toString()));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Email Id is not in Database", Toast.LENGTH_SHORT).show();
        }
    }
    void callSignUp(String kml)
    {
        String name1 = editText1.getText().toString();
        String pl = editText2.getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(name1);
        user.setPassword(pl);
        user.setEmail(name1);
        user.signUpInBackground(e -> {
            if (e != null) {
                ParseUser.logOut();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Signed in as "+kml, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
