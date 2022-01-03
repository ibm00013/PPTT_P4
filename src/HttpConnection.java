
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;//Implementamos la clase java.net que utiliza Java para las comunicaciones de red.
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	Socket socket = null;

	public HttpConnection(Socket s) {
		socket = s;
	}

	@Override
	public void run() {
		DataOutputStream dos = null;
		try {
			System.out.println("Starting new HTTP connection with " + socket.getInetAddress().toString());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
			dos.flush();
			BufferedReader bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			URL urlObj = new URL("https://google.com");
			HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
			
		
			System.out.println("El método es : "+httpCon.getRequestMethod()+ ", la ruta es: "+httpCon.getURL()+ " y la versión del protocolo y código de respuesta es: "+httpCon.getHeaderField(0));
			
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {// HTTP_OK es 200
			System.out.println("El servidor devolvió el código de respuesta" + httpCon.getResponseCode()
			+ ". Descarga fallida.");
			System.exit(0);
			}
			else {
				String line = bis.readLine();
				while (!(line=bis.readLine()).equals("") && line!=null) {
					System.out.println("Leído["+line.length()+"]: "+line);
					dos.write(("ECO " + line + "\r\n").getBytes());
					dos.flush();
			}
			
			
			}
		} catch (IOException ex) {
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				dos.close();
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

}
