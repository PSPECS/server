package com.velociteam.pspecs.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.MensajeDTO;

@Service
public class FirebaseChatService {
	
	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Autowired
	HTTPService httpService;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	public void saveMsg(String userId, MensajeDTO msg){
		Map<String,Object> data = new HashMap<>();
		data.put("notification",msg);
		data.put("to",usuariosDao.getTokenByUser(userId));
		httpService.sendPost(FCM_URL, new Gson().toJson(data));
	}
}
