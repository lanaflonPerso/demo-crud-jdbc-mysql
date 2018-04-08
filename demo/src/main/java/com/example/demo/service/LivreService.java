package com.example.demo.service;

import com.example.demo.dao.LivreDao;
import com.example.demo.model.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    @Autowired
    private LivreDao livreDao;

    // Retrouve toutes les données des livres
    public List<Livre> getAllLivres() throws Exception {
        return livreDao.listLivres();
    }

    // Retrouve toutes les données d'un livre
    public Livre getLivre(Long id) throws Exception {
        return livreDao.getLivre(id);
    }

    // Ajouter un livre
    public Livre addLivre(Livre livre) throws Exception {
        return livreDao.addLivre(livre);
    }

    // Modifier un livre
    public Livre updateLivre(Long id, Livre livre) throws Exception {
        return livreDao.updateLivre(livre);
    }

    // Supprimer un livre
    public void deleteLivre(Long id) throws Exception {
        livreDao.deleteLivre(id);
    }
}

