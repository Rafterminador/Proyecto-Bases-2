/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.url.proyectobases2;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
/**
 *
 * @author ferna
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Document nuevoDoc = new Document();
//        MongoClientURI uri = new MongoClientURI(
//            "mongodb+srv://fercho:1234fer@cluster0.mld3v.mongodb.net/prueba?retryWrites=true&w=majority");
//        MongoClient mongoClient = new MongoClient(uri);
//        MongoDatabase database = mongoClient.getDatabase("prueba");//nombre de la coleccion o tabla
//        MongoCollection<Document> collection = database.getCollection("cafeteria");//creo un documento a partir de la coleccion
        //System.out.println(collection.countDocuments());//obtiene cantidad de rows de la base de datos
//        


        //database.createCollection("usuarios");
        //System.out.println(database.getName());//obtener el nombre de la base de datos
        
        
                    //Insert a document into the "characters" collection.
            //MongoCollection collection = database.getCollection("characters");

//            Document mickeyMouse = new Document();
//            Document charlieBrown = new Document();
//
//            mickeyMouse.append("_id", 1)
//                    .append("characterName", "Mickey Mouse")
//                    .append("creator", new Document("firstName", "Walt").append("lastName", "Disney"))
//                    .append("pet", "Goofy");
//
//            charlieBrown.append("_id", 2)
//                    .append("characterName", "Charlie Brown")
//                    .append("creator", new Document("firstName", "Charles").append("lastName", "Shultz"))
//                    .append("pet", "Snoopy");
//
//            try {
//                collection.insertOne(mickeyMouse);
//                collection.insertOne(charlieBrown);
//                System.out.println("Successfully inserted documents. \n");
//            } catch (MongoWriteException mwe) {
//                if (mwe.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
//                    System.out.println("Document with that id already exists");
//                }
//            }
        
//        System.out.println("Print the documents.");//imprime los documentos
//
//        MongoCursor cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().toString());
//            }
//
//        } finally {
//            cursor.close();
//        }


        //cifrado preuba = new cifrado();
        //preuba.run();
        cuerpo aplicacion = new cuerpo();
        nuevoDoc = aplicacion.crearDocumentoMenu("pan", 2, (float) 0.4);
        aplicacion.insertarDatos("menu", nuevoDoc);
        nuevoDoc = aplicacion.crearDocumentoMenu("mantequilla", 5, (float) 3.5);
        aplicacion.insertarDatos("menu", nuevoDoc);
    }
    
}
