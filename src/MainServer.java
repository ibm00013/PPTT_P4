
import java.io.IOException;
import java.net.InetAddress;//Implementamos la clase java.net que utiliza Java para las comunicaciones de red.
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Protocolos de Transporte
 * Grado en Ingeniería Telemática
 * Dpto. Ingeníería de Telecomunicación
 * Univerisdad de Jaén
 *
 *******************************************************
 * Práctica 4.
 * Fichero: MainServer.java
 * Versión: 1.0
 * Fecha: 10/2020
 * Descripción:
 * 	Servidor sencillo multi-hebra Socket TCP para atención al protocolo HTTP/1.1
 * Autor: Juan Carlos Cuevas Martínez
 *
 ******************************************************
 * Alumno 1: Ignacio Beltrán Mata
 * Alumno 2: Rafael Estévez Torronteras
 *
 ******************************************************/
public class MainServer {
    
    static ServerSocket server=null;//Instanciamos una variable server de la clase ServerSocket.
    static final short TCP_PORT = 80; //Definimos una constante con el puerto a utilizar en la comunicación TCP.
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            InetAddress serveraddr = InetAddress.getLocalHost();//A través del método getLocalHost de la clase InetAdress obtenemos la dirección local del servidor.
            server = new ServerSocket(TCP_PORT,5,serveraddr);//Creamos el servidor llamando al constructor ServerSocket con los parámetros TCP_PORT para el puerto, '5' para el tamaño de la cola de espera y la dirección con serveraddr.
            System.out.println("Simple HTTP/1.1. Server waiting in "+serveraddr+" port "+TCP_PORT);//Imprime por consola mensaje de introducción al servidor con dirección y puerto.
            while(true){
                Socket s = server.accept();//Creamos el socket a través de la conexión entrante con el método accept.
                HttpConnection conn = new HttpConnection(s);//Creamos una conexión HTTP pasando como argumento el socket recién aceptado.
                new Thread(conn).start();//Inicializa la multihebra de conexiones. 
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
