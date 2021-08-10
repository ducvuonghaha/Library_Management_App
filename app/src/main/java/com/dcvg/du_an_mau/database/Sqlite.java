package com.dcvg.du_an_mau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbBook";
    public static final int VERSION = 1;

    public static final String TABLE_LIBRARIAN = "LIBRARIAN";
    public static final String COLUMN_LIBRARIAN_ID = "LIBRARIAN_ID";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_LIBRARIAN_NAME = "LIBRARIAN_NAME";
    public static final String CREATE_TABLE_LIBRARIAN =
            "CREATE TABLE LIBRARIAN(" +
                    COLUMN_LIBRARIAN_ID + " VARCHAR(15) PRIMARY KEY, " +
                    COLUMN_PASSWORD + " VARCHAR(50), " +
                    COLUMN_LIBRARIAN_NAME + " NVARCHAR(100))";

    public static final String TABLE_MEMBER = "MEMBER";
    public static final String COLUMN_MEMBER_ID = "MEMBER_ID";
    public static final String COLUMN_MEMBER_NAME = "MEMBER_NAME";
    public static final String COLUMN_MEMBER_BIRTH = "EMBER_BIRTH";
    public static final String CREATE_TABLE_MEMBER =
            "CREATE TABLE MEMBER(" +
                    COLUMN_MEMBER_ID + " VARCHAR(15) PRIMARY KEY, " +
                    COLUMN_MEMBER_NAME + " NVARCHAR(100), " +
                    COLUMN_MEMBER_BIRTH + " DATE)";

    public static final String TABLE_CATEGORY = "CATEGORY";
    public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
    public static final String COLUMN_CATEGORY_NAME = "CATEGORY_NAME";

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE CATEGORY(" +
                    COLUMN_CATEGORY_ID + " VARCHAR(15) PRIMARY KEY, " +
                    COLUMN_CATEGORY_NAME + " NVARCHAR(100))";

    public static final String TABLE_BOOK = "BOOK";
    public static final String COLUMN_BOOK_ID = "BOOK_ID";
    public static final String COLUMN_BOOK_NAME = "BOOK_NAME";
    public static final String COLUMN_BOOK_PRICE = "BOOK_PRICE";
    public static final String COLUMN_CATEGORY_ID_B = "CATEGORY_ID";
    public static final String CREATE_TABLE_BOOK =
            "CREATE TABLE BOOK(" +
                    COLUMN_BOOK_ID + " VARCHAR(15) PRIMARY KEY, " +
                    COLUMN_BOOK_NAME + " NVARCHAR(100), " +
                    COLUMN_BOOK_PRICE + " MONEY, " +
                    COLUMN_CATEGORY_ID_B + " VARCHAR(15))";

    public static final String TABLE_CARD = "CARD";
    public static final String COLUMN_CARD_ID = "CARD_ID";
    public static final String COLUMN_CARD_DATE = "CARD_DATE";
    public static final String COLUMN_CARD_PRICE = "CARD_PRICE";
    public static final String COLUMN_RETURN_CARD = "RETURN_CARD";

    public static final String CREATE_TABLE_CARD =
            "CREATE TABLE CARD(" +
                    COLUMN_CARD_ID + " VARCHAR(15) PRIMARY KEY, " +
                    COLUMN_MEMBER_ID + " VARCHAR(15), " +
                    COLUMN_CATEGORY_ID + " VARCHAR(15), " +
                    COLUMN_BOOK_ID + " VARCHAR(15), " +
                    COLUMN_CARD_DATE + " DATE, " +
                    COLUMN_CARD_PRICE + " MONEY, " +
                    COLUMN_RETURN_CARD + " BOOLEAN)";

    public Sqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIBRARIAN);
        db.execSQL(CREATE_TABLE_MEMBER);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIBRARIAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
    }
}
