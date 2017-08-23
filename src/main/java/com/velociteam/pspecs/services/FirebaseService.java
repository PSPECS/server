package com.velociteam.pspecs.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.velociteam.pspecs.exception.AuthenticationException;

@Service
public class FirebaseService {
	
	private static final String FB_DATABASE_NAME = "https://pspecs-e7eeb.firebaseio.com/";
	private static final String SERVICE_ACCOUNT_PATH = "pspecs-471739017f1f.json";
	
	private FirebaseApp fbApp;
	
	private void buildFBApp() throws FileNotFoundException{
		if(fbApp!=null) return;
		
		FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_PATH);

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setServiceAccount(serviceAccount)
//		  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
		  .setDatabaseUrl(FB_DATABASE_NAME)
		  .build();

		this.fbApp = FirebaseApp.initializeApp(options);
	}
	
	public String buildFbToken(String userId){
		try {
			buildFBApp();
		} catch (FileNotFoundException e) {
			throw new AuthenticationException("There is some error with fb service account file.",e);
		}
		return FirebaseAuth.getInstance().createCustomToken(userId);
	}
	
}
