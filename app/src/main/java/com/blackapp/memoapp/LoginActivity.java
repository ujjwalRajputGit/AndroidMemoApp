package com.blackapp.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button register_btn,login_btn;
    EditText email_txt,password_txt;
    private FirebaseAuth firebaseAuth;
//    final ProgressDialog progressDialog = new ProgressDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register_btn = (Button)findViewById(R.id.rst_btn);
        login_btn = (Button)findViewById(R.id.login_btn);
        email_txt = (EditText) findViewById(R.id.email_txt);
        password_txt = (EditText)findViewById(R.id.pass_txt);

        firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();
                
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Please enter a valid emil", Toast.LENGTH_SHORT).show();
                    return;
                }

//                progressDialog.setMessage("Logging in, please wait...");
//                progressDialog.show();


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this,MemoActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(LoginActivity.this, "ERROR:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            }
                        });
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}
