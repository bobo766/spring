package ru.korshun.eda.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="location_alarm")
public class Alarms {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double lat;

    private double lon;

    @Column(name = "date_time")
    private String dateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "id_gbr", referencedColumnName = "id")
    private User gbr;

    @Column(name = "gps_enable")
    private int isGpsEnable;

    @Column(name = "nw_enable")
    private int isNetworkEnable;


    public Alarms() {
    }

    public Alarms(int id, double lat, double lon, String dateTime, User user, int isGpsEnable, int isNetworkEnable) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.dateTime = dateTime;
        this.user = user;
        this.isGpsEnable = isGpsEnable;
        this.isNetworkEnable = isNetworkEnable;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getDateTime() {
        return dateTime;
    }

    public User getUser() {
        return user;
    }

    public User getGbr() {
        return gbr;
    }

    public int getIsGpsEnable() {
        return isGpsEnable;
    }

    public int getIsNetworkEnable() {
        return isNetworkEnable;
    }
}
