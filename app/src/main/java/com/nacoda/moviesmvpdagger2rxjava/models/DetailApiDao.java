package com.nacoda.moviesmvpdagger2rxjava.models;

/**
 * Created by ASUS on 25/09/2017.
 */

public class DetailApiDao extends BaseMoviesApiDao {

    private String backdrop_path;
    private String budget;
    private String homepage;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public DetailApiDao(String backdrop_path, String budget, String homepage, String runtime, String tagline) {

        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.homepage = homepage;
        this.runtime = runtime;
        this.tagline = tagline;
    }

    private String runtime;
    private String tagline;
}
