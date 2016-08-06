package com.velociteam.pspecs.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velociteam.pspecs.dto.MensajeDTO;

@Service
public class FirebaseChatService {
	
	private void initialiaze() throws FileNotFoundException{
		FirebaseOptions options = new FirebaseOptions.Builder()
			    .setDatabaseUrl("https://pspecs-2d21b.firebaseio.com")
			    .setServiceAccount(new FileInputStream("pspecs-97e1d9e5648f.json"))
			    .build();
			FirebaseApp.initializeApp(options);
	}
	
	private DatabaseReference getDB(){
		return FirebaseDatabase
			    .getInstance()
			    .getReference("mensajes");
	}
	
	public void saveMsg(String userId, MensajeDTO msg){
		try {
			this.initialiaze();
		} catch (FileNotFoundException e) {
			new RuntimeException("Archivo firebase no encontrado",e.getCause()); 
		}
		
		DatabaseReference dbRef = getDB().child("mensajes").child(userId);
		
		dbRef.setValue(msg);
		
	}
}
