package com.sqlite.radik.to_do_list.data;

/**
 * Created by Radik on 27.03.2018.
 */

public class TaskDescription {

    private String definition;

    public TaskDescription() {

    }

    public TaskDescription(String mDefinition) {
        this.definition = mDefinition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String mDefinition) {
        this.definition = mDefinition;
    }
}
