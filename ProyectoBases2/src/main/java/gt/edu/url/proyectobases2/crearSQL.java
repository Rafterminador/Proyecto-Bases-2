/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.url.proyectobases2;
import java.io.File;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

import com.mongodb.ErrorCategory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.io.FileWriter;
/**
 *
 * @author ferna
 */
public class crearSQL {
    public void crear(){
        MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://fercho:1234fer@cluster0.mld3v.mongodb.net/prueba?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("prueba");//nombre de la coleccion o tabla
        MongoCollection<Document> collection = database.getCollection("menu");//creo un documento a partir de la coleccion

        String bdNombre = database.getName();//obtener el nombre de la base de datos
        String path = new File("src/main/java/gt/edu/url/proyectobases2/"+bdNombre+".sql")
                .getAbsolutePath();//esto para obtener la ruta total del sistema
        //System.out.println(path);imprime la ruta de donde se creo el archivo
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
              //System.out.println("File created: " + myObj.getName());
            } else {//ya existia antes un archivo entonces sobreescribo
                if (myObj.delete()) { 
                    //System.out.println("Deleted the file: " + myObj.getName());
                }
                myObj.createNewFile();
            }
            //escribo el archivo sql
            FileWriter myWriter = new FileWriter(path);
            myWriter.write("DROP DATABASE IF EXISTS prueba;"
                    +"\nCREATE DATABASE prueba;"
                    +"\nUSE prueba;");
            myWriter.write("\nCREATE TABLE otra (\n" +
           "\n" +
           ");\n" +
           "COLLATE='latin1_swedish_ci';");
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred with files");
        }
    }
}
