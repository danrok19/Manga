package com.db.manga.entity.objsql.types;

import java.io.Serializable;

public class RatingType implements Serializable {

    private int value;

    private String date;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
