package com.esp.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Entity
public class Esp {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private LocalDate date;
    @Column
    private LocalTime time;
    @OneToOne
    private User user;
    @OneToOne
    private UserHistory history;

    public Esp(long id, String name) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Esp(long id) {
        this.id = id;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.user = User.creatNewUserWithNewId(this.id);
        this.history = UserHistory.creatNewUserWithNewId(this.id);
    }

    public Esp(String name) {
        Random rand = new Random();
        this.id = rand.nextLong();
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.user = User.creatNewUserWithNewId(this.id);
        this.history = UserHistory.creatNewUserWithNewId(this.id);
    }

    public static Esp createNewEsp(long id) {
      return new Esp(id);
    }

    public Esp() {
        Random rand = new Random();
        this.id = rand.nextLong();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Esp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", user=" + user +
                ", history=" + history +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserHistory getHistory() {
        return history;
    }

    public void setHistory(UserHistory history) {
        this.history = history;
    }
}
