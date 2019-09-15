package com.blackapp.memoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class MemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        RecyclerView memolist = findViewById(R.id.memolist);
        memolist.setLayoutManager(new LinearLayoutManager(this));
        String[] data = {"java","bjvfj","nfjgf","bfjb","njfbu"};
        memolist.setAdapter(new MemoAdapter(data));
    }
}
