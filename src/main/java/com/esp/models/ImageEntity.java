package com.esp.models;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ImageEntity {
    @Id
    @Column(length = 64)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;  //long is a large string (mediumtext). It is not suitable for indexing in general.
    @Column
    private String name;
    @Column
    private File file;
    @Column
    private LocalDate date;
    @Column
    private LocalTime time;
    @ManyToOne
    private Esp esp;
    @OneToOne
    private UserHistory history;
    @Column(nullable = false, unique = true)
    private String user_id;

   /* @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //MANY-TO-ONE BIDIRECTIONAL, this class is the OWNING SIDE of the association
     @JoinTable(
            name = "imageEntity_user",
            joinColumns = @JoinColumn(name = "imageEntity_id", unique = true),
            inverseJoinColumns = @JoinColumn(name = "user_id", unique = true)
    )*/

    public ImageEntity(String name, File file) {
        this.name = name;
        this.file = file;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.esp = Esp.createNewEsp(this.id);
        this.user_id = getUser_id();
    }

   /* public ImageEntity(String name, File file, String user_id) {
        this.name = name;
        this.file = file;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.esp = Esp.createNewEsp(this.id);
        this.user_id = user_id;
    }*/

    public ImageEntity() throws FileNotFoundException {
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file=" + file +
                ", date=" + date +
                ", time=" + time +
                ", esp=" + esp +
                ", history=" + history +
                '}';
    }
}