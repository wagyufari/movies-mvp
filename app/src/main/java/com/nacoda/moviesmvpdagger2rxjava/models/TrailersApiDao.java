package com.nacoda.moviesmvpdagger2rxjava.models;

/**
 * Created by ASUS on 25/09/2017.
 */

public class TrailersApiDao extends BaseTrailersApiDao {

    public String getKey() {
        return key;
    }

    public TrailersApiDao(String key) {

        this.key = key;
    }

    private String key;
}
