package com.blackapp.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class new_memo extends AppCompatActivity {

    EditText nmtitle,nmdesc;
    Button nmaddnotes;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        nmtitle = findViewById(R.id.new_memo_title);
        nmdesc = findViewById(R.id.new_memo_desc);
        nmaddnotes = findViewById(R.id.newaddmemo_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("memo").child(firebaseAuth.getCurrentUser().getUid());

        nmaddnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String title = nmtitle.getText().toString().trim();
            String desc = nmdesc.getText().toString().trim();

            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc))
            {
              addnotes(title,desc);
            }
            else
            {
                Toast.makeText(new_memo.this, "Fill empty fields", Toast.LENGTH_SHORT).show();
            }


            }
        });


    }

    private void addnotes(String title, String desc)
    {
        if (firebaseAuth.getCurrentUser() != null)
        {
         DatabaseReference newMemoRef = databaseReference.push();

            Map memoMap = new HashMap();
            memoMap.put("title", title);
            memoMap.put("desc",desc);

            newMemoRef.setValue(memoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(new_memo.this, "Memo is added", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(new_memo.this, "ERROR"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {
            Toast.makeText(this, "User is not SIGNED IN", Toast.LENGTH_SHORT).show();
        }
    }
}
