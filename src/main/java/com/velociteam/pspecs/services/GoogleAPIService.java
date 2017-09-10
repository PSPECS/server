package com.velociteam.pspecs.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class GoogleAPIService {

	private static String CLIENT_ID = "589899251108-j0m6fff8p74175c3lfibivrpgvfmp9vj.apps.googleusercontent.com";
	
	public boolean isValidToken(String idTokenString) throws GeneralSecurityException, IOException{
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
			    .setAudience(Collections.singletonList(CLIENT_ID))
			    // Or, if multiple clients access the backend:
			    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
			    .build();

			// (Receive idTokenString by HTTPS POST)
			GoogleIdToken idToken = verifier.verify(idTokenString);
			return (idToken != null);
	}
	
	
}
