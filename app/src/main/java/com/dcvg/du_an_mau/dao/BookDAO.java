package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Book;
import com.dcvg.du_an_mau.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_BOOK_PRICE;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_ID_B;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_BOOK;

public class BookDAO {
    private Sqlite sqlite;

    public BookDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertBook(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_ID, book.getBook_id().trim());
        contentValues.put(COLUMN_BOOK_NAME, book.getBook_name().trim());
        contentValues.put(COLUMN_BOOK_PRICE, book.getBook_price());
        contentValues.put(COLUMN_CATEGORY_ID_B, book.getCategory_id());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_BOOK, null, contentValues);
        return result;
    }

    public long updateBook(Book book, String idBook) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, book.getBook_name().trim());
        contentValues.put(COLUMN_BOOK_PRICE, book.getBook_price());
        contentValues.put(COLUMN_CATEGORY_ID_B, book.getCategory_id());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_BOOK, contentValues, COLUMN_BOOK_ID + "=?", new String[]{idBook});
        return result;
    }

    public long deleteBook(String idBook) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_BOOK, COLUMN_BOOK_ID + "=?", new String[]{idBook});
        return result;
    }

    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_BOOK;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String BOOK_ID = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
                    String BOOK_NAME = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                    double BOOK_PRICCE = cursor.getDouble(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
                    String BOOK_CATEGORY_NAME = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID_B));
                    Book book = new Book(BOOK_ID, BOOK_NAME, BOOK_PRICCE, BOOK_CATEGORY_NAME);
                    books.add(book);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return books;
    }

    public List<Object[]> getAllNameAndIdBook() {
        List<Object[]> nameAndIdBooks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT BOOK_ID, BOOK_NAME FROM " + TABLE_BOOK;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    nameAndIdBooks.add(new String[]{cursor.getString(0), cursor.getString(1)});
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return nameAndIdBooks;
    }

    public String getNameBookById(String id) {
        String nameBook = "";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT BOOK_NAME FROM " + TABLE_BOOK + " WHERE BOOK_ID = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    nameBook = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return nameBook;
    }

    public String getCateGoryBookById(String id) {
        String categoryBook = "";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT CATEGORY_ID FROM " + TABLE_BOOK + " WHERE BOOK_ID = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    categoryBook = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return categoryBook;
    }

    public String getPriceBookById(String id) {
        String price = "";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT BOOK_PRICE FROM " + TABLE_BOOK + " WHERE BOOK_ID = '" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    price = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return price;
    }
}
