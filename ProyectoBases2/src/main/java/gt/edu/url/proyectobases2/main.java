/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.url.proyectobases2;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author ferna
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://fercho:1234fer@cluster0.mld3v.mongodb.net/prueba?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("prueba");//nombre de la coleccion
        //MongoCollection<Document> collection = database.getCollection("prueba");//creo un documento a partir de la coleccion
        //database.createCollection("usuarios");
        //System.out.println(database.getName());//obtener el nombre de la base de datos
        
        
    }
    
}
