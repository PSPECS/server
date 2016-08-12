package com.velociteam.pspecs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.ImagenDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.ResponseMsgDTO;

public class MensajesDao extends AbstractDao{
	
	public List<ResponseMsgDTO> search(String userId, RequestMsgDTO requestMsg) {
		
		List<ResponseMsgDTO> mensajes = new ArrayList<>();
		//TODO Agregar filtro por fecha y devolver ordenados por fecha para que el limit ande.
		DBCursor dbMensajes = super.getDB().getCollection("mensajes")
				.find(new BasicDBObject("userTo",new ObjectId(userId)));
		
		for (DBObject mensaje : dbMensajes) {
			List<ImagenDTO> imagenes = new ArrayList<>();
			BasicDBList imgs = (BasicDBList) mensaje.get("imagenes");
			
			for (Iterator<Object> imIt = imgs.iterator(); imIt.hasNext();) {
				Object imagen = (Object) imIt.next();
				imagenes.add(new ImagenDTO((String) ((DBObject) imagen).get("")));
			}
			
			ResponseMsgDTO response = new ResponseMsgDTO(
					(String) ((DBObject) mensaje).get("userFrom"),
					(String) ((DBObject) mensaje).get("timestamp"),
					imagenes);
			
			mensajes.add(response);
		}
		
		return mensajes.stream().limit(Long.valueOf(requestMsg.getUltimos())).collect(Collectors.toList());
	}

}
