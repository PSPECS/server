package com.velociteam.pspecs.dao;

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
import com.velociteam.pspecs.dto.MensajeDTO;
import com.velociteam.pspecs.dto.MensajeDTO.ImagenMetadataDTO;
import com.velociteam.pspecs.dto.RequestMsgDTO;
import com.velociteam.pspecs.dto.ResponseMsgDTO;

@Repository
public class MensajesDao extends AbstractDao{
	
	public List<ResponseMsgDTO> search(String userId, RequestMsgDTO requestMsg) {
		
		List<ResponseMsgDTO> mensajes = new ArrayList<>();
		//TODO Agregar filtro por fecha y devolver ordenados por fecha para que el limit ande.
		BasicDBList or = new BasicDBList();
		or.add(new BasicDBObject("usuarioDestino",userId).append("usuarioOrigen", requestMsg.getUsuarioAChatear()));
		or.add(new BasicDBObject("usuarioOrigen",userId).append("usuarioDestino", requestMsg.getUsuarioAChatear()));
		DBCursor dbMensajes = super.getDB().getCollection("mensajes")
				.find(new BasicDBObject("$or",or));
		
		for (DBObject mensaje : dbMensajes) {
			List<MensajeDTO.ImagenMetadataDTO> imagenes = new ArrayList<>();
			BasicDBList imgs = (BasicDBList) mensaje.get("imagenes");
			
			for (Iterator<Object> imIt = imgs.iterator(); imIt.hasNext();) {
				Object imagen = (Object) imIt.next();
				imagenes.add(setImagenMetadata(imagen));
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
				.append("timestamp", new SimpleDateFormat("dd/MM/yyyy-hh:mm").format(new Date()))
				.append("usuarioDestino", mensajesDTO.getTo())
				.append("imagenes", buildImagenes(mensajesDTO.getImagenes())));
	}
	
	private ImagenMetadataDTO setImagenMetadata(Object imagen) {
		return new MensajeDTO().new ImagenMetadataDTO((String) ((DBObject) imagen).get("resId"), (String) ((DBObject) imagen).get("tipo"));
	}
	

	private List<BasicDBObject> buildImagenes(List<ImagenMetadataDTO> list) {
		return list.stream().filter(imMetadata->imMetadata!=null).map(imMetadata-> new BasicDBObject("resId",imMetadata.getId()).append("tipo", imMetadata.getTipo())).collect(Collectors.toList());
	}

}
