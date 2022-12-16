package com.example.projetandroid;

public class Personne {

    private int id;

    private String username;

    private int age;

    private enum Genre {M,F}

    private enum BloodPressure {HIGH,LOW,NORMAL}

    private enum Cholesterol {HIGH,NORMAL}

    private double na;

    private double k;

    Genre genre;

    BloodPressure bloodPressure;

    Cholesterol cholesterol;

    private String drug;

    // getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

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

    public double getNa() {
        return na;
    }

    public double getK() {
        return k;
    }

    public String getDrug() {
        return drug;
    }

    // setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setK(double k) {
        this.k = k;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    // constructors

    public Personne() {}

    public Personne(
            String username,
            int age, String genre, String bloodPressure,
                    String cholesterol, double na , double k , String drug) {
        this.username = username;
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;
        this.drug = drug;
    }

    public Personne(
            int age, String genre, String bloodPressure,
            String cholesterol, double na , double k , String drug) {
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;
        this.drug = drug;
    }

    public Personne(
            int age, String genre, String bloodPressure,
            String cholesterol, double na , double k ) {
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;

    }

}
