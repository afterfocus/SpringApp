package com.afterfocus.springapp.model;

import javax.persistence.*;

@Entity
@Table(name = "DISK")
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "disk_id", nullable = false)
    private int diskID;

    @Column(name = "rus_title", length = 80)
    private String rusTitle;

    @Column(name = "eng_title", length = 80)
    private String engTitle;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "person_id")
    private Person person;

    public Disk() {
    }

    public Disk(String rusTitle, String engTitle, int releaseYear) {
        this.rusTitle = rusTitle;
        this.engTitle = engTitle;
        this.releaseYear = releaseYear;
    }

    public Disk(int id, String rusTitle, String engTitle, int releaseYear, Person person) {
        this.diskID = id;
        this.rusTitle = rusTitle;
        this.engTitle = engTitle;
        this.releaseYear = releaseYear;
        this.person = person;
    }

    public int getDiskID() {
        return diskID;
    }

    public void setDiskID(int diskID) {
        this.diskID = diskID;
    }

    public String getRusTitle() {
        return rusTitle;
    }

    public void setRusTitle(String rusTitle) {
        this.rusTitle = rusTitle;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) { this.person = person; }
}
