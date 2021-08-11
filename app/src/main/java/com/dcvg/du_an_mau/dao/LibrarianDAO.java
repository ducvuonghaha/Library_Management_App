package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Category;
import com.dcvg.du_an_mau.model.Librarian;

import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_LIBRARIAN_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_LIBRARIAN_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_LIBRARIAN_USERNAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_PASSWORD;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_BOOK;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_CATEGORY;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_LIBRARIAN;

public class LibrarianDAO {

    private Sqlite sqlite;

    public LibrarianDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertLibrarianDAO(Librarian librarian) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, librarian.getPassword().trim());
        contentValues.put(COLUMN_LIBRARIAN_USERNAME, librarian.getUsername().trim());
        contentValues.put(COLUMN_LIBRARIAN_NAME, librarian.getLibrarian_name().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_LIBRARIAN, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public int checkLibrarianExist(String username) {
        int doExist = 0;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT COUNT(*) FROM " + TABLE_LIBRARIAN + " WHERE " + COLUMN_LIBRARIAN_USERNAME + " = '" + username + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    doExist = cursor.getInt(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return doExist;
    }

    public int login(String username, String password) {
        int doLogin = 0;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT COUNT(*) FROM " + TABLE_LIBRARIAN + " WHERE " + COLUMN_LIBRARIAN_USERNAME + " = '" + username + "' AND " + COLUMN_PASSWORD + " = '" + password + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    doLogin = cursor.getInt(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return doLogin;
    }

    public long changePassword(String password, String username) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, password.trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_LIBRARIAN, contentValues, COLUMN_LIBRARIAN_USERNAME + "=?", new String[]{username});
        return result;
    }

    public String getPasswordByUsername(String username) {
        String password = "";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_LIBRARIAN + " WHERE " + COLUMN_LIBRARIAN_USERNAME + " = '" + username + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    password = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return password;
    }
}
