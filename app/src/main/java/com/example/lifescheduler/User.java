package com.example.lifescheduler;

public class User {
    private Integer id;
    private String numeUtilizator;
    private String parolaUtilizator;
    private String emailUtilizator;
    private Integer nivel_de_acces;

    public User() {
    }

    public User(Integer id, String numeUtilizator, String parolaUtilizator, String emailUtilizator, Integer nivel_de_acces) {
        this.id = id;
        this.numeUtilizator = numeUtilizator;
        this.parolaUtilizator = parolaUtilizator;
        this.emailUtilizator = emailUtilizator;
        this.nivel_de_acces = nivel_de_acces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getParolaUtilizator() {
        return parolaUtilizator;
    }

    public void setParolaUtilizator(String parolaUtilizator) {
        this.parolaUtilizator = parolaUtilizator;
    }

    public String getEmailUtilizator() {
        return emailUtilizator;
    }

    public void setEmailUtilizator(String emailUtilizator) {
        this.emailUtilizator = emailUtilizator;
    }

    public Integer getNivel_de_acces() {
        return nivel_de_acces;
    }

    public void setNivel_de_acces(Integer nivel_de_acces) {
        this.nivel_de_acces = nivel_de_acces;
    }
}
