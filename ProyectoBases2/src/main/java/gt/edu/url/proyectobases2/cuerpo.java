/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.url.proyectobases2;
import com.mongodb.DBObject;
import com.mongodb.ErrorCategory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.bson.Document;
import org.json.*;  
import java.util.*; 
/**
 *
 * @author ferna
 */
public class cuerpo {
    private MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://fercho:1234fer@cluster0.mld3v.mongodb.net/prueba?retryWrites=true&w=majority");
    private MongoClient mongoClient = new MongoClient(uri);
    private MongoDatabase database = mongoClient.getDatabase("prueba");
    ArrayList<Dictionary> listaClientes=new ArrayList<Dictionary>();
    ArrayList<Dictionary> listaMenus=new ArrayList<Dictionary>();
    ArrayList<Dictionary> listaProveedores=new ArrayList<Dictionary>();
    ArrayList<Dictionary> listaUsuarios=new ArrayList<Dictionary>();
    ArrayList<Dictionary> listaVentas=new ArrayList<Dictionary>();
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
    public Document crearDocumentoCliente(String nombre, int telefono, int nit, String direccion){
        MongoCollection<Document> colleccion= database.getCollection("clientes");//
        long cant = colleccion.countDocuments();
        cant += 1;//este el id que insertaremos
        Document nuevoDoc = new Document();
        nuevoDoc.append("_id", cant)
                .append("nombre", nombre)
                .append("telefono", telefono)
                .append("nit", nit)
                .append("direccion", direccion);
        return nuevoDoc;
    }
    public Document crearDocumentoUsuario(String nombre, int acceso, String contra){
        MongoCollection<Document> colleccion= database.getCollection("usuarios");//
        long cant = colleccion.countDocuments();
        cant += 1;//este el id que insertaremos
        Document nuevoDoc = new Document();
        cifrado cifrar = new cifrado();
        contra = cifrar.run(contra);
        System.out.println(contra + "contra");
        nuevoDoc.append("_id", cant)
                .append("nombre", nombre)
                .append("acceso", acceso)
                .append("password", contra);
        return nuevoDoc;
    }
    public Document crearDocumentoProveedor(String nombre, int telefono, String empresa, String direccion){
        MongoCollection<Document> colleccion= database.getCollection("proveedores");//
        long cant = colleccion.countDocuments();
        cant += 1;//este el id que insertaremos
        Document nuevoDoc = new Document();
        nuevoDoc.append("_id", cant)
                .append("nombre", nombre)
                .append("telefono", telefono)
                .append("empresa", empresa)
                .append("direccion", direccion);      
        return nuevoDoc;
    }//existencias hace referencia a cuantas unidades compro y float al total gastado
    public Document crearDocumentoVenta(int clienteId, int productoId, int existencias, double precio){
        MongoCollection<Document> colleccion= database.getCollection("ventas");//
        long cant = colleccion.countDocuments();
        cant += 1;//este el id que insertaremos
        Document nuevoDoc = new Document();
        double total2 = Math.round(precio*100) / 100.0;//decimal de dos digitos
        nuevoDoc.append("_id", cant)
                .append("clienteId", clienteId)
                .append("productoId", productoId)
                .append("existencias", existencias)
                .append("precio", total2);      
        return nuevoDoc;
    }
    public void leerClientes() {
        listaClientes.clear();
        MongoCollection<Document> colleccion= database.getCollection("clientes");//
        MongoCursor cursor = colleccion.find().iterator();
        String datos = "";
        JSONObject jo;
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toString());
                datos = cursor.next().toString();
                
                datos = datos.substring(9, datos.length()-1);//es que por defecto mongo trae Document{...} entonces quito la palabra document
                datos = datos.replace("=", ":");
                String json = "";
                for(int i = 0; i < datos.length(); i++){
                    if(datos.charAt(i) == ':' || datos.charAt(i) == '}' || datos.charAt(i) == ','){
                        json += "\"";
                    }
                    json += datos.charAt(i);
                    if(datos.charAt(i) == '{'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ' '){
                        if(datos.charAt(i-1) == ','){
                            json += "\"";
                        }
                    }
                    if(datos.charAt(i) == ':'){
                        json += "\"";
                    }
                    //System.out.println("dato"+ datos.charAt(i));
                }
                            
