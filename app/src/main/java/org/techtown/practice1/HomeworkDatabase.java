package org.techtown.practice1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class HomeworkDatabase {
    private static final String TAG = "HomeworkDatabase";

    // 싱글톤 인스턴스
    private static HomeworkDatabase database;
    // 데이터 베이스 이름
    public static String DATABASE_NAME = "homework.db";
    // table name for HOMEWORK
    public static String TABLE_HOMEWORK = "HOMEWORK";
    // version
    public static int DATABASE_VERSION = 1;
    // Helper class defined
    private DatabaseHelper dbHelper;
    // 컨텍스트 객체
    private Context context;
    // 데이터베이스 객체
    private SQLiteDatabase db;
    // 생성자
    private HomeworkDatabase(Context context) {
        this.context = context;
    }

    // 인스턴스 가져오기
    public static HomeworkDatabase getInstance(Context context) {
        if (database == null) {
            database = new HomeworkDatabase(context);
        }

        return database;
    }

    // 데이터베이스 열기
    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    // 데이터베이스 닫기
    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    // execute raw query using the input SQL
    // close the cursor after fetching any result
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL, null);
            println("cursor count : " + cursor.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return cursor;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }
        return true;
    }

    // Database Helper inner class
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase _db) {
            println("creating database [" + DATABASE_NAME + "].");

            // TABLE_NOTE
            println("creating table [" + TABLE_HOMEWORK + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_HOMEWORK;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_HOMEWORK + "("
                    + " _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  DEADLINE TEXT, "
                    + "  SUBJECTNAME TEXT, "
                    + "  HOMEWORKNAME TEXT, "
                    + "  ALARM_TIME TEXT, "
                    + "  ID INTEGER" + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);  // SQL문 실행하기
            }
        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
