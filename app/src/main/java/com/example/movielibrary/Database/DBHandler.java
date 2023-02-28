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

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "movies";

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

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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
