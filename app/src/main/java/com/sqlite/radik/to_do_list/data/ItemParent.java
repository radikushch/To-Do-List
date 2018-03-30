package com.sqlite.radik.to_do_list;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radik on 27.03.2018.
 */

public class ItemParent implements ParentObject {

    private List<Object> mChildrenList;
    private String _caption;
    private int _priority;
    private int _id;

    public ItemParent() {

    }

    public ItemParent(String caption, int priority, ArrayList<Object> childList) {
        _caption = caption;
        this._priority = priority;
        this.mChildrenList = childList;
    }

    public String getCaption() {
        return _caption;
    }

    public int getPriority() {
        return _priority;
    }

    public void setCaption(String caption) {
        _caption = caption;
    }

    public void setPriority(int priority) {
        this._priority = priority;
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
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