                jo = new JSONObject(json);
                Dictionary edu = new Hashtable();
                edu.put("_id", jo.getString("_id")); 
                edu.put("nombre", jo.getString("nombre"));
                edu.put("telefono", jo.getString("telefono"));
                edu.put("nit", jo.getString("nit"));
                edu.put("direccion", jo.getString("direccion"));
                listaClientes.add(edu);
                
            }

        } finally {
            cursor.close();
        }
    }
    public void leerClienteLista(){//aqui leeremos los datos que nos de mongo
        leerClientes();
        for (int i = 0; i < listaClientes.size(); i ++){
            System.out.println("_id:" + listaClientes.get(i).get("_id"));
            System.out.println("nombre:" + listaClientes.get(i).get("nombre"));
            System.out.println("telefono:" + listaClientes.get(i).get("telefono"));
            System.out.println("nit:" + listaClientes.get(i).get("nit"));
            System.out.println("direccion:" + listaClientes.get(i).get("direccion"));
            System.out.println("");
        }
    }
     public void leerMenu() {
        listaMenus.clear();
        MongoCollection<Document> colleccion= database.getCollection("menu");//
        MongoCursor cursor = colleccion.find().iterator();
        String datos = "";
        JSONObject jo;
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toString());
                datos = cursor.next().toString();
                
                datos = datos.substring(9, datos.length()-1);//es que por defecto mongo trae Document{...} entonces quito la palabra document
                datos = datos.replace("=", ":");
                String json = "";
                for(int i = 0; i < datos.length(); i++){
                    if(datos.charAt(i) == ':' || datos.charAt(i) == '}' || datos.charAt(i) == ','){
                        json += "\"";
                    }
                    json += datos.charAt(i);
                    if(datos.charAt(i) == '{'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ' '){
                        if(datos.charAt(i-1) == ','){
                            json += "\"";
                        }
                    }
                    if(datos.charAt(i) == ':'){
                        json += "\"";
                    }
                    //System.out.println("dato"+ datos.charAt(i));
                }
                            
                jo = new JSONObject(json);
                Dictionary edu = new Hashtable();
                edu.put("_id", jo.getString("_id")); 
                edu.put("nombre", jo.getString("nombre"));
                edu.put("cantidad", jo.getString("cantidad"));
                edu.put("precio", jo.getString("precio"));
                listaMenus.add(edu);
                
            }

        } finally {
            cursor.close();
        }
    }
    public void leerMenuLista(){//aqui leeremos los datos que nos de mongo
        leerMenu();
        for (int i = 0; i < listaMenus.size(); i ++){
            System.out.println("_id:" + listaMenus.get(i).get("_id"));
            System.out.println("nombre:" + listaMenus.get(i).get("nombre"));
            System.out.println("cantidad:" + listaMenus.get(i).get("cantidad"));
            System.out.println("precio:" + listaMenus.get(i).get("precio"));
            System.out.println("");
        }
    }
    public void leerProveedores() {
        listaProveedores.clear();
        MongoCollection<Document> colleccion= database.getCollection("proveedores");//
        MongoCursor cursor = colleccion.find().iterator();
        String datos = "";
        JSONObject jo;
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toString());
                datos = cursor.next().toString();
                
                datos = datos.substring(9, datos.length()-1);//es que por defecto mongo trae Document{...} entonces quito la palabra document
                datos = datos.replace("=", ":");
                String json = "";
                for(int i = 0; i < datos.length(); i++){
                    if(datos.charAt(i) == ':' || datos.charAt(i) == '}' || datos.charAt(i) == ','){
                        json += "\"";
                    }
                    json += datos.charAt(i);
                    if(datos.charAt(i) == '{'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ' '){
                        if(datos.charAt(i-1) == ','){
                            json += "\"";
                        }
                    }
                    if(datos.charAt(i) == ':'){
                        json += "\"";
                    }
                    //System.out.println("dato"+ datos.charAt(i));
                }
                            
                jo = new JSONObject(json);
                Dictionary edu = new Hashtable();
                edu.put("_id", jo.getString("_id")); 
                edu.put("nombre", jo.getString("nombre"));
                edu.put("telefono", jo.getString("telefono"));
                edu.put("empresa", jo.getString("empresa"));
                edu.put("direccion", jo.getString("direccion"));
                listaProveedores.add(edu);    
            }

        } finally {
            cursor.close();
        }
    }
    public void leerMenuProveedores(){//aqui leeremos los datos que nos de mongo
        leerProveedores();
        for (int i = 0; i < listaProveedores.size(); i ++){
            System.out.println("_id:" + listaProveedores.get(i).get("_id"));
            System.out.println("nombre:" + listaProveedores.get(i).get("nombre"));
            System.out.println("telefono:" + listaProveedores.get(i).get("telefono"));
            System.out.println("empresa:" + listaProveedores.get(i).get("empresa"));
            System.out.println("direccion:" + listaProveedores.get(i).get("direccion"));
            System.out.println("");
        }
    }
    public void leerUsuarios() {
        listaUsuarios.clear();
        MongoCollection<Document> colleccion= database.getCollection("usuarios");//
        MongoCursor cursor = colleccion.find().iterator();
        String datos = "";
        JSONObject jo;
        boolean cifrar = false;
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toString());
                datos = cursor.next().toString();   
                datos = datos.substring(9, datos.length()-1);//es que por defecto mongo trae Document{...} entonces quito la palabra document
                datos = datos.replace("=", ":");
                String json = "";
                for(int i = 0; i < datos.length(); i++){
                    if(datos.charAt(i) == '$' ){
                        cifrar = true;      
                    }
                    if(datos.charAt(i) == '}'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ':' && cifrar != true){//esto por el cifrado
                        json += "\"";
                    }
                    if(datos.charAt(i) == ',' && cifrar != true){
                        json += "\"";
                    }
                    json += datos.charAt(i);
                    if(datos.charAt(i) == '{'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ' '){
                        if(datos.charAt(i-1) == ',' && cifrar != true){
                            json += "\"";
                        }
                    }
                    if(datos.charAt(i) == ':' && cifrar != true){//esto por el cifrado
                        json += "\"";
                    }
                    //System.out.println("dato"+ datos.charAt(i));
                }
