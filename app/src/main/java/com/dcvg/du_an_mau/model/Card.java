package com.dcvg.du_an_mau.model;

import java.sql.Timestamp;

public class Card {
    public String card_id;
    public String member_id;
    public String category_id;
    public String book_id;
    public String card_date;
    public double price;
    public boolean return_card;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCard_date() {
        return card_date;
    }

    public void setCard_date(String card_date) {
        this.card_date = card_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReturn_card() {
        return return_card;
    }

    public void setReturn_card(boolean return_card) {
        this.return_card = return_card;
    }

    public Card(String card_id, String member_id, String category_id, String book_id, String card_date, double price, boolean return_card) {
        this.card_id = card_id;
        this.member_id = member_id;
        this.category_id = category_id;
        this.book_id = book_id;
        this.card_date = card_date;
        this.price = price;
        this.return_card = return_card;
    }
}
