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
public class cuerpo {
    private MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://fercho:1234fer@cluster0.mld3v.mongodb.net/prueba?retryWrites=true&w=majority");
    private MongoClient mongoClient = new MongoClient(uri);
    private MongoDatabase database = mongoClient.getDatabase("prueba");
    
    public void insertarDatos(String tabla, Document Doc){
        //le mandamos el nombre de la tabla a insertar
//        estas son:
//        menu
//        ventas
//        clientes
//        proveedores
//        usuarios
//y mandamos el doc que contiene lo que vamos a insertar este dependera de si es menu, venta y asi
        MongoCollection<Document> collection = database.getCollection(tabla);//creo un nuevo dato en la tabla seleccionada
        try {
            collection.insertOne(Doc);
            System.out.println("Insertado correctamente. \n");
        } catch (MongoWriteException mwe) {
            if (mwe.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("Document with that id already exists");
            }
        }
        
    }
    public Document crearDocumentoMenu(String nombre, int cantidad, float precio){
        MongoCollection<Document> colleccion= database.getCollection("menu");//
        long cant = colleccion.countDocuments();
        cant += 1;//este el id que insertaremos
        Document nuevoDoc = new Document();
        nuevoDoc.append("_id", cant)
                .append("nombre", nombre)
                .append("cantidad", cantidad)
                .append("precio", precio);
        return nuevoDoc;
    }
    
}