//                System.out.println("este es json" + json);           
                jo = new JSONObject(json);
                Dictionary edu = new Hashtable();
                edu.put("_id", jo.getString("_id")); 
                edu.put("nombre", jo.getString("nombre"));
                edu.put("acceso", jo.getString("acceso"));
                edu.put("password", jo.getString("password"));
                listaUsuarios.add(edu);
                cifrar = false;
            }

        } finally {
            cursor.close();
        }
    }
    public void leerMenuUsuarios(){//aqui leeremos los datos que nos de mongo
        leerUsuarios();
        for (int i = 0; i < listaUsuarios.size(); i ++){
            System.out.println("_id:" + listaUsuarios.get(i).get("_id"));
            System.out.println("nombre:" + listaUsuarios.get(i).get("nombre"));
            System.out.println("acceso:" + listaUsuarios.get(i).get("acceso"));
            System.out.println("password:" + listaUsuarios.get(i).get("password"));
            System.out.println("");
        }
    }
     public void leerVentas() {
        listaVentas.clear();
        MongoCollection<Document> colleccion= database.getCollection("ventas");//
        MongoCursor cursor = colleccion.find().iterator();
        String datos = "";
        JSONObject jo;
        try {
            while (cursor.hasNext()) {
                //System.out.println(cursor.next().toString());
                datos = cursor.next().toString();
                
                datos = datos.substring(9, datos.length()-1);//es que por defecto mongo trae Document{...} entonces quito la palabra document
                datos = datos.replace("=", ":");
                String json = "";
                for(int i = 0; i < datos.length(); i++){
                    if(datos.charAt(i) == ':' || datos.charAt(i) == '}' || datos.charAt(i) == ','){
                        json += "\"";
                    }
                    json += datos.charAt(i);
                    if(datos.charAt(i) == '{'){
                        json += "\"";
                    }
                    if(datos.charAt(i) == ' '){
                        if(datos.charAt(i-1) == ','){
                            json += "\"";
                        }
                    }
                    if(datos.charAt(i) == ':'){
                        json += "\"";
                    }
                    //System.out.println("dato"+ datos.charAt(i));
                }
                System.out.println(json);            
                jo = new JSONObject(json);
                Dictionary edu = new Hashtable();
                edu.put("_id", jo.getString("_id")); 
                edu.put("clienteId", jo.getString("clienteId"));
                edu.put("productoId", jo.getString("productoId"));
                edu.put("existencias", jo.getString("existencias"));
                edu.put("precio", jo.getString("precio"));
                listaVentas.add(edu);    
            }

        } finally {
            cursor.close();
        }
    }
    public void leerMenuVentas(){//aqui leeremos los datos que nos de mongo
        leerVentas();
        for (int i = 0; i < listaVentas.size(); i ++){
            System.out.println("_id:" + listaVentas.get(i).get("_id"));
            System.out.println("clientId:" + listaVentas.get(i).get("clienteId"));
            System.out.println("productoId:" + listaVentas.get(i).get("productoId"));
            System.out.println("existencias:" + listaVentas.get(i).get("existencias"));
            System.out.println("precio:" + listaVentas.get(i).get("precio"));
            System.out.println("");
        }
    }
}

