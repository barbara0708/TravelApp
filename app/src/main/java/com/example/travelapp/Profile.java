package com.example.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    CircleImageView image_profile;
    ImageView arrow;
    public static Uri uri;
    EditText fName,fEmail,fNumber, fPassword;
    Button btnChangeData;
    public static final int PICK_IMAGE = 1;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;
    String name, number, email, password,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fPassword=findViewById(R.id.password);
        image_profile=findViewById(R.id.image_profile);
        btnChangeData=findViewById(R.id.btnChangeData);
        arrow=findViewById(R.id.arrow);
        fName=findViewById(R.id.name);
        fEmail=findViewById(R.id.email);
        fNumber=findViewById(R.id.number);
        btnChangeData.setOnClickListener(this);
        image_profile.setOnClickListener(this);
        arrow.setOnClickListener(this);
        if(uri!=null){
            image_profile.setImageURI(uri);
        }
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b90ea-default-rtdb.firebaseio.com/");
        userID=user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile!=null){
                    name=userProfile.getName();
                    number=userProfile.getNumber();
                    email=userProfile.getEmail();
                    password=userProfile.getPassword();
                    gender=userProfile.getGender();
                    if(gender.equals("male")){
                        image_profile.setImageResource(R.drawable.man);
                    }else {
                        image_profile.setImageResource(R.drawable.woman);
                    }
                    fName.setText(name);
                    fEmail.setText(email);
                    fNumber.setText(number);
                    fPassword.setText(password);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            uri=data.getData();
            image_profile.setImageURI(uri);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.image_profile:
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),PICK_IMAGE);*/
                break;
            case R.id.btnChangeData:
                updateUser();
                break;

        }
    }

    private void updateUser() {
        String newName=fName.getText().toString().trim();
        String newEmail=fEmail.getText().toString().trim();
        String newNumber=fNumber.getText().toString().trim();
        String newPassword=fPassword.getText().toString().trim();
        if(newName.isEmpty()){
            fName.setError("Name is required");
            fName.requestFocus();
            return;
        }
        if(newEmail.isEmpty()){
            fEmail.setError("Email is required");
            fEmail.requestFocus();
            return;
        }
        if(newNumber.isEmpty()){
            fNumber.setError("Number is required");
            fNumber.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
            fEmail.setError("Please provide a valid email");
            fEmail.requestFocus();
            return;
        }
        if(!email.equals(newEmail)){
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(newEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "User email address updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(), "User password updated", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
        }
        if(!password.equals(newPassword)){
            AuthCredential credential=EmailAuthProvider.getCredential(newEmail,password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Error password not updated", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            }else {
                                Toast.makeText(getApplicationContext(), "Error auth failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
            //user.reauthenticate(credential)

        boolean nameBool=reference.child(userID).child("name").setValue(newName).isComplete();
        boolean emailBool=reference.child(userID).child("email").setValue(newEmail).isSuccessful();
        boolean numberBool=reference.child(userID).child("number").setValue(newNumber).isSuccessful();
        reference.child(userID).child("password").setValue(newPassword);
        if(!nameBool&&!emailBool&&!numberBool){
            Toast.makeText(getApplicationContext(), "Data has been saved successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}