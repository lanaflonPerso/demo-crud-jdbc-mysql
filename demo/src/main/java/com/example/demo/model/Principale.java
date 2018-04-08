package com.example.demo.model;

public class Principale extends Livre  {

    private Livre livre;
    private Auteur auteur;
    private Genre genre;


    public Auteur getObjetAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }


    public Genre getObjetGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
