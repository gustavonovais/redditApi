package com.novais.gustavo.redditapi.model;


import java.io.Serializable;

/**
 * Created by Gustavo on 20/01/17.
 */


public class Children implements Serializable {
    private String kind;
    private Data data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
