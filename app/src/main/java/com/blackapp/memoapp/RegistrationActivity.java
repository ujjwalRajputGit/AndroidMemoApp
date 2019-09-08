package com.blackapp.memoapp;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

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

public class RegistrationActivity extends AppCompatActivity {

    EditText confpass_txt,email_txt,pass_txt;
    Button signup_button;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        confpass_txt = (EditText)findViewById(R.id.confpass_txt);
        email_txt = (EditText)findViewById(R.id.email_txt);
        pass_txt = (EditText)findViewById(R.id.pass_txt);
        signup_button = (Button)findViewById(R.id.signup_btn);

        firebaseAuth = FirebaseAuth.getInstance();

       signup_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String email = email_txt.getText().toString().trim();
               String password = pass_txt.getText().toString().trim();
               String confirmpassword = confpass_txt.getText().toString().trim();

               if (TextUtils.isEmpty(email))
               {
                   Toast.makeText(RegistrationActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                   return;
               }
               if (TextUtils.isEmpty(password))
               {
                   Toast.makeText(RegistrationActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                   return;
               }
               if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   Toast.makeText(RegistrationActivity.this, "Please enter a valid emil", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (password.length()<6){

                   Toast.makeText(RegistrationActivity.this, "enter atleast 6 charater long password", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (TextUtils.isEmpty(confirmpassword)){
                   Toast.makeText(RegistrationActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (password.equals(confirmpassword)){

                   firebaseAuth.createUserWithEmailAndPassword(email, password)
                           .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       Toast.makeText(RegistrationActivity.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                                       finish();

                                   } else {
                                       Toast.makeText(RegistrationActivity.this, "ERROR:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }

                               }
                           });

               }
               else
               {
                   Toast.makeText(RegistrationActivity.this, "password do not match", Toast.LENGTH_SHORT).show();
                   return;
               }

           }
       });
    }
}
