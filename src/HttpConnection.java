
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
//import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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
					System.out.println("Le�do["+line.length()+"]: "+line);
					//output.write(("ECO " + line + "\r\n").getBytes());
					//output.flush();
				}
				output.write("HTTP/1.1 200 OK\r\n".getBytes());
		        output.write("Content-Type: text/html;\r\n charset=utf-8;\r\n".getBytes());
		        output.write("Connection: close; \r\n".getBytes());
		        output.write("Server: MINISERVER;\r\n".getBytes());
		            // este linea en blanco marca el final de los headers de la response
		        output.write("\r\n".getBytes());
		            // Env�a el HTML
		        output.write("<H1>Bienvenido al Mini Server : 'EL PATRON'</H1>".getBytes());
		        output.write(("<H2>Request Method->" + request + "</H2>").getBytes());
		        output.write("<p>Raestoestofufa Raestoestofufa Raestoestofufa Raestoestofufa Raestoestofufa</p>".getBytes());
		  
				output.flush();
			}else {
				System.out.println("ERROR 405.");
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
		
			//System.out.println("El m�todo es : "+httpCon.getRequestMethod()+ ", la ruta es: "+httpCon.getURL()+ " y la versi�n del protocolo y c�digo de respuesta es: "+httpCon.getHeaderField(0));
			
			/*if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {// HTTP_OK es 200
			System.out.println("El servidor devolvi� el c�digo de respuesta" + httpCon.getResponseCode());
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
