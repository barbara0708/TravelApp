package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack;
    TextView tvReg;
    EditText etLogEmail,etLogPassword;
    Button btnLogin;
    public static boolean logged;
    TextView tvForgot;
    MainActivity mainActivity;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logged=false;
        mainActivity=new MainActivity();
        tvForgot=findViewById(R.id.tvForgot);
        tvReg=findViewById(R.id.tvReg);
        btnLogin=findViewById(R.id.btnLogin);
        imgBack=findViewById(R.id.imgBack);
        etLogEmail=findViewById(R.id.etLogEmail);
        etLogPassword=findViewById(R.id.etLogPassword);
        mAuth=FirebaseAuth.getInstance();

        imgBack.setOnClickListener(this);
        tvReg.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvReg:
                startActivity(new Intent(getApplicationContext(),Registration.class));
                break;
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.tvForgot:
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                break;
            case R.id.imgBack:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private void userLogin() {
        String email=etLogEmail.getText().toString().trim();
        String password=etLogPassword.getText().toString().trim();
        if(email.isEmpty()){
            etLogEmail.setError("Email is required");
            etLogEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etLogPassword.setError("Password is required");
            etLogPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etLogEmail.setError("Please provide valid email");
            etLogEmail.requestFocus();
            return;
        }
        if(password.length()<6){
            etLogPassword.setError("Min password length is 6!");
            etLogPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                Toast.makeText(getApplicationContext(), "You logged in!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("logged",true));
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(getApplicationContext(), "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                            }
                            logged=true;
                        }else {
                            Toast.makeText(getApplicationContext(), "Failed to login! Check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}