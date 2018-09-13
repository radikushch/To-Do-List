package com.sqlite.radik.to_do_list.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radik on 27.03.2018.
 */
@Entity(tableName = "tasks")
public class TaskCaption implements ParentObject {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Ignore private List<Object> mChildrenList;
    private String caption;
    private int priority;

    @Ignore
    public TaskCaption() {
    }

    public TaskCaption(String caption, int priority, ArrayList<Object> childList) {
        this.caption = caption;
        this.priority = priority;
        this.mChildrenList = childList;
    }

    public String getCaption() {
        return caption;
    }

    public int getPriority() {
        return priority;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int _id) {
        this.id = _id;
    }
}
