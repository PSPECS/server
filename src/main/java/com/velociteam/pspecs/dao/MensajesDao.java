package com.velociteam.pspecs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.velociteam.pspecs.dto.ImagenMetadataDTO;
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.ResponseMsgDTO;

@Repository
public class MensajesDao extends AbstractDao{
	
	public List<ResponseMsgDTO> search(String userId, RequestMsgDTO requestMsg) throws ParseException {
		
		List<ResponseMsgDTO> mensajes = new ArrayList<>();
		BasicDBList or = new BasicDBList();
		or.add(new BasicDBObject("usuarioDestino",userId).append("usuarioOrigen", requestMsg.getUsuarioAChatear()));
		or.add(new BasicDBObject("usuarioOrigen",userId).append("usuarioDestino", requestMsg.getUsuarioAChatear()));
		or.add(new BasicDBObject("timestamp",new BasicDBObject("$lt", new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse(requestMsg.getAnteriorA()))));
		DBCursor dbMensajes = super.getDB().getCollection("mensajes")
				.find(new BasicDBObject("$or",or));
		
		for (DBObject mensaje : dbMensajes.sort(new BasicDBObject("lastupdated",-1)).limit(Integer.valueOf(requestMsg.getUltimos()))) {
			List<ImagenMetadataDTO> imagenes = new ArrayList<>();
			BasicDBList imgs = (BasicDBList) mensaje.get("imagenes");
			
			for (Iterator<Object> imIt = imgs.iterator(); imIt.hasNext();) {
				Object imagen = (Object) imIt.next();
				imagenes.add(setImagenMetadata(imagen));
			}
			
			ResponseMsgDTO response = new ResponseMsgDTO(
					(String) ((DBObject) mensaje).get("usuarioOrigen"),
					new SimpleDateFormat("dd/MM/yyyy-hh:mm").format(mensaje.get("timestamp")),
					imagenes);
			
			mensajes.add(response);
		}
		
		return mensajes;
	}

	public void saveMsg(String userFrom,MensajeDTO mensajesDTO){
		DBCollection mensajes = getDB().getCollection("mensajes");
		
		mensajes.insert(new BasicDBObject("usuarioOrigen",userFrom)
				.append("timestamp", new Date())
				.append("usuarioDestino", mensajesDTO.getTo())
				.append("imagenes", buildImagenes(mensajesDTO.getImagenes())));
	}
	
	private ImagenMetadataDTO setImagenMetadata(Object imagen) {
		return new ImagenMetadataDTO((String) ((DBObject) imagen).get("resId"), (String) ((DBObject) imagen).get("tipo"));
	}
	

	private List<BasicDBObject> buildImagenes(List<ImagenMetadataDTO> list) {
		return list.stream().filter(imMetadata->imMetadata!=null).map(imMetadata-> new BasicDBObject("resId",imMetadata.getId()).append("tipo", imMetadata.getTipo())).collect(Collectors.toList());
	}

}
