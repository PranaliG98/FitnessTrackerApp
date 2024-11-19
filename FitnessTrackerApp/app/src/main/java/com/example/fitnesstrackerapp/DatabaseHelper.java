package com.example.fitnesstrackerapp;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";


    private static final String TABLE_NAME_ACTIVITY = "activity_table";
    private static final String COL_ID = "ACT_ID";
    private static final String COL_USER_ID = "USER_ID"; // New column for user ID
    private static final String COL_TYPE = "TYPE";  // Running, Jogging, Walking
    private static final String COL_UNIT = "UNIT";  // Distance in km or duration in minutes
    private static final String COL_CALORIES = "CALORIES";  // Calories burned

    private static final String DATE = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PASSWORD TEXT)");

        // Create activity table
        db.execSQL("CREATE TABLE " + TABLE_NAME_ACTIVITY + " (ACT_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, TYPE TEXT, UNIT REAL, CALORIES REAL,DATE TEXT, FOREIGN KEY(USER_ID) REFERENCES " + TABLE_NAME + "(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);
        return db.insert(TABLE_NAME, null, contentValues);
    }


    // Insert activity record for a specific user
    public long addActivity(int userId, String type, float unit, float calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_ID, userId);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_UNIT, unit);
        contentValues.put(COL_CALORIES, calories);
        return db.insert(TABLE_NAME_ACTIVITY, null, contentValues);
    }

    // Retrieve activities for a specific user
    public Cursor getActivitiesForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_ACTIVITY + " WHERE USER_ID = ?", new String[]{String.valueOf(userId)});
    }

    public Cursor getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
    }

    public Cursor getWeeklyCalories(int userId, String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT DATE, SUM(CALORIES) AS TOTAL_CALORIES FROM " +
                        TABLE_NAME_ACTIVITY +
                        " WHERE USER_ID = ? GROUP BY DATE",
                new String[]{String.valueOf(userId)});
    }
}


