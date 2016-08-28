package com.velociteam.pspecs.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.velociteam.pspecs.dao.MensajesDao;
import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.dto.FirebaseNotificationDTO;
import com.velociteam.pspecs.dto.MensajeDTO;

@Service
public class FirebaseChatService {
	
	private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Autowired
	HTTPService httpService;
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	MensajesDao mensajesDao;

	public void saveMsg(String userId, MensajeDTO msg){
		Map<String,Object> data = new HashMap<>();
		try {
			String etapaPecs = usuariosDao.getUserInfoById(userId).getEtapaPecs();
			data.put("to", usuariosDao.getTokenByUser(msg.getTo()));
			data.put("message_id", new MessageId(new BigInteger(130, new SecureRandom()).toString(32)));
			data.put("notification", new FirebaseNotificationDTO("", "Nuevo mensaje", "@drawable/logo_hd", "default", "OPEN_CHAT"));
			data.put("data", new Data(userId,etapaPecs));
			httpService.sendPost(FCM_URL, new Gson().toJson(data));
		} catch (Exception e){
			//TODO q hacer
		}
		mensajesDao.saveMsg(userId, msg);
	}
	
	public class Data {
		private String user_id;
		private String user_level;
		
		public Data() {
		}
		
		public Data(String user_id,String user_level) {
			super();
			this.user_id = user_id;
			this.user_level = user_level;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getUser_level() {
			return user_level;
		}

		public void setUser_level(String user_level) {
			this.user_level = user_level;
		}
	}
	
	public class MessageId {
		private String message_id;
		
		public MessageId() {
		}
		
		public MessageId(String message_id) {
			super();
			this.message_id = message_id;
		}

		public String getUser_id() {
			return message_id;
		}

		public void setUser_id(String user_id) {
			this.message_id = user_id;
		}
	}
}

