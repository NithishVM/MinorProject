package com.example.placementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    Button button,button2;

    String teacher[]={"andhedharani@rvce.edu.in","vijayalakshmi@rvce.edu.in","deepikak@rvce.edu.in"};
    String student[]={"nithishvm.mca20@rvce.edu.in","vidyaaradhya.mca20@rvce.edu.in","teststudent@rvce.edu.in"};
    String up[]={"rajath.mca20@rvce.edu.in"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        button=findViewById(R.id.login);
        button2=findViewById(R.id.signup);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1= editText1.getText().toString();
                String pl=editText2.getText().toString();
                String ends_with= "@rvce.edu.in";

                ParseUser.logInInBackground(name1, pl, (user, e) -> {
                    if(user != null)
                    {
                        if(Arrays.asList(teacher).contains(name1) || Arrays.asList(student).contains(name1)){
                        Toast.makeText(MainActivity.this, "Welcome "+name1+"To the App", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,TeacherDash.class);
                        startActivity(intent);
                        finish();}
                        else if(Arrays.asList(up).contains(name1) )
                        {
                            Intent intent=new Intent(MainActivity.this,UnplacedDash.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1= editText1.getText().toString();
                String pl=editText2.getText().toString();
                if(Arrays.asList(teacher).contains(name1) || Arrays.asList(student).contains(name1) ||  Arrays.asList(up).contains(name1)){
                    ParseUser user=new ParseUser();
                    user.setUsername(name1);
                    user.setPassword(pl);
                    user.setEmail(name1);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null)
                            {
                                ParseUser.logOut();
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (Arrays.asList(student).contains(name1)) {
                                    Toast.makeText(MainActivity.this, "Successfully Signed Up as Student", Toast.LENGTH_SHORT).show();
                                }
                                else if(Arrays.asList(teacher).contains(name1))
                                {
                                    Toast.makeText(MainActivity.this, "Successfully Signed Up as Teacher", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "User Name is not in Given Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
