package com.example.util;

import java.util.List;

public class Result {

    private List<Vector> way;

    private String text;

    public Result(List<Vector> way, String text) {
        this.way = way;
        this.text = text;
    }

    public List<Vector> getWay() {
        return way;
    }

    public String getText() {
        return text;
    }
}
