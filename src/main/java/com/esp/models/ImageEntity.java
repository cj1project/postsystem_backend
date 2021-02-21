package com.esp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class ImageEntity {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private File file;
    @Column
    private LocalDate date;
    @Column
    private LocalTime time;
    @OneToOne
    private Esp esp;

    public ImageEntity(String name, File file) {
        this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
       // var s = String.valueOf(this.id);
        this.name = name;
        this.file = file;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.esp = Esp.createNewEsp(this.id);
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", file=" + file +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
