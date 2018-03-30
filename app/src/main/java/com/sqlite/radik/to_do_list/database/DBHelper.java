package com.sqlite.radik.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radik on 30.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper implements IDBHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "asksManager";
    private static final String TABLE_NAME = "Tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_DEFINITION = "definition";
    private static final String KEY_PRIORITY = "priority";



    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                + KEY_ID + " integer primary key autoincrement,"
                + KEY_CAPTION + " text,"
                + KEY_DEFINITION + " text,"
                + KEY_PRIORITY + " integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addItem(ItemParent itemParent) {
        ItemChild itemChild = (ItemChild) itemParent.getChildObjectList().get(0);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CAPTION, itemParent.getCaption());
        cv.put(KEY_DEFINITION, itemChild.getmDefinition());
        cv.put(KEY_PRIORITY, itemParent.getPriority());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    @Override
    public ItemParent getItem(int id) {
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
        ItemChild itemChild = new ItemChild(cursor.getString(1));
        ArrayList<Object> mChildren = new ArrayList<>();
        mChildren.add(itemChild);
        ItemParent itemParent = new ItemParent(cursor.getString(0), Integer.parseInt(cursor.getString(2)),mChildren);
        return itemParent;
    }

    @Override
    public List<ItemParent> getAllItems() {
        List<ItemParent> itemsList= new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                ItemParent itemParent = new ItemParent();
                itemParent.setCaption(cursor.getString(0));
                itemParent.setPriority(Integer.parseInt(cursor.getString(2)));
                List<Object> list = new ArrayList<>();
                ItemChild itemChild = new ItemChild(cursor.getString(1));
                list.add(itemChild);
                itemParent.setChildObjectList(list);
                itemsList.add(itemParent);
            }while (cursor.moveToNext());
        }
        return itemsList;
    }

    @Override
    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateItem(ItemParent itemParent) {
        ItemChild itemChild = (ItemChild) itemParent.getChildObjectList().get(0);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CAPTION, itemParent.getCaption());
        cv.put(KEY_DEFINITION, itemChild.getmDefinition());
        cv.put(KEY_PRIORITY, itemParent.getPriority());
        return db.update(TABLE_NAME, cv,
                KEY_ID + "=?",
                new String[] {String.valueOf(itemParent.get_id())});
    }

    @Override
    public void deleteItem(ItemParent itemParent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(itemParent.get_id()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
