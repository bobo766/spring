package ru.korshun.eda.entity;

import javax.persistence.*;

@Entity
@Table(name="coordinates")
public class Ajax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double lat;

    @Column(name = "long")
    private double lon;

    @Column(name = "datetime")
    private String dateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    public Ajax() {
    }

    public Ajax(int id, double lat, double lon, String dateTime, User user) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.dateTime = dateTime;
        this.user = user;
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
}
