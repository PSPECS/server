package com.velociteam.pspecs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.ImagenDTO;
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.ResponseMsgDTO;

@Repository
public class MensajesDao extends AbstractDao{
	
	public List<ResponseMsgDTO> search(String userId, RequestMsgDTO requestMsg) {
		
		List<ResponseMsgDTO> mensajes = new ArrayList<>();
		//TODO Agregar filtro por fecha y devolver ordenados por fecha para que el limit ande.
		DBCursor dbMensajes = super.getDB().getCollection("mensajes")
				.find(new BasicDBObject("usuarioDestino",new ObjectId(userId)));
		
		for (DBObject mensaje : dbMensajes) {
			List<ImagenDTO> imagenes = new ArrayList<>();
			BasicDBList imgs = (BasicDBList) mensaje.get("imagenes");
			
			for (Iterator<Object> imIt = imgs.iterator(); imIt.hasNext();) {
				Object imagen = (Object) imIt.next();
				imagenes.add(new ImagenDTO((String) ((DBObject) imagen).get("resId")));
			}
			
			ResponseMsgDTO response = new ResponseMsgDTO(
					(String) ((DBObject) mensaje).get("usuarioOrigen"),
					(String) ((DBObject) mensaje).get("timestamp"),
					imagenes);
			
			mensajes.add(response);
		}
		
		return mensajes.stream().limit(Long.valueOf(requestMsg.getUltimos())).collect(Collectors.toList());
	}
	
	public void saveMsg(String userFrom,MensajeDTO mensajesDTO){
		DBCollection mensajes = getDB().getCollection("mensajes");
		
		mensajes.insert(new BasicDBObject("usuarioOrigen",userFrom)
				.append("timestamp", new Date())
				.append("usuarioDestino", mensajesDTO.getTo())
				.append("imagenes", buildImagenes(mensajesDTO.getImagenes())));
	}

	private List<BasicDBObject> buildImagenes(List<String> imagenes) {
		
		return imagenes.stream().map(imagen-> new BasicDBObject("resId",imagen)).collect(Collectors.toList());
	}

}