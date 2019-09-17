package com.blackapp.memoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemoActivity extends AppCompatActivity {

    FloatingActionButton addmemo_btn;
    List<MemoData> data;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    RecyclerView memolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        addmemo_btn = findViewById(R.id.addmemo_btn);
        memolist = findViewById(R.id.memolist);
        memolist.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("memo").child(firebaseAuth.getCurrentUser().getUid());
        }
        updateUI();

          data = new ArrayList<>();

        addmemo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoActivity.this , new_memo.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String key=datas.getKey();
                    //data.add(new MemoData(key,"key"));
                    //memolist.setAdapter(new MemoAdapter(data));
                    databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String title = dataSnapshot.child("title").getValue().toString();
                            String desc = dataSnapshot.child("desc").getValue().toString();

                            data.add(new MemoData(title,desc));
                            memolist.setAdapter(new MemoAdapter(data));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }






    private void updateUI(){

        if (firebaseAuth != null){
            Toast.makeText(MemoActivity.this, "user logined", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MemoActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(MemoActivity.this, "User not log in", Toast.LENGTH_SHORT).show();
        }
    }
}
