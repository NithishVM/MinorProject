package com.example.placementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    EditText editText1,editText2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        constraintLayout=findViewById(R.id.cLayout);
        editText1=findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        button=findViewById(R.id.button);
        //constraintLayout.setBackgroundColor(Color.YELLOW);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject person = new ParseObject("Person");
                String name1= editText1.getText().toString();
                String pl=editText2.getText().toString();
                int plf=Integer.parseInt(pl);
                person.put("name",name1);
                person.put("age", plf);
                person.saveInBackground();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
                query.getInBackground("mhPFDlCahj", new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // object will be your person
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        });
//Reading your First Data Object from Back4App
    }
}
