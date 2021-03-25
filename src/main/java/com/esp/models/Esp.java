package com.esp.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Esp {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 64)
    private String ID = "";
    @Column
    private String name;
    @Column(name = "created_on")
    private LocalDate createdOn;    // = LocalDate.now();

    //private User user;
    @ManyToOne  //@OneToMany
    private ImageEntity imageEntity; //List<ImageEntity> imageEntity;

    public Esp(String name) {
        this.ID = getID();
        this.name = name;
        this.createdOn = LocalDate.now();
    }

    public Esp() {
        this.createdOn = LocalDate.now();
        //this.user = User.creatNewUserWithNewId(this.ID);
    }

    public String getID() {
        return ID;
    }

    public static Esp createNewEsp(String id) {
        return new Esp(id);
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return createdOn;
    }

    public void setDate(LocalDate date) {
        this.createdOn = date;
    }

    public ImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    @Override
    public String toString() {
        return "Esp{" +
                "id=" + ID +
                ", name='" + name + '\'' +
                ", date=" + createdOn +
                //", user=" + user +
                ", history=" + imageEntity +
                '}';
    }

   /* public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }*/

}
