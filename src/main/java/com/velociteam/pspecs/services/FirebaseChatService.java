package com.velociteam.pspecs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.velociteam.pspecs.dto.MensajeDTO;

@Service
public class FirebaseChatService {
	
	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	@Autowired
	HTTPService httpService;
	
	public void saveMsg(MensajeDTO msg){
		httpService.sendPost(FCM_URL, new Gson().toJson(msg));
	}
}
