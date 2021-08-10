package com.dcvg.du_an_mau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dcvg.du_an_mau.database.Sqlite;
import com.dcvg.du_an_mau.model.Member;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_MEMBER_BIRTH;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_MEMBER_ID;
import static com.dcvg.du_an_mau.database.Sqlite.COLUMN_MEMBER_NAME;
import static com.dcvg.du_an_mau.database.Sqlite.TABLE_MEMBER;

public class MemberDAO {
    private Sqlite sqlite;

    public MemberDAO(Context context) {
        this.sqlite = new Sqlite(context);
    }

    public long insertMember(Member member) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEMBER_ID, member.getMember_id().trim());
        contentValues.put(COLUMN_MEMBER_NAME, member.getMember_name().trim());
        contentValues.put(COLUMN_MEMBER_BIRTH, member.getBirth());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_MEMBER, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public long updateMember(Member member, String idMember) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEMBER_NAME, member.getMember_name().trim());
        contentValues.put(COLUMN_MEMBER_BIRTH, member.getBirth().toString().trim());
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.update(TABLE_MEMBER, contentValues, COLUMN_MEMBER_ID + "=?", new String[]{idMember});
        sqLiteDatabase.close();
       return result;
    }

    public long deleteMember(String idMember) {
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_MEMBER, COLUMN_MEMBER_ID + "=?", new String[]{idMember});
        sqLiteDatabase.close();
        return  result;
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_MEMBER;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String MEMBER_ID = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_ID));
                    String MEMBER_NAME = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME));
                    String MEMBER_BIRTH = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_BIRTH));
                    Member member = new Member(MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH);
                    members.add(member);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return members;
    }

    public List<Object[]> getAllNameAndIdMembers() {
        List<Object[]> memberNamesAndIds = new ArrayList<>();
        String sql = "SELECT MEMBER_ID, MEMBER_NAME FROM " + TABLE_MEMBER;
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    memberNamesAndIds.add(new Object[]{cursor.getString(0), cursor.getString(1)});
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return memberNamesAndIds;
    }

    public String getNameMemberById(String id) {
        String memberName = "";
        String sql = "SELECT MEMBER_NAME FROM " + TABLE_MEMBER + " WHERE MEMBER_ID = '" + id + "'";
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    memberName = cursor.getString(0);
                    cursor.moveToNext();
                }
                cursor.close();
                sqLiteDatabase.close();
            }
        }
        return memberName;
    }
}
