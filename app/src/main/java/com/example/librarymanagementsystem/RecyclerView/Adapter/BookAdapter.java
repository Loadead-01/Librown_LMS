package com.example.librarymanagementsystem.RecyclerView.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.RecyclerView.Model.BookModel;
import com.example.librarymanagementsystem.User.BookDetailActivity;
import com.example.librarymanagementsystem.User.BorrowActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    List<BookModel> book = new ArrayList<>();
    Context context;

    public BookAdapter(Context context, List<BookModel> book) {
        super();
        this.context = context;
        this.book = book;
    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        String category = book.get(position).getCategory();
        int book_id = book.get(position).getBook_id();
        int image = book.get(position).getCover();
        String title = book.get(position).getTitle();
        String author = book.get(position).getAuthor();
        String published = book.get(position).getPublished_date();
        int availability = book.get(position).getAvailability();
        String description = book.get(position).getDescription();

        holder.im_cover.setBackgroundResource(image);
        holder.tv_title.setText(title);
        holder.tv_author.setText(author);
        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_book);
            ImageView im_cover = dialog.findViewById(R.id.im_cover);
            TextView tv_title = dialog.findViewById(R.id.tv_title);
            TextView tv_author = dialog.findViewById(R.id.tv_author);
            TextView tv_published = dialog.findViewById(R.id.tv_published);
            TextView tv_availability = dialog.findViewById(R.id.tv_availability);
            Button btn_details = dialog.findViewById(R.id.btn_details);
            Button btn_borrow = dialog.findViewById(R.id.btn_borrow);

            im_cover.setImageResource(image);
            tv_title.setText(title);
            tv_author.setText("Author: " + author);
            tv_published.setText("Published: " + published);
            if (book.get(position).getAvailability() == 0) {
                tv_availability.setText("Available");
                tv_availability.setTextColor(Color.GREEN);
            } else {
                tv_availability.setText("Not Available");
                tv_availability.setTextColor(Color.RED);
            }
            btn_details.setOnClickListener(v1 -> {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("IMAGE", image);
                intent.putExtra("TITLE", title);
                intent.putExtra("AUTHOR", author);
                intent.putExtra("PUBLISHED", published);
                intent.putExtra("AVAILABILITY", availability);
                intent.putExtra("BOOK_ID", book_id);
                intent.putExtra("CATEGORY", category);
                intent.putExtra("DESCRIPTION", description);
                context.startActivity(intent);
            });
            btn_borrow.setOnClickListener(v1 -> {
                Intent intent = new Intent(context, BorrowActivity.class);
                intent.putExtra("IMAGE", image);
                intent.putExtra("TITLE", title);
                intent.putExtra("AUTHOR", author);
                intent.putExtra("PUBLISHED", published);
                intent.putExtra("AVAILABILITY", availability);
                intent.putExtra("BOOK_ID", book_id);
                intent.putExtra("CATEGORY", category);
                intent.putExtra("DESCRIPTION", description);
                context.startActivity(intent);
            });
            dialog.show();

        });
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookHolder(view);
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        ImageView im_cover;
        TextView tv_author, tv_title;
        public BookHolder(@NonNull View itemView) {
            super(itemView);
            im_cover = itemView.findViewById(R.id.im_book_cover);
            tv_title = itemView.findViewById(R.id.tv_book_title);
            tv_author = itemView.findViewById(R.id.tv_author);
        }
    }


}
