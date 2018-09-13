package com.sqlite.radik.to_do_list.presenter;

import android.content.Intent;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.MainActivity;
import com.sqlite.radik.to_do_list.TaskActivity;
import com.sqlite.radik.to_do_list.data.TaskCaption;
import com.sqlite.radik.to_do_list.model.Model;

/**
 * Created by Radik on 30.03.2018.
 */

public class Presenter {

    private MainActivity mainView;
    private TaskActivity taskView;
    private Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public void attachMainView(MainActivity view){
        this.mainView = view;
    }

    public void attachTaskView(TaskActivity taskView) {
        this.taskView = taskView;
    }

    public void detachMainView() {
        mainView = null;
    }

    public void detachTaskView() {
        taskView = null;
    }

    public void startTaskActivity() {
        Intent intent = new Intent(mainView, TaskActivity.class);
        mainView.startActivity(intent);
    }

    public void addTask() {
        ParentObject parentObject = taskView.getItemData();
        if(parentObject != null) {
            model.addItem((TaskCaption) parentObject);
        }
        taskView.close();
    }

    public void viewIsReady() {
        mainView.showItems(model.getAllItems());
    }

    public void loadItems() {
        mainView.showItems(model.getAllItems());
        setUnSorted();
    }

    public void loadSortedItems() {
        mainView.showItems(model.getSortedItems());
        setSorted();
    }

    public void deleteItem(TaskCaption taskCaption) {
        model.deleteItem(taskCaption);
        loadItems();
    }

    public void setSorted() {
        mainView.showSorted();
    }

    public void setUnSorted() {
        mainView.showUnSorted();
    }


}
