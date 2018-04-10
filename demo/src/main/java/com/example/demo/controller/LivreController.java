package com.example.demo.controller;

import com.example.demo.model.Livre;
import com.example.demo.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LivreController {

    @Autowired
    private LivreService livreService;

    //localhost:8080/api/livres pour voir toutes les infos des livres
    @RequestMapping(value = "/livres", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLivres() {
        List<Livre> listLivre = null;
        try {
            listLivre = livreService.getAllLivres();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(listLivre);
    }
    //localhost:8080/api/livre/{id} pour voir toutes les infos d'un livre
    @RequestMapping(value = "/livre/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLivre(@PathVariable Long id) {
        Livre livre = null;

        try {
            livre = livreService.getLivre(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        if (livre == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(livre);
    }
    //localhost:8080/api/livre pour créer un livre (ex :  {"titre": "machin5","auteur": 2,"genre": 3})
    @RequestMapping(value = "/livre", method = RequestMethod.POST)
    public ResponseEntity<?> addLivre(@RequestBody Livre livre) {
        Livre resultLivre = null;
        String titre = livre.getTitre();
        if ((titre == null) || (titre.isEmpty()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le titre n'est pas défini ! !");

        try {
            livreService.addLivre(livre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resultLivre);
    }

    @RequestMapping(value = "/livre/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLivre(@RequestBody Livre livre,@PathVariable Long id) throws Exception {
        Livre result = null;
        
        try {
            result = livreService.updateLivre(id, livre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //localhost:8080/api/livre/{id} pour supprimer un livre
    @RequestMapping(value = "/livre/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLivre(@PathVariable Long id) {
        try {
            livreService.deleteLivre(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
