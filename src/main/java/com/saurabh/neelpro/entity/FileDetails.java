package com.saurabh.neelpro.entity;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "file_details")
public class FileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nameOfPerson;

    private LocalDate localDate;

    private LocalTime localTime;

    public FileDetails() {
    }

    public FileDetails(int id, String nameOfPerson, LocalDate localDate, LocalTime localTime) {
        this.id = id;
        this.nameOfPerson = nameOfPerson;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfPerson() {
        return nameOfPerson;
    }

    public void setNameOfPerson(String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public String toString() {
        return "FileDetails{" +
                "id=" + id +
                ", nameOfPerson='" + nameOfPerson + '\'' +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                '}';
    }
}
