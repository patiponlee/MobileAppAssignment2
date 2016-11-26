package com.egco428.a23265.mobileappassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 6/11/2559.
 */
public class CommentDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper mySQLiteHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME, MySQLiteHelper.COLUMN_PASSWORD, MySQLiteHelper.COLUMN_LATITUDE,MySQLiteHelper.COLUMN_LONGTITUDE};

    public CommentDataSource(Context context){
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = mySQLiteHelper.getWritableDatabase();
    }

    public void close(){
        mySQLiteHelper.close();
    }

    public Comment createMessage(String username, String password, String latitude, String longitude){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGTITUDE, longitude);
        open();
        long insertID = database.insert(MySQLiteHelper.TABLE_RESULTS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS, allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        Comment newMessage = cursorToComment(cursor);
        cursor.close();
        return newMessage;
    }

    public void deleteFortuneResult(Comment results){
        long id = results.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_RESULTS, MySQLiteHelper.COLUMN_ID + "=" + id,null);
    }

    public List<Comment> getAllComments(){
        List<Comment> results = new ArrayList<Comment>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Comment newresult = cursorToComment(cursor);
            results.add(newresult);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public Comment cursorToComment(Cursor cursor){ //set value to comment
        Comment results = new Comment(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        return results;
    }
}
