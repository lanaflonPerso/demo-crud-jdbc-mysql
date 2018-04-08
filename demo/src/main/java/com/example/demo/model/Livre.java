package com.example.demo.model;

public class Livre {

    private Long id;
    private String titre;
    private Long auteur;
    private Long genre;


    public Livre() {
    }

    public Livre(Long id, String titre, Long auteur, Long genre) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getAuteur() {
        return auteur;
    }

    public void setAuteur(Long auteur) {
        this.auteur = auteur;
    }

    public Long getGenre() {
        return genre;
    }

    public void setGenre(Long genre) {
        this.genre = genre;
    }
}