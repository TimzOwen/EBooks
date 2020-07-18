package com.owen.ebooks;

public class Books {

    private String id;
    private String [] authors;
    private String title;
    private String subtitle;
    private String publisher;
    private String publishedDate;

    public Books(String id, String[] authors, String title, String subtitle, String publisher, String publishedDate) {
        this.id = id;
        this.authors = authors;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }
}
