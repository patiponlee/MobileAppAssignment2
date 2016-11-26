package com.egco428.a23265.mobileappassignment2;

/**
 * Created by USER on 6/11/2559.
 */
public class Comment {
    private String username;
    private String latitudedb;
    private String password;
    private String longtitudedb;

    private long id;
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public Comment(long id, String username, String password, String latitudedb, String longtitudedb){
        this.id = id;
        this.username = username;
        this.latitudedb = latitudedb;
        this.password = password;
        this.longtitudedb = longtitudedb;
    }

    public String getUsername(){return username;}
    public String getLatitudedb(){return latitudedb;}
    public String getPassword(){return password;}
    public String getLongtitudedb(){return longtitudedb;}
}
