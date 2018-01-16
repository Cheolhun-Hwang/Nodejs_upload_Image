package com.hch.hooney.nodejs_upload_image.DAO;

/**
 * Created by hooney on 2018. 1. 16..
 */

public class InfoPoint {
    private String Date;
    private int Point;
    private String Context;

    public InfoPoint() {
        this.Date = "";
        this.Point=0;
        this.Context = "";
    }

    public InfoPoint(String date, int point, String context) {
        Date = date;
        Point = point;
        Context = context;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }
}
