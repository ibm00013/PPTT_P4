
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;//Implementamos la clase java.net que utiliza Java para las comunicaciones de red.
import java.net.URL;
//import java.net.URLConnection;
//import java.net.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
//import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Protocolos de Transporte Grado en Ingenier�a Telem�tica Dpto. Ingen�er�a de
 * Telecomunicaci�n Univerisdad de Ja�n
 *
 *******************************************************
 * Pr�ctica 4. Fichero: HttpConnection.java Versi�n: 1.0 Fecha: 10/2020
 * Descripci�n: Clase sencilla de atenci�n al protocolo HTTP/1.1 Autor: Juan
 * Carlos Cuevas Mart�nez
 *
 ******************************************************
 * Alumno 1:Rafael Est�vez Torronteras
 * Alumno 2:Ignacio Beltr�n Mata
 *
 ******************************************************/
public class HttpConnection implements Runnable {

	Socket socket = null;//Atributo de la clase HttpConnection

	public HttpConnection(Socket s) {//Constructor de la clase HttpConnection
		socket = s;
	}

	@Override
	public void run() {
		DataOutputStream output = null;
		try {
			System.out.println("Starting new HTTP connection with " + socket.getInetAddress().toString());
			output = new DataOutputStream(socket.getOutputStream()); //Definici�n salida de los datos
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Definici�n de la entrada de los datos
			 
			
			
			String request = input.readLine(); //Se recoge la primera linea de petici�n
			String [] separacion = request.split(" "); 	
			String ruta = separacion[1]; //Se recoge la ruta de la petici�n
			String line = "";
			
			
			if(request.startsWith("GET")) { //Se comprueba que la petici�n empieze por GET
				
				System.out.println(request);
				
				
				if(!ruta.startsWith("/") || !separacion[2].equals("HTTP/1.1")) {//Controlamos que la ruta empiece por / y la versi�n del protocolo sea HTTP/1.1
					output.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes()); //Control de error de aplicaci�n 400 
					output.flush();
					System.out.println("ERROR: Bad Request (400)"); //Mostramos por pantalla el error
				}else {
				
				
				while (!(line=input.readLine()).equals("") && line!=null) { //Si no es nulo o est� vacio se imprime por pantalla
					System.out.println("Le�do["+line.length()+"]: "+line);
					//output.write(("ECO " + line + "\r\n").getBytes());
					//output.flush();
				}
				
				
				System.out.println(ruta);
				
				switch (ruta) {//Diferentes posibles casos de ruta: Defecto, index.html, uja.jpeg y css.css
				case "/": //Para la ruta por defecto e index.html se env�a el codigo de respuesta 200 OK y las cabeceras correspodientes.
				case "/index.html":
					output.write("HTTP/1.1 200 OK\r\n".getBytes());
			        output.write("Content-Type: text/html; image/jpeg; text/css; \r\n charset=utf-8;\r\n".getBytes());
			        output.write("Connection: close; \r\n".getBytes());
			        output.write("Content-Length: 52098;\r\n".getBytes());
			            // este linea en blanco marca el final de los headers de la response
			        output.write("\r\n".getBytes());
			            // Env�a el HTML
			  
			        File index = new File("C:\\Users\\rafae\\git\\PPTT_P4\\p4\\index.html");//Creamos una instancia de nuestro fichero index.html
					BufferedReader lector = new BufferedReader(new FileReader(index));//Buffer encargado de leer el archivo
					StringBuilder fichero = new StringBuilder();//Clase encargada de crear un String con el contenido del archivo l�nea por l�nea
					String contenido=null;
					while((contenido = lector.readLine()) != null) {//Leemos el contenido y lo almacenamos en un array llamado datosfichero
						fichero.append(contenido);
					}
					
					String html = fichero.toString();//Almacenamos todo el contenido del archivo html en una sola cadena de texto
			        
					output.write(html.getBytes());//Enviamos el archivo
					output.flush();
					break;
				case "/img/uja.jpeg":
					output.write("HTTP/1.1 200 OK\r\n".getBytes());
			        output.write("Content-Type: image/jpeg; \r\n charset=utf-8;\r\n".getBytes());
			        output.write("Connection: close; \r\n".getBytes());
			        output.write("Content-Length: 52098;\r\n".getBytes());
			            // este linea en blanco marca el final de los headers de la response
			        output.write("\r\n".getBytes());
			            // Env�a el HTML
			  
			        File imagen = new File("C:\\Users\\rafae\\git\\PPTT_P4\\p4\\img\\uja.jpeg");//Creamos una instancia de nuestro fichero index.html
			        long tamimg = imagen.length();
			        byte[] bytesimg = new byte[(int)tamimg];
			        FileInputStream fis = new FileInputStream(imagen);
			        fis.read(bytesimg);
			        fis.close();
			        
			        output.write(bytesimg);
			        output.flush();
					break;
				case "/css/css.css":
					output.write("HTTP/1.1 200 OK\r\n".getBytes());
			        output.write("Content-Type: text/css; \r\n charset=utf-8;\r\n".getBytes());
			        output.write("Connection: close; \r\n".getBytes());
			        output.write("Content-Length: 52098;\r\n".getBytes());
			            // este linea en blanco marca el final de los headers de la response
			        output.write("\r\n".getBytes());
			            // Env�a el HTML
			  
			        File style = new File("C:\\Users\\rafae\\git\\PPTT_P4\\p4\\css\\css.css");//Creamos una instancia de nuestro fichero index.html
					BufferedReader lector3 = new BufferedReader(new FileReader(style));//Buffer encargado de leer el archivo
					StringBuilder fichero3 = new StringBuilder();//Clase encargada de crear un String con el contenido del archivo l�nea por l�nea
					String contenido3=null;
					while((contenido3 = lector3.readLine()) != null) {//Leemos el contenido y lo almacenamos en un array llamado datosfichero
						fichero3.append(contenido3);
					}
					
					String html3 = fichero3.toString();//Almacenamos todo el contenido del archivo html en una sola cadena de texto
			        
					output.write(html3.getBytes());//Enviamos el archivo
					output.flush();
					break;
				default:
					output.write("HTTP/1.1 404 Not found\r\n\r\n".getBytes()); //Se envia por el salida el error 404 Not Found si no se encuentra la ruta.
					output.flush();
					System.out.println("ERROR: Not found (404)");
				}
				}

			}else {
				output.write("HTTP/1.1 405 Method not allowed\r\n\r\n".getBytes()); //Si el m�todo no es GET (admitido) se env�a al cliente el error 405 de Method Not Allowed y se imprime por pantalla
				output.flush();
				System.out.println("ERROR: M�todo no definido (405)");
			}
		} catch (IOException ex) {
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				output.close();//Se cierra el buffer de salida
				socket.close();//Cerramos la conexi�n socket
			} catch (IOException ex) {
				Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

}
