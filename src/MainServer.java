
import java.io.IOException;
import java.net.InetAddress;//Implementamos la clase java.net que utiliza Java para las comunicaciones de red.
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Protocolos de Transporte
 * Grado en Ingenier�a Telem�tica
 * Dpto. Ingen�er�a de Telecomunicaci�n
 * Univerisdad de Ja�n
 *
 *******************************************************
 * Pr�ctica 4.
 * Fichero: MainServer.java
 * Versi�n: 1.0
 * Fecha: 10/2020
 * Descripci�n:
 * 	Servidor sencillo multi-hebra Socket TCP para atenci�n al protocolo HTTP/1.1
 * Autor: Juan Carlos Cuevas Mart�nez
 *
 ******************************************************
 * Alumno 1: Ignacio Beltr�n Mata
 * Alumno 2: Rafael Est�vez Torronteras
 *
 ******************************************************/
public class MainServer {
    
    static ServerSocket server=null;//Instanciamos una variable server de la clase ServerSocket.
    static final short TCP_PORT = 80; //Definimos una constante con el puerto a utilizar en la comunicaci�n TCP.
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            InetAddress serveraddr = InetAddress.getLocalHost();//A trav�s del m�todo getLocalHost de la clase InetAdress obtenemos la direcci�n local del servidor.
            server = new ServerSocket(TCP_PORT,5,serveraddr);//Creamos el servidor llamando al constructor ServerSocket con los par�metros TCP_PORT para el puerto, '5' para el tama�o de la cola de espera y la direcci�n con serveraddr.
            System.out.println("Simple HTTP/1.1. Server waiting in "+serveraddr+" port "+TCP_PORT);//Imprime por consola mensaje de introducci�n al servidor con direcci�n y puerto.
            while(true){
                Socket s = server.accept();//Creamos el socket a trav�s de la conexi�n entrante con el m�todo accept.
                HttpConnection conn = new HttpConnection(s);//Creamos una conexi�n HTTP pasando como argumento el socket reci�n aceptado.
                new Thread(conn).start();//Inicializa la multihebra de conexiones. 
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
