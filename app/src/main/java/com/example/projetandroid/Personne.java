package com.example.projetandroid;


import java.util.Locale;

public class Personne extends User {

    private int age;

    public Personne() {}

    private enum Genre {MALE,FEMALE}

    private enum BloodPressure {HIGH,LOW,NORMAL}

    private enum Cholesterol {HIGH,NORMAL}

    private int na;

    private int k;

    Genre genre;

    BloodPressure bloodPressure;

    Cholesterol cholesterol;

    public int getAge() {
        return age;
    }

    public String getGenre() {
        return genre.toString();
    }

    public String getBloodPressure() {
        return bloodPressure.toString();
    }

    public String getCholesterol() {
        return cholesterol.toString();
    }

    public int getNa() {
        return na;
    }

    public int getK() {
        return k;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGenre(String genre) {
        this.genre = Genre.valueOf(genre);
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
    }

    public void setCholesterol(String  cholesterol) {
        this.cholesterol = Cholesterol.valueOf(cholesterol);
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setNa(int na) {
        this.na = na;
    }


    public Personne(
            String username, String password,
            int age, String genre, String bloodPressure,
                    String cholesterol, int na , int k) {
        super(username, password);
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;
    }
}
