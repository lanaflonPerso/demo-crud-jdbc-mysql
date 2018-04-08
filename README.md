# demo-crud-jdbc-mysql

1. Télécharger le repo  
2. Modifier les données du fichier 'application.properties' en fonction de ça configuration Mysql
```
##config MySQL
spring.datasource.url =   jdbc:mysql://localhost:3306/livre?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username = admin
spring.datasource.password = admin
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
La BDD livre est créer directement à partir des données du SCRIPT SQL 'schema.sql' dans le package ressources (création des tables livre, auteur, genre)
Les tables sont alimentées automatiquement à partir du SCRIPT SQL 'data.sql' dans le package ressources

3.Les API 
>>http://localhost:8080/api/livres (pour voir toutes les données de tous les livres GET)  
>>http://localhost:8080/api/livre/{id} (pour voir ou supprimer un livre GET/DELETE)  
>>http://localhost:8080/api/livre (pour ajouter un livre POST format attendu de type JSON ex: {"titre": "machin5","auteur": 2,"genre": 3})
