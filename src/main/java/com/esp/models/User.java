package com.esp.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class User {
    @Id
    @Column
    private long id;
    @Column(nullable = false, unique = true, length = 45)
    private String username;
    @Column(nullable = false, length = 64)
    private String password;
    @Column(nullable = false, length =20)
    private String firstname;
    @Column(nullable = false, length = 20)
    private String lastname;
    @Column(nullable = false, unique = true, length = 45)
    private String email;
    private int phonenumber;
    private String subscription;
    @OneToOne
    private Esp esp;
    @OneToOne
    private UserHistory history;

    public User() {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
    }

    public User(String username, String password, String firstname, String lastname, String email, int phonenumber,
                String subscription, Esp esp, UserHistory history) {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.subscription = subscription;
        this.esp = esp;
        this.history = history;
    }

    public User(String username, String password) {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
        this.username = username;
        this.password = password;
        this.esp = Esp.createNewEsp(id);
    }

    public User(long id) {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);;
    }

    public User(String name) {
        Random rand = new Random();
        this.id = rand.nextLong();
        this.username = name;
    }

    public User(String username, String password, boolean b, boolean b1, boolean b2, boolean b3, List<GrantedAuthority> user) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Esp getEsp() {
        return esp;
    }

    public void setEsp(Esp esp) {
        this.esp = esp;
    }

    public UserHistory getHistory() {
        return history;
    }

    public void setHistory(UserHistory history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber=" + phonenumber +
                ", subscription='" + subscription + '\'' +
                ", esp=" + esp +
                ", history=" + history +
                '}';
    }

    public static User creatNewUserWithNewId(long id) {
        return new User(id);
    }
}
