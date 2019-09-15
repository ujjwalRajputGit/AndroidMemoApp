package com.blackapp.memoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder>{

    private String[] memo;
    public MemoAdapter(String[] memo)
    {
       this.memo = memo;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_memo_layout, parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {

        String title = memo[position];
        holder.memo_title.setText(title);

    }

    @Override
    public int getItemCount() {
        return memo.length;
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder{

        TextView memo_title , memo_desc;
        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            memo_title = itemView.findViewById(R.id.memo_title);
            memo_desc = itemView.findViewById(R.id.memo_desc);
        }
    }

}

