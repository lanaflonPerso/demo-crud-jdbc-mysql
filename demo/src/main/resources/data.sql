INSERT IGNORE INTO `livre` (`id`, `titre`, `id_auteur`, `id_genre`) VALUES (NULL, 'hernany', '2', '2'),(NULL, 'les miserables', '2', '2'),(NULL, 'le père Goriot', '3', '4');

INSERT IGNORE INTO `auteur` (`id`,`nom`,`prenom`) VALUES (NULL,'Hugo','Victor'),(NULL,'Balzac','Honoré');

INSERT IGNORE INTO `genre` (`id`,`genre`) VALUES (NULL,'Classique'),(NULL,'Historique'),(NULL,'Fiction');