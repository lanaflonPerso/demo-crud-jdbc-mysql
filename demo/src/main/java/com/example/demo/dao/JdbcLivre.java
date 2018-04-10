package com.example.demo.dao;

import com.example.demo.model.Auteur;
import com.example.demo.model.Genre;
import com.example.demo.model.Livre;
import com.example.demo.model.Principale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcLivre implements LivreDao {


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private DataSource datasource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor
     *
     * @param jdbcTemplate : the JDBCTemplace connected to the Database (thanks to Spring)
     */
    @Autowired
    public JdbcLivre(JdbcTemplate jdbcTemplate) {
        this.datasource = jdbcTemplate.getDataSource();
    }

    // Méthode pour voir toutes les info de tous les livres
    public List<Livre> listLivres() throws Exception {
        Principale livre;
        String sql;
        ArrayList<Livre> aLlistOfLivres = new ArrayList<>();

        // Preparation de la requete SQL
        sql = "SELECT *\n" + "FROM livre, auteur, genre\n" +
                "WHERE livre.id_auteur = auteur.id\n" +
                "AND livre.id_genre = genre.id;";

        try (Connection connection = this.datasource.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Log info
                    logSQL(pstmt);
                    // Parcourir les resultats de la requete
                    while (rs.next()) {
                        livre = getLivreFromResultSet(rs);
                        aLlistOfLivres.add(livre);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("SQL Error !:" + pstmt.toString(), e);
                    throw e;
                }
            }
        }
        return aLlistOfLivres;
    }

    //Fabrication de l'objet avec toutes les infos à partir de la classe modèle "Principale"
    private Principale getLivreFromResultSet(ResultSet rs) throws SQLException {
        Principale livre = new Principale();
        Auteur auteur = new Auteur();
        Genre genre = new Genre();
        //On récupère les données par colonnes
        livre.setId(rs.getLong("id"));
        livre.setTitre(rs.getString("titre"));
        livre.setAuteur(rs.getLong("id_auteur"));
        livre.setGenre(rs.getLong("id_genre"));
        auteur.setId(rs.getLong("id_auteur"));
        auteur.setNom(rs.getString("nom"));
        auteur.setPrenom(rs.getString("prenom"));
        genre.setId(rs.getLong("id_genre"));
        genre.setGenre(rs.getString("genre"));
        //On reconstruit l'Objet "Principale"
        livre.setAuteur(auteur);
        livre.setGenre(genre);

        return livre;
    }


    // Méthode pour voir un livre
    @Override
    public Livre getLivre(Long id) throws Exception {
        Livre livre = null;
        String sql;

        // Preparation de la requete SQL
        sql = "SELECT *\n" +
                "FROM livre, auteur, genre\n" +
                "WHERE livre.id_auteur = auteur.id\n" +
                "AND livre.id_genre = genre.id\n" +
                "AND livre.id = ?;";


        try (Connection connection = this.datasource.getConnection()) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                //on injecte le parametre ci dessous à la place du ? de la requete
                pstmt.setLong(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    pstmt.setLong(1, id);
                    // Log info
                    logSQL(pstmt);
                    // Handle the query results
                    while (rs.next()) {
                        livre = getLivreFromResultSet(rs);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("SQL Error !:" + pstmt.toString(), e);
                    throw e;
                }
            }
        }
        return livre;
    }

    //Pour ajouter un livre
    @Override
    public Livre addLivre(Livre livre) throws Exception {
        PreparedStatement pstmt = null;
        Livre result = null;
        try {
            // Preparation de la requete SQL
            String sql = "INSERT INTO livre (titre, id_auteur, id_genre) VALUES (?,?,?)";
            pstmt = datasource.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //on injecte le parametre ci dessous à la place des ? de la requete
            pstmt.setString(1, livre.getTitre());
            pstmt.setLong(2, livre.getAuteur());
            pstmt.setLong(3, livre.getGenre());

            // Log info
            logSQL(pstmt);

            // Run the the update query
            pstmt.executeUpdate();

            // TODO
            // recupération de l'id genere, et maj de l'acteur avec l'id et la date de modif
            ResultSet rs = pstmt.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL Error !:" + pstmt.toString(), e);
            throw e;
        } finally {
            pstmt.close();
        }

        return result;


    }

    @Override
    public Livre updateLivre(Livre livre) throws Exception {
        Livre result = null;
        PreparedStatement pstmt = null;

        try {
            // Prepare the SQL query
            String sql = "UPDATE livre SET titre = ?, id_auteur = ?, id_genre = ? WHERE id = ?";
            pstmt = datasource.getConnection().prepareStatement(sql);
            pstmt.setString(1, livre.getTitre());
            pstmt.setLong(2, livre.getAuteur() );
            pstmt.setLong(3, livre.getGenre());
            pstmt.setLong(4, livre.getId() );

            // Log info
            logSQL(pstmt);

            // Run the the update query
            int resultCount = pstmt.executeUpdate();
            if(resultCount != 1)
                throw new Exception("Livre not found !");

            result = livre;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL Error !:" + pstmt.toString(), e);
            throw e;
        } finally {
            pstmt.close();
        }

        return result;
    }




    // Méthode pour effacer un livre
    @Override
    public void deleteLivre(Long id) throws Exception {
        String sql;
        sql = "DELETE FROM livre WHERE livre.id = ?";

        try (Connection connection = this.datasource.getConnection()) {

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, id);

                try {

                    int rs = pstmt.executeUpdate();
                    pstmt.setLong(1, id);

                    // Log info
                    logSQL(pstmt);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("SQL Error !:" + pstmt.toString(), e);
                    throw e;

                }
            }
        }
    }

    //méthode pour les logs
    private void logSQL(PreparedStatement pstmt) {
        String sql;

        if (pstmt == null)
            return;

        sql = pstmt.toString().substring(":".indexOf(pstmt.toString()) + 2);
        log.debug(sql);
    }
}
