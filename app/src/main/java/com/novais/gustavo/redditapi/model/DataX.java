package com.novais.gustavo.redditapi.model;

import java.util.ArrayList;

/**
 * Created by Gustavo on 20/01/17.
 */

public class DataX {
    private  String modhash;
    private String after;
    private Object before;
    private ArrayList<Children> children;

    public ArrayList<Children> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Children> children) {
        this.children = children;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

}
