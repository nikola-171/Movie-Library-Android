package com.example.movielibrary.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movielibrary.Database.Entities.MovieDetails;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "movieLibrary";

    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "movies";

    private static final String TABLE_NAME_SETTINGS = "settings";
    private static final String TABLE_NAME_SETTING_COL_NAME = "name";
    private static final String TABLE_NAME_SETTING_COL_VALUE = "value";

    private static final String ID_COL = "id";

    private static final String TITLE_COL = "title";

    private static final String POSTER_COL = "poster";

    private static final String MOVIE_ID_COL = "movieId";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT,"
                + POSTER_COL + " TEXT,"
                + MOVIE_ID_COL + " TEXT)";

        String settingsTable = "CREATE TABLE " + TABLE_NAME_SETTINGS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_NAME_SETTING_COL_NAME + " TEXT,"
                + TABLE_NAME_SETTING_COL_VALUE + " TEXT)";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(settingsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS);
        onCreate(sqLiteDatabase);
    }

    public void addNewMovie(String title, String poster, String movieId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE_COL, title);
        values.put(POSTER_COL, poster);
        values.put(MOVIE_ID_COL, movieId);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void insertSetting(String name, String value) {
        if(getSettingByName(name) == null){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TABLE_NAME_SETTING_COL_NAME, name);
            values.put(TABLE_NAME_SETTING_COL_VALUE, value);

            db.insert(TABLE_NAME_SETTINGS, null, values);

            db.close();
        }else{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(TABLE_NAME_SETTING_COL_VALUE, value);

            String[] args = new String[]{name};

            db.update(TABLE_NAME_SETTINGS, values, TABLE_NAME_SETTING_COL_NAME +"=?", args);

            db.close();
        }

    }

    public String getSettingByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT " + TABLE_NAME_SETTING_COL_VALUE + " FROM " + TABLE_NAME_SETTINGS + " WHERE " + TABLE_NAME_SETTING_COL_NAME + " =?";

        Cursor cursor = db.rawQuery(selectString, new String[] {name});
        String val = cursor.moveToFirst() ? cursor.getString(0) : null;

        cursor.close();
        db.close();
        return val;
    }

    public boolean isMovieInDatabase(String movieId){
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + MOVIE_ID_COL + " =?";

        Cursor cursor = db.rawQuery(selectString, new String[] {movieId});
        boolean hasObject = cursor.moveToFirst();

        cursor.close();
        db.close();
        return hasObject;
    }

    public ArrayList<MovieDetails> readAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<MovieDetails> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new MovieDetails(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void deleteMovie(String movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "movieId=?", new String[]{movieId});
        db.close();
    }
}
