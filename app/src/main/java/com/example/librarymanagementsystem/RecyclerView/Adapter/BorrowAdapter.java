package com.example.librarymanagementsystem.RecyclerView.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.RecyclerView.Model.BorrowModel;

import java.util.ArrayList;
import java.util.List;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.BorrowHolder> {
    Context context;
    List<BorrowModel> borrow = new ArrayList<>();

    public BorrowAdapter(Context context, List<BorrowModel> borrow) {
        super();
        this.context = context;
        this.borrow = borrow;
    }

    @NonNull
    @Override
    public BorrowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrow, parent, false);
        return new BorrowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowHolder holder, int position) {
        holder.tv_borrowID.setText(String.valueOf(borrow.get(position).getBorrow_id()));
        holder.tv_title.setText(borrow.get(position).getTitle());
        holder.tv_date.setText(borrow.get(position).getDate());
        holder.tv_status.setText(borrow.get(position).getStatus());
        holder.im_cover.setImageResource(borrow.get(position).getCover());
    }

    @Override
    public int getItemCount() {
        return borrow.size();
    }

    public class BorrowHolder extends RecyclerView.ViewHolder {
        ImageView im_cover;
        TextView tv_borrowID, tv_title, tv_date, tv_status;
        public BorrowHolder(@NonNull View itemView) {
            super(itemView);
            tv_borrowID = itemView.findViewById(R.id.tv_borrowID);
            im_cover = itemView.findViewById(R.id.im_cover);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_status = itemView.findViewById(R.id.tv_status);

        }
    }
}
