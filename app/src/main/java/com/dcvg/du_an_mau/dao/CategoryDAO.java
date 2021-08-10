package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_CATEGORY_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_CATEGORY;

public class CategoryDAO {
    private Sqlite sqlite;

    public CategoryDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertCategory(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_ID, category.getCategory_id().trim());
        contentValues.put(COLUMN_CATEGORY_NAME, category.getCategory_name().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_CATEGORY, null, contentValues);
        return result;
    }

    public long updateCategory(Category category, String idCategory) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, category.getCategory_name().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_CATEGORY, contentValues, COLUMN_CATEGORY_ID + "=?", new String[]{idCategory});
        return result;
    }

    public long deleteCategory(String idCategory) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID + "=?", new String[]{idCategory});
        return result;
    }

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_CATEGORY;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String CATEGORY_ID = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID));
                    String CATEGORY_NAME = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME));
                    Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
                    categories.add(category);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return categories;
    }

    public List<Object[]> getAllNameAndIdCategory() {
        List<Object[]> nameAndIdCategories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT CATEGORY_ID, CATEGORY_NAME FROM " + TABLE_CATEGORY;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    nameAndIdCategories.add(new String[]{cursor.getString(0), cursor.getString(1)});
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return nameAndIdCategories;
    }

    public String getNameCategoryById(String categoryId) {
        String nameCategory = "";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        String sql = "SELECT CATEGORY_NAME FROM " + TABLE_CATEGORY + " WHERE CATEGORY_ID = '" + categoryId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    nameCategory = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return nameCategory;
    }
}
