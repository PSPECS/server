package com.velociteam.pspecs.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream()));
			String inputLine;
	        while ((inputLine = in.readLine()) != null) 
	            System.out.println(inputLine);
	        in.close();
			
			//Validar Respuesta
//			if (!(httpConnection.getResponseCode()==HttpURLConnection.HTTP_OK)){
				throw new RuntimeException(inputLine);
//			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
