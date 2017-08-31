package com.whw.phoneinterception.bean;

import java.io.Serializable;

/**
 * Created by wuhaiwen on 2017/8/30.
 */

public class Contacts implements Serializable{
    private long id;
    private String name;
    private String number;
    private String photo_uri;
    private long photo_id;

    public Contacts(long id, String name, String number, String photo_uri, long photo_id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photo_uri = photo_uri;
        this.photo_id = photo_id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public long getPhoto_id() {
        return photo_id;
    }
}
