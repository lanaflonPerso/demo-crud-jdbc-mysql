package com.example.demo.dao;

import com.example.demo.model.Livre;

import java.util.List;

public interface LivreDao {

    // creation du contrat des méthodes à utiliser, elle seront implémenté dans JDBCLivre
    public List<Livre> listLivres() throws Exception;

    public Livre getLivre(Long id) throws Exception;

    public Livre addLivre(Livre livre) throws Exception;

    public Livre updateLivre(Livre livre) throws Exception;

    public void deleteLivre(Long id) throws Exception;

}
