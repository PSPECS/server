package com.velociteam.pspecs.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
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
	
	public void saveMsg(String userId, MensajeDTO msg){
		Map<String,Object> data = new HashMap<>();
		data.put("to", usuariosDao.getTokenByUser(userId));
		data.put("message_id", new MessageId(new BigInteger(130, new SecureRandom()).toString(32)));
		data.put("notification", new FirebaseNotificationDTO("msg_body", "title", "@drawable/logo_hd", "default", "OPEN_CHAT"));
		data.put("data", new UserId(userId));
		httpService.sendPost(FCM_URL, new Gson().toJson(data));
	}
	
	public class UserId {
		private String user_id;
		
		public UserId() {
		}
		
		public UserId(String user_id) {
			super();
			this.user_id = user_id;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
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

