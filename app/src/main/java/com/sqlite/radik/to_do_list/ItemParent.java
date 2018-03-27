package com.sqlite.radik.to_do_list;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radik on 27.03.2018.
 */

public class ItemParent implements ParentObject {

    private List<Object> mChildrenList;
    private String Caption;
    private int priority;

    public ItemParent(String caption, int priority, ArrayList<Object> childList) {
        Caption = caption;
        this.priority = priority;
        this.mChildrenList = childList;
    }

    public String getCaption() {
        return Caption;
    }

    public int getPriority() {
        return priority;
    }

    public void setCaption(String caption) {
        Caption = caption;
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
}
