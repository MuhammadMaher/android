package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    LogOut logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        logOut=new LogOut();
//
//        logOut.onOptionsItemSelected();

    }

    public void logIn(View view) {
        Intent intent =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);

    }

    public void Register(View view) {
        Intent intent =new Intent(MainActivity.this,RegisterScreen.class);
        startActivity(intent);

    }
    @BindingAdapter("glide")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView).load(imageUrl).into(imageView);
    }

}