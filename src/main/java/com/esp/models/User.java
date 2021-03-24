package com.esp.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "users")
public class User implements Serializable {
    //private static final long serialVersionUID = 7654875958636458575L;
    @Id
    @Column(length = 64)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;       //long is a large string (mediumtext). It is not suitable for indexing in general.
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
    @OneToOne
    private Subscription subscription;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    /*cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "role")*/
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Esp esp;

    //@OneToMany//(targetEntity=ImageEntity.class, mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  //user //ONE-TO-MANY BIDIRECTIONAL, INVERSE SIDE
    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   /* @JoinTable(
            name="imageEntity_user",  //joined table: Defaults to the concatenated names of the two associated primary entity tables, separated by an underscore.
            joinColumns=
                @JoinColumn(name="imageEntity_user_id", referencedColumnName="user_id"), //FK: The foreign key columns of the join table which reference the primary table of the entity owning the association. (I.e. the owning side of the association).
            inverseJoinColumns=
                @JoinColumn(name="imageEntity_id", referencedColumnName="id", unique = true) //PK
    )*/
    //@OneToMany
   @Column(nullable = false, unique = true, length = 65)
    private String imageEntityId;

    private boolean enabled;

    public User() {
    }

    public User(String id, String username, String password, String firstname, String lastname, String email, int phonenumber,
                Subscription subscription, Esp esp, String imageEntity ) {
        this.user_id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.subscription = subscription;
        this.esp = Esp.createNewEsp(user_id);
        this.imageEntityId = imageEntity;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.esp = Esp.createNewEsp(user_id);
    }

    public User(String name) {
        this.user_id = getId();
        this.username = name;
    }

    public User(String username, String password, boolean b, boolean b1, boolean b2, boolean b3, List<GrantedAuthority> user) {
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Esp getEsp() {
        return esp;
    }

    public void setEsp(Esp esp) {
        this.esp = esp;
    }

    public String getImageEntityId() {
        return imageEntityId;
    }

    public void setImageEntityId(String imageEntity) {
        this.imageEntityId = imageEntity;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber=" + phonenumber +
                ", subscription='" + subscription.getId() + '\'' +
                ", esp=" + esp +
                ", imageEntityId=" + imageEntityId +
                '}';
    }

    public static User creatNewUserWithNewId(String id) {
        return new User(id);
    }

    public  boolean enabled(){
        return this.enabled;
    }

    public  void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

}
