package com.example.placementapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Adddetails extends AppCompatActivity {
    private EditText ctc, role, sname, cname;
    private Button sendDatabtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference();
    StudentDetails employeeInfo;
    ImageView upload;
    Uri imageuri = null;
    Uri fyi;
    String uriuseable;
    String getpdfuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddetails);

        upload = findViewById(R.id.imageButton);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

        ctc = findViewById(R.id.ctc);
        cname = findViewById(R.id.cname);
        sname = findViewById(R.id.stud_name);
        role = findViewById(R.id.role);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("StudentDetails");
        employeeInfo = new StudentDetails();

        sendDatabtn = findViewById(R.id.addata);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = sname.getText().toString();
                String companyname = cname.getText().toString();
                String ctcomp = ctc.getText().toString();
                String rol = role.getText().toString();
                String urli = uriuseable;
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(name) && TextUtils.isEmpty(ctcomp)) {
                    Toast.makeText(Adddetails.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(name, companyname, ctcomp, rol,fyi);
                }
            }
        });
    }

    private void addDatatoFirebase(String name, String cname, String ctc, String role, Uri getp) {
        employeeInfo.setSname(name);
        employeeInfo.setCname(cname);
        employeeInfo.setCtc(ctc);
        employeeInfo.setRole(role);
        employeeInfo.setUrlid(getp.toString());


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.push().setValue(employeeInfo);
                Toast.makeText(Adddetails.this, "Data added to Database", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Adddetails.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(Adddetails.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

//     Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            Toast.makeText(Adddetails.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(Adddetails.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        fyi=uri;
                                    }
                                });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(Adddetails.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
