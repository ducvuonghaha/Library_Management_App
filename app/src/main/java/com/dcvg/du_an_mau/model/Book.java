package com.dcvg.du_an_mau.model;

public class Book {
    public String book_id;
    public String book_name;
    public double book_price;
    public String category_id;
    public String numberOfBorrow;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public double getBook_price() {
        return book_price;
    }

    public void setBook_price(double book_price) {
        this.book_price = book_price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getNumberOfBorrow() {
        return numberOfBorrow;
    }

    public void setNumberOfBorrow(String numberOfBorrow) {
        this.numberOfBorrow = numberOfBorrow;
    }

    public Book(String book_id, String book_name, double price, String category_id) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_price = price;
        this.category_id = category_id;
    }

    public Book(String book_id, String book_name, double price, String category_id, String numberOfBorrow) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_price = price;
        this.category_id = category_id;
        this.numberOfBorrow = numberOfBorrow;
    }
}
