package com.sqlite.radik.to_do_list;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.ItemParent;
import com.sqlite.radik.to_do_list.database.DBHelper;
import com.sqlite.radik.to_do_list.database.IDBHelper;

import java.util.List;

/**
 * Created by Radik on 30.03.2018.
 */

public class Model implements IDBHelper{

    private DBHelper dbHelper;

    public Model(DBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    @Override
    public void addItem(ItemParent itemParent) {
        dbHelper.addItem(itemParent);
    }

    @Override
    public ItemParent getItem(int id) {
       return dbHelper.getItem(id);
    }

    @Override
    public List<ParentObject> getAllItems() {
        return dbHelper.getAllItems();
    }

    @Override
    public int getItemsCount() {
        return dbHelper.getItemsCount();
    }

    @Override
    public int updateItem(ItemParent itemParent) {
        return dbHelper.updateItem(itemParent);
    }

    @Override
    public void deleteItem(ItemParent itemParent) {
        dbHelper.deleteItem(itemParent);
    }

    @Override
    public void deleteAll() {
        dbHelper.deleteAll();
    }
}
