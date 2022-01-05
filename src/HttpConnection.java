
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
 * Protocolos de Transporte Grado en Ingeniería Telemática Dpto. Ingeníería de
 * Telecomunicación Univerisdad de Jaén
 *
 *******************************************************
 * Práctica 4. Fichero: HttpConnection.java Versión: 1.0 Fecha: 10/2020
 * Descripción: Clase sencilla de atención al protocolo HTTP/1.1 Autor: Juan
 * Carlos Cuevas Martínez
 *
 ******************************************************
 * Alumno 1:Rafael Estévez Torronteras
 * Alumno 2:Ignacio Beltrán Mata
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
			output = new DataOutputStream(socket.getOutputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			URL url = new URL("http://"+InetAddress.getLocalHost().getHostAddress());
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			
			urlcon.setRequestProperty("Connection", "close");
			
			String line = input.readLine();
			String request = line;
			if(line.startsWith("GET")) {
				System.out.println(line);
				while (!(line=input.readLine()).equals("") && line!=null) {
					System.out.println("Leído["+line.length()+"]: "+line);
					//output.write(("ECO " + line + "\r\n").getBytes());
					//output.flush();
				}
				String [] separacion = request.split(" "); 	
				String ruta = separacion[1];
				System.out.println(ruta);
				switch (ruta) {
				case "/":
				case "/index.html":
					output.write("HTTP/1.1 200 OK\r\n".getBytes());
			        output.write("Content-Type: text/html; image/jpeg; text/css; \r\n charset=utf-8;\r\n".getBytes());
			        output.write("Connection: close; \r\n".getBytes());
			        output.write("Content-Length: 52098;\r\n".getBytes());
			            // este linea en blanco marca el final de los headers de la response
			        output.write("\r\n".getBytes());
			            // Envía el HTML
			  
			        File index = new File("C:\\Users\\ignab\\git\\PPTT_P4\\p4\\index.html");//Creamos una instancia de nuestro fichero index.html
					BufferedReader lector = new BufferedReader(new FileReader(index));//Buffer encargado de leer el archivo
					StringBuilder fichero = new StringBuilder();//Clase encargada de crear un String con el contenido del archivo línea por línea
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
			            // Envía el HTML
			  
			        File imagen = new File("C:\\Users\\ignab\\git\\PPTT_P4\\p4\\img\\uja.jpeg");//Creamos una instancia de nuestro fichero index.html
			        long tamimg = imagen.length();
			        byte[] bytesimg = new byte[(int)tamimg];
			        FileInputStream fis = new FileInputStream(imagen);
			        fis.read(bytesimg);
			        fis.close();
			        
			        output.write(bytesimg);
			        output.flush();
			            /*
			        BufferedReader lector2 = new BufferedReader(new FileReader(imagen));//Buffer encargado de leer el archivo
					StringBuilder fichero2 = new StringBuilder();//Clase encargada de crear un String con el contenido del archivo línea por línea
					String contenido2=null;
					while((contenido2 = lector2.readLine()) != null) {//Leemos el contenido y lo almacenamos en un array llamado datosfichero
						fichero2.append(contenido2);
					}
					
					String html2 = fichero2.toString();//Almacenamos todo el contenido del archivo html en una sola cadena de texto
			        
					output.write(html2.getBytes());//Enviamos el archivo
					output.flush();
					*/
					break;
				case "/css/css.css":
					output.write("HTTP/1.1 200 OK\r\n".getBytes());
			        output.write("Content-Type: text/css; \r\n charset=utf-8;\r\n".getBytes());
			        output.write("Connection: close; \r\n".getBytes());
			        output.write("Content-Length: 52098;\r\n".getBytes());
			            // este linea en blanco marca el final de los headers de la response
			        output.write("\r\n".getBytes());
			            // Envía el HTML
			  
			        File style = new File("C:\\Users\\ignab\\git\\PPTT_P4\\p4\\css\\css.css");//Creamos una instancia de nuestro fichero index.html
					BufferedReader lector3 = new BufferedReader(new FileReader(style));//Buffer encargado de leer el archivo
					StringBuilder fichero3 = new StringBuilder();//Clase encargada de crear un String con el contenido del archivo línea por línea
					String contenido3=null;
					while((contenido3 = lector3.readLine()) != null) {//Leemos el contenido y lo almacenamos en un array llamado datosfichero
						fichero3.append(contenido3);
					}
					
					String html3 = fichero3.toString();//Almacenamos todo el contenido del archivo html en una sola cadena de texto
			        
					output.write(html3.getBytes());//Enviamos el archivo
					output.flush();
					break;
				default:
					output.write("HTTP/1.1 404 Not found\r\n\r\n".getBytes());
					output.flush();
					System.out.println("ERROR: Not found (404)");
				}
				
			}else {
				output.write("HTTP/1.1 405 Method not allowed\r\n\r\n".getBytes());
				output.flush();
				System.out.println("ERROR: Método no definido (405)");
			}
			
			//output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
			//output.flush();
			
			
			//URL urlObj = new URL("https://google.es");
			//HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
			/*
			File index = new File("C:\\Users\\ignab\\git\\PPTT_P4\\p4\\index.html");//Creamos una instancia de nuestro fichero index.html
			//FileOutputStream foutput = new FileOutputStream(index);
			FileInputStream finput = new FileInputStream(index);
			byte[] datosfichero = new byte[1024];
			
			while(finput.available() > 0) {//Leemos el contenido y lo almacenamos en un array llamado datosfichero
				finput.read(datosfichero);
				output.write(datosfichero);//Enviamos al cliente el contenido del fichero
				output.flush();
			}
			//String text = "<html><head></head><body><h1>HOLA</h1></body></html>";
			//foutput.write(text.getBytes());
		
			//System.out.println("El método es : "+httpCon.getRequestMethod()+ ", la ruta es: "+httpCon.getURL()+ " y la versión del protocolo y código de respuesta es: "+httpCon.getHeaderField(0));
			
			/*if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {// HTTP_OK es 200
			System.out.println("El servidor devolvió el código de respuesta" + httpCon.getResponseCode());
			System.exit(0);
			}
			else {*/
				
				//finput.close();
			
			
		} catch (IOException ex) {
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				output.close();
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

}
