package com.hasan.bistroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText userET , passET , emailET;
    Button registerBtn;

    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userET = findViewById(R.id.PersonName);
        passET = findViewById( R.id.Password);
        emailET = findViewById(R.id.EmailAddress);
        registerBtn = findViewById(R.id.button);


        //Firebase AUth
        auth = FirebaseAuth.getInstance();

        //adding event listner to button register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = emailET.getText().toString();
                String password  =passET.getText().toString();
                if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this , "Please fill all fields " ,Toast.LENGTH_SHORT);

                }else{
                    RegisterNow(email_text , password);
                }


            }
        });
    }
    private void RegisterNow(final String email , final String password){
        auth.createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser  = auth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            myRef = FirebaseDatabase.getInstance().getReference("Myusers").child(userID);

                            //hashmaps
                            HashMap<String , String> hashMap = new HashMap<>();
                            hashMap.put("id" , userID);
                            hashMap.put("email" , email);
                            hashMap.put("imageURL" , "default");

                            // opening main activity after success registration
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent i = new Intent(RegisterActivity.this , MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();


                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegisterActivity.this , "Invalid Email or password" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}