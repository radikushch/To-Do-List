package com.sqlite.radik.to_do_list;

import android.content.Intent;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.ItemParent;

/**
 * Created by Radik on 30.03.2018.
 */

public class Presenter {

    private IView view;
    private Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public void attachView(IView view){
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void startTaskActivity() {
        Intent intent = new Intent((MainActivity)view, TaskActivity.class);
        ((MainActivity) view).startActivity(intent);
    }

    public void addTask() {
        ParentObject parentObject = view.getItemData();
        if(parentObject != null) {
            model.addItem((ItemParent) parentObject);
        }
    }

    public void viewIsReady() {
        view.showItems(model.getAllItems());
    }
}
