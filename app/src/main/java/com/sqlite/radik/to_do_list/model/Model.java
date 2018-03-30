package com.sqlite.radik.to_do_list.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.ItemParent;
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

    public void addItem(ItemParent itemParent) {
        dbHelper.insert(itemParent);
    }

    public ItemParent getItem(int id) {
       return dbHelper.query(id);
    }

    public List<ParentObject> getAllItems() {
        return dbHelper.rawQuery();
    }

    public int getItemsCount() {
        return dbHelper.count();
    }

    public int updateItem(ItemParent itemParent) {
        return dbHelper.update(itemParent);
    }

    public void deleteItem(ItemParent itemParent) {
        dbHelper.delete(itemParent);
    }

    public void deleteAll() {
        dbHelper.deleteAll();
    }
}
