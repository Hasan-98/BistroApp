
package com.hasan.bistroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {
    EditText userETlogin , passETlogin;
    Button loginBtn , registerBtn;

    FirebaseAuth auth;

    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Intent i = new Intent(Login_Activity.this ,MainActivity.class );
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        userETlogin = findViewById(R.id.username);
        passETlogin = findViewById(R.id.password);
        loginBtn = findViewById(R.id.submit);
        registerBtn = findViewById(R.id.new_member);

        auth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_Activity.this , RegisterActivity.class);
                startActivity(i) ;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email__text = userETlogin.getText().toString();
                String pass_text = passETlogin.getText().toString();


                //checking if it is empty
                if (TextUtils.isEmpty(email__text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(Login_Activity.this , "Please fill all fields " ,Toast.LENGTH_SHORT);
                }
                else{
                    auth.signInWithEmailAndPassword(email__text , pass_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent i = new Intent(Login_Activity.this , MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();


                                    }
                                    else{
                                        Toast.makeText(Login_Activity.this , "LogIn Failed" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}