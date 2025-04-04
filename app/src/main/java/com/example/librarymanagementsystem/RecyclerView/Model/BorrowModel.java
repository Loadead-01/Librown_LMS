package com.example.librarymanagementsystem.RecyclerView.Model;

public class BorrowModel {
    int borrow_id, cover;

    public int getBorrow_id() {
        return borrow_id;
    }

    public int getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public BorrowModel(int borrow_id, int cover, String title, String date, String status) {
        this.borrow_id = borrow_id;
        this.cover = cover;
        this.title = title;
        this.date = date;
        this.status = status;
    }

    String title, date, status;
}
