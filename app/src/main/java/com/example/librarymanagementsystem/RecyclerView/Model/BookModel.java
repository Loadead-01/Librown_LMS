package com.example.librarymanagementsystem.RecyclerView.Model;

public class BookModel {
    String author;
    String title;
    String published_date;
    String category;
    String description;
    int cover, availability, book_id;

    public int getBook_id() {
        return book_id;
    }

    public BookModel(int book_id, String author, String category, String title, String published_date, String description, int cover, int availability) {
        this.author = author;
        this.category = category;
        this.title = title;
        this.published_date = published_date;
        this.description = description;
        this.cover = cover;
        this.availability = availability;
        this.book_id = book_id;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublished_date() {
        return published_date;
    }

    public String getDescription() {
        return description;
    }

    public int getCover() {
        return cover;
    }

    public int getAvailability() {
        return availability;
    }
}
