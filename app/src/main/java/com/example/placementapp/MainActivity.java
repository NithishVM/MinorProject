package com.example.placementapp;

import android.content.Intent;
import android.os.Bundle;
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
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    Button button,button2;
    ArrayList<String> name_list_log;
    ArrayList<String> names_log=new ArrayList<>();
    ArrayList<String> namespd=new ArrayList<>();
    ArrayList<String> names_log_pd;
    String[] teacher ={"andhedharani@rvce.edu.in","vijayalakshmi@rvce.edu.in","deepikak@rvce.edu.in","lp@rvce.edu.in"};
    String[] student ={"nithishvm.mca20@rvce.edu.in","vidyaaradhya.mca20@rvce.edu.in","teststudent@rvce.edu.in"};
    String[] up ={"rajath.mca20@rvce.edu.in"};

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
                ParseUser.logInInBackground(name1, pl, (user, e) -> {
                    if(user != null)
                    {
                        Toast.makeText(MainActivity.this, "Welcome "+name1+"To the App", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,TeacherDash.class);
                        startActivity(intent);
                        finish();
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
                getSignUp();
            }
//                String name1= editText1.getText().toString();
//                String pl=editText2.getText().toString();
//                if(Arrays.asList(names_log).contains(name1) || Arrays.asList(names_log).contains(name1) ||  Arrays.asList(up).contains(name1)){
//                    ParseUser user=new ParseUser();
//                    user.setUsername(name1);
//                    user.setPassword(pl);
//                    user.setEmail(name1);
//                    user.signUpInBackground(new SignUpCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if(e != null)
//                            {
//                                ParseUser.logOut();
//                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                if (Arrays.asList(student).contains(name1)) {
//                                    Toast.makeText(MainActivity.this, "Successfully Signed Up as Student", Toast.LENGTH_SHORT).show();
//                                }
//                                else if(Arrays.asList(teacher).contains(name1))
//                                {
//                                    Toast.makeText(MainActivity.this, "Successfully Signed Up as Teacher", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "User Name is not in Given Database", Toast.LENGTH_SHORT).show();
//                }
//            }
        });
    }

    public void getSignUp()
    {
        name_list_log=new ArrayList<>();
        names_log_pd=new ArrayList<>();
        DatabaseReference referencen = FirebaseDatabase.getInstance().getReference("UnplacedStudentData");

        referencen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snaps : snapshot.getChildren()) {
                    {
                        name_list_log.add(snaps.getKey());
                    }
                    names_log=name_list_log;
                }

                for(int k=0;k<names_log.size();k++){
                    DatabaseReference referencepd = FirebaseDatabase.getInstance().getReference("UnplacedStudentData").child(names_log.get(k));
                    referencepd.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotpd) {
                            for (DataSnapshot snapd : snapshotpd.getChildren()) {
                                if (snapd.getKey().equalsIgnoreCase("emailup")) {
                                    String name1 = editText1.getText().toString();
                                    String pl = editText2.getText().toString();
                                    ParseUser user = new ParseUser();
                                    user.setUsername(name1);
                                    user.setPassword(pl);
                                    user.setEmail(name1);
                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e != null) {
                                                ParseUser.logOut();
                                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Successfully Signed Up as Unplaced Student", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
