
package EstructuraNoLineales;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Arbol {
    
    NodoArbol inicial;
    private static int contadorImagen = 1; // Contador de imágenes generadas
    
    public Arbol(){
        this.inicial=null;
    }
    
    public void insertar(int valor){
        if(this.inicial == null){
            this.inicial = new NodoArbol(valor);
        }else{
            this.inicial.insertar(valor);
        }
    }
    
    public void dispararPreorden(){
        this.preorden(this.inicial);
    }
    public void preorden(NodoArbol nodo){
        if(nodo == null){
            return;
        }else{
            System.out.print(nodo.getValor()+" , ");
            preorden(nodo.getNodoIzq());
            preorden(nodo.getNodoDerecho());
            
        }
    }
    
    public void dispararInorden(){
        this.Inorden(this.inicial);
    }
    public void Inorden(NodoArbol nodo){
        if(nodo == null){
            return;
        }else{
            
            Inorden(nodo.getNodoIzq());
            System.out.print(nodo.getValor()+" , ");
            Inorden(nodo.getNodoDerecho());
            
        }
    }
    public void dispararPostorden(){
        this.postorden(this.inicial);
    }
    public void postorden(NodoArbol nodo){
        if(nodo == null){
            return;
        }else{
            
            postorden(nodo.getNodoIzq());           
            postorden(nodo.getNodoDerecho());
            System.out.print(nodo.getValor()+" , ");
            
        }
    }
    
    public void dibujarGraphviz() {
        String rutaProyecto = System.getProperty("user.dir");
        String rutaArchivoDot = rutaProyecto + File.separator + "arbol.dot";
        String rutaImagen = rutaProyecto + File.separator + "arbol_" + contadorImagen + ".png";

        try {
            // Generar el archivo DOT
            generarArchivoDot(rutaArchivoDot);

            // Ejecutar Graphviz para convertir el archivo DOT en una imagen PNG
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "-o", rutaImagen, rutaArchivoDot);
            pb.redirectErrorStream(true);
            pb.start();

            // Eliminar la imagen anterior (si existe)
            File imagenAnterior = new File(rutaProyecto + File.separator + "arbol_" + (contadorImagen - 1) + ".png");
            if (imagenAnterior.exists()) {
                imagenAnterior.delete();
            }

            contadorImagen++; // Incrementar el contador de imágenes
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarArchivoDot(String rutaArchivo) throws IOException {
        FileWriter fw = new FileWriter(rutaArchivo);
        PrintWriter pw = new PrintWriter(fw);

        pw.println("digraph G {");
        pw.println("node[shape = circle]");
        pw.println("node[style = filled]");
        pw.println("node[fillcolor = \"#EEEEE\"]");
        pw.println("node[color = \"#EEEEE\"]");
        pw.println("edge[color = \"#31CEF0\"]");

        // Agregar código para generar el contenido del archivo DOT basado en el árbol
        generarContenidoDot(inicial, pw);

        pw.println("}");

        pw.close();
    }

    private void generarContenidoDot(NodoArbol nodo, PrintWriter pw) {
        if (nodo != null) {
            pw.println(nodo.getValor() + ";");
            if (nodo.getNodoIzq() != null) {
                pw.println(nodo.getValor() + " -> " + nodo.getNodoIzq().getValor() + ";");
                generarContenidoDot(nodo.getNodoIzq(), pw);
            }
            if (nodo.getNodoDerecho() != null) {
                pw.println(nodo.getValor() + " -> " + nodo.getNodoDerecho().getValor() + ";");
                generarContenidoDot(nodo.getNodoDerecho(), pw);
            }
        }
    }
}
