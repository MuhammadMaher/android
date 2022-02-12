package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduationproject.RecyclerView.RecyclerViewClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity{
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void CriminalInfo(View view) {
    }

    public void myProfile(View view) {

        Intent intent =new Intent(HomeActivity.this,update.class);
        startActivity(intent);
    }

    public void adminInfo(View view) {
    }


    public void getSomeNews(View view) {

        Intent intent =new Intent(HomeActivity.this, RecyclerViewClass.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (item.getItemId() == R.id.item_profile) {
            Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, update.class));
        }
        return super.onOptionsItemSelected(item);
    }
}