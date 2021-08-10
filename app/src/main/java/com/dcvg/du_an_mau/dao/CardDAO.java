package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Book;
import com.dcvg.du_an_mau.model.Card;

import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_PRICE;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CARD_DATE;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CARD_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CARD_PRICE;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_ID_B;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_MEMBER_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_RETURN_CARD;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_BOOK;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_CARD;

public class CardDAO {
    private Sqlite sqlite;

    public CardDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertCard(Card card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CARD_ID, card.getCard_id().trim());
        contentValues.put(COLUMN_MEMBER_ID, card.getMember_id().trim());
        contentValues.put(COLUMN_CATEGORY_ID, card.getCategory_id().trim());
        contentValues.put(COLUMN_BOOK_ID, card.getBook_id().trim());
        contentValues.put(COLUMN_CARD_DATE, card.getCard_date().trim());
        contentValues.put(COLUMN_CARD_PRICE, card.getPrice());
        contentValues.put(COLUMN_RETURN_CARD, card.isReturn_card());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_CARD, null, contentValues);
        return result;
    }

    public long updateCard(Card card, String idCard) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEMBER_ID, card.getMember_id().trim());
        contentValues.put(COLUMN_CATEGORY_ID, card.getCategory_id().trim());
        contentValues.put(COLUMN_BOOK_ID, card.getBook_id().trim());
        contentValues.put(COLUMN_CARD_DATE, card.getCard_date().trim());
        contentValues.put(COLUMN_CARD_PRICE, card.getPrice());
        contentValues.put(COLUMN_RETURN_CARD, card.isReturn_card());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_CARD, contentValues, COLUMN_CARD_ID + "=?", new String[]{idCard});
        return result;
    }

    public long deleteCard(String idCard) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_CARD, COLUMN_CARD_ID + "=?", new String[]{idCard});
        return result;
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_CARD;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String ID_CARD = cursor.getString(cursor.getColumnIndex(COLUMN_CARD_ID));
                    String ID_MEMBER_CARD = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_ID));
                    String ID_CATEGORY_CARD = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                    String ID_BOOK_CARD = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
                    String DATE_CARD = cursor.getString(cursor.getColumnIndex(COLUMN_CARD_DATE));
                    double PRICE_CARD = cursor.getDouble(cursor.getColumnIndex(COLUMN_CARD_PRICE));
                    boolean RETURN_CARD = cursor.getInt(cursor.getColumnIndex(COLUMN_RETURN_CARD)) > 0;
                    Card card = new Card(ID_CARD, ID_MEMBER_CARD, ID_CATEGORY_CARD, ID_BOOK_CARD, DATE_CARD, PRICE_CARD, RETURN_CARD);
                    cards.add(card);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return cards;
    }

    public List<Book> getTopBook() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT BOOK.BOOK_ID, BOOK.BOOK_NAME, BOOK.BOOK_PRICE, BOOK.CATEGORY_ID, COUNT(BOOK.BOOK_ID) FROM BOOK JOIN CARD ON BOOK.BOOK_ID = CARD.BOOK_ID GROUP BY BOOK.BOOK_ID ORDER BY COUNT(BOOK.BOOK_ID) DESC LIMIT 10";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String BOOK_ID = cursor.getString(0);
                    String BOOK_NAME = cursor.getString(1);
                    double BOOK_PRICCE = cursor.getDouble(2);
                    String BOOK_CATEGORY_NAME = cursor.getString(3);
                    String BOOK_NUMBER_OF_BORROW = cursor.getString(4);
                    Book book = new Book(BOOK_ID, BOOK_NAME, BOOK_PRICCE, BOOK_CATEGORY_NAME, BOOK_NUMBER_OF_BORROW);
                    books.add(book);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return books;
    }

    public double getTotalByTime(String startDate, String endDate) {
        double total = 0;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT SUM(CARD_PRICE) FROM CARD WHERE CARD_DATE >= '" + startDate +"' AND CARD_DATE <= '" + endDate + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    total = cursor.getDouble(0);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return total;
    }
}
