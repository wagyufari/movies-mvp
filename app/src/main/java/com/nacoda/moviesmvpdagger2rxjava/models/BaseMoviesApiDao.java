package com.nacoda.moviesmvpdagger2rxjava.models;


import java.util.List;

/**
 * Created by ASUS on 25/09/2017.
 */

public class BaseMoviesApiDao<T> {

    private List<T> results;
    private String total_pages;

    public String getTotal_pages() {
        return total_pages;
    }

    public List<T> getResults() {
        return results;
    }
}
