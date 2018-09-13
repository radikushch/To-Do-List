package com.sqlite.radik.to_do_list.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.TaskDescription;
import com.sqlite.radik.to_do_list.data.TaskCaption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radik on 30.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String DATABASE_NAME = "tasksManager.db";
    private static final String TABLE_NAME = "Tasks";

    private static final String KEY_ID = "_id";
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_DEFINITION = "definition";
    private static final String KEY_PRIORITY = "priority";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + KEY_ID + " integer primary key,"
                + KEY_CAPTION + " text not null,"
                + KEY_DEFINITION + " text not null,"
                + KEY_PRIORITY + " integer not null"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insert(TaskCaption taskCaption) {
        TaskDescription taskDescription = (TaskDescription) taskCaption.getChildObjectList().get(0);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CAPTION, taskCaption.getCaption());
        cv.put(KEY_DEFINITION, taskDescription.getmDefinition());
        cv.put(KEY_PRIORITY, taskCaption.getPriority());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public TaskCaption query(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {KEY_ID, KEY_CAPTION, KEY_DEFINITION, KEY_PRIORITY},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(cursor != null) cursor.moveToFirst();
        TaskDescription taskDescription = new TaskDescription(cursor.getString(2));
        ArrayList<Object> mChildren = new ArrayList<>();
        mChildren.add(taskDescription);
        TaskCaption taskCaption = new TaskCaption(cursor.getString(1), Integer.parseInt(cursor.getString(3)),mChildren);
        taskCaption.set_id(Integer.parseInt(cursor.getString(0)));
        return taskCaption;
    }

    public List<ParentObject> rawQuery() {
        List<ParentObject> itemsList= new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                TaskCaption taskCaption = new TaskCaption();
                taskCaption.set_id(Integer.parseInt(cursor.getString(0)));
                taskCaption.setCaption(cursor.getString(1));
                List<Object> list = new ArrayList<>();
                TaskDescription taskDescription = new TaskDescription(cursor.getString(2));
                list.add(taskDescription);
                taskCaption.setChildObjectList(list);
                taskCaption.setPriority(cursor.getInt(3));
                itemsList.add(taskCaption);
            }while (cursor.moveToNext());
        }
        return itemsList;
    }

    public int count() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int update(TaskCaption taskCaption) {
        TaskDescription taskDescription = (TaskDescription) taskCaption.getChildObjectList().get(0);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CAPTION, taskCaption.getCaption());
        cv.put(KEY_DEFINITION, taskDescription.getmDefinition());
        cv.put(KEY_PRIORITY, taskCaption.getPriority());
        return db.update(TABLE_NAME, cv,
                KEY_ID + "=?",
                new String[] {String.valueOf(taskCaption.get_id())});
    }

    public void delete(TaskCaption taskCaption) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] { String.valueOf(taskCaption.get_id()) });
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public List<ParentObject> sortQuery() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                KEY_PRIORITY,
                null);
        List<ParentObject> itemsList= new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                TaskCaption taskCaption = new TaskCaption();
                taskCaption.set_id(Integer.parseInt(cursor.getString(0)));
                taskCaption.setCaption(cursor.getString(1));
                List<Object> list = new ArrayList<>();
                TaskDescription taskDescription = new TaskDescription(cursor.getString(2));
                list.add(taskDescription);
                taskCaption.setChildObjectList(list);
                taskCaption.setPriority(cursor.getInt(3));
                itemsList.add(taskCaption);
            }while (cursor.moveToNext());
        }
        return itemsList;
    }
}
