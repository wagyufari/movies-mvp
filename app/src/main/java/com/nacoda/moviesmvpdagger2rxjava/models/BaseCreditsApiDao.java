package com.nacoda.moviesmvpdagger2rxjava.models;


import java.util.List;

/**
 * Created by ASUS on 25/09/2017.
 */

public class BaseCreditsApiDao<T> {

    private List<T> results;
    private String id;

    public List<T> getResults() {
        return results;
    }

    public String getId() {
        return id;
    }
}
