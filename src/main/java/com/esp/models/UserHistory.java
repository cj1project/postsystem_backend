package com.esp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class UserHistory {
    @Id
    private long id;
    @OneToOne
    private Esp esp;
    @OneToOne
    private User user;
    public UserHistory(Esp esp, User user) {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
        this.esp = esp;
        this.user = user;
    }

    public UserHistory() {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
    }

    public static UserHistory creatNewUserWithNewId(long id) {
        return  new UserHistory();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Esp getEsp() {
        return esp;
    }

    public void setEsp(Esp esp) {
        this.esp = esp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHistory{" +
                "id=" + id +
                ", esp=" + esp +
                ", user=" + user +
                '}';
    }
}
