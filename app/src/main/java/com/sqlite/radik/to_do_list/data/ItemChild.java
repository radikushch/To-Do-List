package com.sqlite.radik.to_do_list;

/**
 * Created by Radik on 27.03.2018.
 */

public class ItemChild {

    private String _mDefinition;

    public ItemChild() {

    }

    public ItemChild(String mDefinition) {
        this._mDefinition = mDefinition;
    }

    public String getmDefinition() {
        return _mDefinition;
    }

    public void setDefinition(String mDefinition) {
        this._mDefinition = mDefinition;
    }
}
