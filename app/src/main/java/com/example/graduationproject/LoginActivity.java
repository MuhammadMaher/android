package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.graduationproject.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void userLogin(View view) {
        String email = binding.tfEmail.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "email Required", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = binding.tfPassword.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(this, "password Required", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        finish();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getLocalizedMessage();
                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onFailure: " + error);
                    }
                });


    }

//    public void logOut(View view) {
//
//        firebaseAuth.signOut();
//        if (firebaseAuth.getCurrentUser() == null) {
//            // go to login screen
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

