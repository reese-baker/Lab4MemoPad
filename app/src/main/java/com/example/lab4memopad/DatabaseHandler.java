package com.example.lab4memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_MEMOS = "memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "task";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMOS_TABLE = "CREATE TABLE memos (_id integer primary key autoincrement, task text)";
        db.execSQL(CREATE_MEMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }

    public void addMemo(Memo m) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, m.getMemo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public boolean deleteMemo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_MEMOS, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public String getAllMemos() {

        String query = "SELECT * FROM " + TABLE_MEMOS;
        StringBuilder s = new StringBuilder();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String memo = cursor.getString(1);
                s.append(id).append(": ").append(memo).append("\n");
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return s.toString();
    }
}