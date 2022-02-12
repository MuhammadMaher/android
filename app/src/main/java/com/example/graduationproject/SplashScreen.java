package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity{

    private static final String TAG = "SplashScreen";
FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);

                    Intent intent;

                    if (firebaseAuth.getCurrentUser() == null){
                        intent = new Intent(SplashScreen.this, HomeActivity.class);
                    //
                    }
                    else{
                        intent = new Intent(SplashScreen.this, LoginActivity.class);
                    }
                    startActivity(intent);
                } catch (InterruptedException e) {
                    Log.i(TAG, "run: "+e.getLocalizedMessage());
                }
            }
        });
        thread.start();

    }


}