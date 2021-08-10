package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Librarian;

import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_LIBRARIAN_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_LIBRARIAN_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_PASSWORD;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_LIBRARIAN;

public class LibrarianDAO {

    private Sqlite sqlite;

    public LibrarianDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertLibrarianDAO(Librarian librarian) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LIBRARIAN_ID, librarian.getLibrarian_id().trim());
        contentValues.put(COLUMN_PASSWORD, librarian.getPasword());
        contentValues.put(COLUMN_LIBRARIAN_NAME, librarian.getLibrarian_name().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_LIBRARIAN, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public long updateLibrarianDAO(Librarian librarian, String idLibrarian) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, librarian.getPasword());
        contentValues.put(COLUMN_LIBRARIAN_NAME, librarian.getLibrarian_name().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_LIBRARIAN, contentValues, COLUMN_LIBRARIAN_ID + "=?", new String[]{idLibrarian});
        sqLiteDatabase.close();
        return result;
    }

    public long deleteLibrarianDAO(String idLibrarian) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_LIBRARIAN, COLUMN_LIBRARIAN_ID + "=?", new String[]{idLibrarian});
        sqLiteDatabase.close();
        return result;
    }

    public List<Librarian> getAllLibrarian() {
        List<Librarian> librarians = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_LIBRARIAN;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.isAfterLast()) {
                    String LIBRARIAN_ID = cursor.getString(cursor.getColumnIndex(COLUMN_LIBRARIAN_ID));
                    String LIBRARIAN_NAME = cursor.getString(cursor.getColumnIndex(COLUMN_LIBRARIAN_NAME));
                    String LIBRARIAN_PASSWORD = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                    Librarian librarian = new Librarian(LIBRARIAN_ID, LIBRARIAN_PASSWORD, LIBRARIAN_NAME);
                    librarians.add(librarian);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return librarians;
    }
}
