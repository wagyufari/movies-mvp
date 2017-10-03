package com.nacoda.moviesmvpdagger2rxjava.models;

import java.util.List;

/**
 * Created by ASUS on 25/09/2017.
 */

public class BaseApiDao<T> {

    private List<T> cast;
    private List<T> results;

    public List<T> getCast() {
        return cast;
    }

    public List<T> getResults() {
        return results;
    }
}
