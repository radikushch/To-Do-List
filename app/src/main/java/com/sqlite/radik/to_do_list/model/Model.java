package com.sqlite.radik.to_do_list.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.TaskCaption;
import com.sqlite.radik.to_do_list.database.DBHelper;

import java.util.List;

/**
 * Created by Radik on 30.03.2018.
 */

public class Model {

    private DBHelper dbHelper;

    public Model(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void addItem(TaskCaption taskCaption) {
        dbHelper.insert(taskCaption);
    }

    public TaskCaption getItem(int id) {
       return dbHelper.query(id);
    }

    public List<ParentObject> getAllItems() {
        return dbHelper.rawQuery();
    }

    public int getItemsCount() {
        return dbHelper.count();
    }

    public int updateItem(TaskCaption taskCaption) {
        return dbHelper.update(taskCaption);
    }

    public void deleteItem(TaskCaption taskCaption) {
        dbHelper.delete(taskCaption);
    }

    public void deleteAll() {
        dbHelper.deleteAll();
    }

    public List<ParentObject> getSortedItems() {
        return dbHelper.sortQuery();
    }
}
