package com.nacoda.moviesmvpdagger2rxjava.models;

/**
 * Created by ASUS on 25/09/2017.
 */

public class Detail {

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

    private String runtime;

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    private String tagline;
}
