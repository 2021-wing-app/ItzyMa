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

    // table name for HOMEWORK
    public static String TABLE_HOMEWORK = "HOMEWORK";

    // version
    public static int DATABASE_VERSION = 1;


    // Helper class defined
    private DatabaseHelper dbHelper;

    // SQLiteDatabase 인스턴스
    private SQLiteDatabase db;

    // 컨텍스트 객체
    private Context context;

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
        println("opening database [" + AppConstants.DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    // 데이터베이스 닫기
    public void close() {
        println("closing database [" + AppConstants.DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    // execute raw query using the input SQL
    // close the cursor after fetching any result
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
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

    public void insertRecord(String deadline, String subjectName, String homeworkName) {
        try {
            db.execSQL( "insert into " + TABLE_HOMEWORK + "(DEADLINE, SUBJECTNAME, HOMEWORKNAME) values ('" + deadline + "', '" + subjectName + "', '" + homeworkName + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<Homework> selectAll() {
        ArrayList<Homework> result = new ArrayList<Homework>();

        try {
            Cursor cursor = db.rawQuery("select DEADLINE, SUBJECTNAME, HOMEWORKNAME from " + TABLE_HOMEWORK, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                int _id = i;
                cursor.moveToNext();
                String deadline = cursor.getString(0);
                String subjectName = cursor.getString(1);
                String homeworkName = cursor.getString(2);

                Homework info = new Homework(_id, deadline, subjectName, homeworkName);
                result.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }

        return result;
    }

    // Database Helper inner class
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, AppConstants.DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            println("creating database [" + AppConstants.DATABASE_NAME + "].");

            // TABLE_NOTE
            println("creating table [" + TABLE_HOMEWORK + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_HOMEWORK;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_HOMEWORK + "("
                    + " _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  DEADLINE TEXT, "
                    + "  SUBJECTNAME TEXT, "
                    + "  HOMEWORKNAME TEXT" + ")";

            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);  // SQL문 실행하기
            }
        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + AppConstants.DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
