package com.velociteam.pspecs.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.velociteam.pspecs.dto.MensajeDTO;

@Service
public class FirebaseChatService {
	
	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	private static final String REGISTRATION_TOKEN = "";
	
	@Autowired
	HTTPService httpService;
	
	public void saveMsg(MensajeDTO msg){
		Map<String,Object> data = new HashMap<>();
		data.put("data",msg);
		data.put("to",REGISTRATION_TOKEN);
		httpService.sendPost(FCM_URL, new Gson().toJson(data));
	}
}
