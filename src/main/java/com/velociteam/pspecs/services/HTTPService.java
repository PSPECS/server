package com.velociteam.pspecs.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class HTTPService {
	
	static final String SERVER_KEY = "key=AIzaSyD4YbI1sDD67J1EKxd5CBfrhhorJJI7cDQ";
	static final String JSON_CONTENT_TYPE = "application/json";

	public void sendPost(String path,String body){
		try {
			//Crear Conexión
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(path).openConnection();
			
			//Set Method
			httpConnection.setRequestMethod("POST");
			
			//Set Header
			httpConnection.setRequestProperty("Authorization", SERVER_KEY);
			httpConnection.setRequestProperty("Content-Type", JSON_CONTENT_TYPE);
			httpConnection.setDoOutput(true);
			
			//Set body
			OutputStream os = httpConnection.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			os.close();
			
			//Validar Respuest
			if (!(httpConnection.getResponseCode()==HttpURLConnection.HTTP_OK)){
				throw new RuntimeException("Fallo el post");
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
