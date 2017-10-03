package com.nacoda.moviesmvpdagger2rxjava.models;

/**
 * Created by ASUS on 25/09/2017.
 */

public class CreditsApiDao extends BaseApiDao {

    private String cast_id;
    private String character;
    private String credit_id;
    private String gender;
    private String name;
    private String order;
    private String profile_path;

    public String getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public CreditsApiDao(String cast_id, String character, String credit_id, String gender, String name, String order, String profile_path) {

        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }
}
