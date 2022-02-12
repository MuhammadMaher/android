package com.example.graduationproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.graduationproject.databinding.ActivityRegisterScreenBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterScreen extends AppCompatActivity {
    private static final String TAG = "RegisterScreen";
    ActivityRegisterScreenBinding binding;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;

    String firstName,lastName, email, imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding= DataBindingUtil.setContentView(this,R.layout.activity_register_screen);
    }

    public void RegisterSubmit(View view) {
        String firstName=binding.etFname.getEditText().toString();
        if (firstName.isEmpty()){
            Toast.makeText(this, "First Name required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null) {
            Toast.makeText(this, "image required", Toast.LENGTH_SHORT).show();
            return;
        }


        String lastName=binding.etLname.getEditText().getText().toString().trim();
        if(lastName.isEmpty()){
            Toast.makeText(this, "Last Name Required", Toast.LENGTH_SHORT).show();
            return;
        }
        String email=binding.etEmail.getEditText().getText().toString().trim();
        if (email.isEmpty()){
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show();
            return;
        }
        String password=binding.etPassword.getEditText().getText().toString().trim();
        if (password.isEmpty()){
            Toast.makeText(this, "Password Required", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterScreen.this, "Account Created", Toast.LENGTH_SHORT).show();
                        uploadImage();
                   }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error=e.getLocalizedMessage();
                        Toast.makeText(RegisterScreen.this, error, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onFailure: " + error);

                    }
                });
        }

        public void onSelectedImage(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            binding.imageView.setImageURI(imageUri);
        }
    }

    private void uploadImage() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        String randomId = uid + String.valueOf(System.currentTimeMillis());

        storageReference.child("userProfile").child("profileImages").child(uid).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getImageUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = e.getLocalizedMessage();
                        Toast.makeText(RegisterScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getImageUrl() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        storageReference.child("userProfile").child("profileImages").child(uid).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl = uri.toString();
                        System.out.println(imageUrl);
                        uploadUserData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String errorMessage = e.getLocalizedMessage();
                Toast.makeText(RegisterScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
          private void uploadUserData(){
            UserData user = new UserData(firstName,lastName, email,imageUrl);

            String uid = firebaseAuth.getCurrentUser().getUid();
              firebaseFirestore.collection("maher").document(uid).set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String errorMessage = e.getLocalizedMessage();
                            Toast.makeText(RegisterScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });

              Intent intent=new Intent(RegisterScreen.this,LoginActivity.class);
              startActivity(intent);
        }


    }
