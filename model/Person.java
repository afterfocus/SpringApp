package com.afterfocus.springapp.model;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "person_id", nullable = false)
    private int personID;

    @Column(name = "surname", length = 80, nullable = false)
    private String surname;

    @Column(name = "name", length = 80, nullable = false)
    private String name;

    @Column(name = "phonenumber", length = 80)
    private String phonenumber;

    @Transient
    private int issuedDisks;

    public Person() {
    }

    public Person(int id, String surname, String name, String phonenumber, int issuedDisks) {
        this.personID = id;
        this.surname = surname;
        this.name = name;
        this.phonenumber = phonenumber;
        this.issuedDisks = issuedDisks;
    }

    public Person(String surname, String name, String phonenumber) {
        this.surname = surname;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getIssuedDisks() {
        return issuedDisks;
    }

    public void setIssuedDisks(int issuedDisks) {
        this.issuedDisks = issuedDisks;
    }
}
