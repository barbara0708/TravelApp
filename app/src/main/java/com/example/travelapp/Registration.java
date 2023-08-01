package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText etName,etEmail, etNumber, etPassword;
    ImageView imgProfile,imgArrow;
    Button btnCreate;
    TextView tvLogin;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        gender="male";
        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference=db.getReference(User.class.getSimpleName());

        imgArrow=findViewById(R.id.imgArrow);
        imgProfile=findViewById(R.id.imgProfile);
        etName=findViewById(R.id.name);
        etEmail=findViewById(R.id.email);
        etNumber=findViewById(R.id.number);
        etPassword=findViewById(R.id.password);
        btnCreate=findViewById(R.id.btnCreate);
        tvLogin=findViewById(R.id.tvLogin);

        btnCreate.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgArrow.setOnClickListener(this);
        openDialog();
    }

    private void openDialog() {
        AlertDialog alertDialog=new AlertDialog.Builder(Registration.this).create();
        alertDialog.setTitle("Profile Image");
        alertDialog.setMessage("You can change your profile photo by clicking on the picture");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLogin:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.btnCreate:
                registerUser();
                break;
            case R.id.imgProfile:
                changePhoto();
                break;
            case R.id.imgArrow:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
    }

    private void changePhoto() {
        if(gender.equals("male")){
            gender="female";
            imgProfile.setImageResource(R.drawable.woman);
        }else {
            gender="male";
            imgProfile.setImageResource(R.drawable.man);
        }


    }

    private void registerUser() {

        String fullName=etName.getText().toString().trim();
        String emailAddress=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        String number=etNumber.getText().toString().trim();
        if(number.isEmpty()){
            etNumber.setError("Phone Number is required!");
            etNumber.requestFocus();
            return;
        }
        if (fullName.isEmpty()){
            etName.setError("Full Name is required!");
            etName.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if(emailAddress.isEmpty()){
            etEmail.setError("Email Address is required");
            etEmail.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(Registration.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(fullName,number,emailAddress,password,gender);
                            FirebaseDatabase.getInstance().getReferenceFromUrl("https://travel-app-e4d67-default-rtdb.firebaseio.com/")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Registration.this,Login.class));
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Registration is failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(getApplicationContext(), "Registration is failed! Try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}