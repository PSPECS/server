package com.velociteam.pspecs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired
	public MensajesDao(@Qualifier("realDB")MongodbDBCreator aCreator) {
		super(aCreator);
	}

	public List<ResponseMsgDTO> search(String userId, RequestMsgDTO requestMsg) throws ParseException {
		
		List<ResponseMsgDTO> mensajes = new ArrayList<>();
		BasicDBList or = new BasicDBList();
		or.add(new BasicDBObject("usuarioDestino",userId).append("usuarioOrigen", requestMsg.getUsuarioAChatear()));
		or.add(new BasicDBObject("usuarioOrigen",userId).append("usuarioDestino", requestMsg.getUsuarioAChatear()));
		
		DBCursor dbMensajes = collection("mensajes")
				.find(new BasicDBObject("$or",or));
		
		for (DBObject mensaje : dbMensajes.sort(new BasicDBObject("timestamp",1))) {
			Long timestamp = (Long) mensaje.get("timestamp");
			if (new SimpleDateFormat("dd/MM/yyyy-hh:mm").parse(requestMsg.getAnteriorA()).getTime()>timestamp){
				List<ImagenMetadataDTO> imagenes = new ArrayList<>();
				BasicDBList imgs = (BasicDBList) mensaje.get("imagenes");
				
				for (Iterator<Object> imIt = imgs.iterator(); imIt.hasNext();) {
					Object imagen = (Object) imIt.next();
					imagenes.add(setImagenMetadata(imagen));
				}
				
				ResponseMsgDTO response = new ResponseMsgDTO(
						(String) ((DBObject) mensaje).get("usuarioOrigen"),
						new SimpleDateFormat("dd/MM/yyyy-HH:mm").format(new Date(timestamp)),
						imagenes,(String) ((DBObject) mensaje).get("descripcion"));
				
				mensajes.add(response);
			}
		}
		
		return mensajes.stream().limit(Long.valueOf(requestMsg.getUltimos())).collect(Collectors.toList());
	}

	public void saveMsg(String userFrom,MensajeDTO mensajesDTO){
		DBCollection mensajes = collection("mensajes");
		
		mensajes.insert(new BasicDBObject("usuarioOrigen",userFrom)
				.append("timestamp", new Date().getTime())
				.append("usuarioDestino", mensajesDTO.getTo())
				.append("imagenes", buildImagenes(mensajesDTO.getImagenes()))
				.append("descripcion", mensajesDTO.getDescripcion()));
	}
	
	private ImagenMetadataDTO setImagenMetadata(Object imagen) {
		return new ImagenMetadataDTO((String) ((DBObject) imagen).get("resId"), (String) ((DBObject) imagen).get("tipo"));
	}
	

	private List<BasicDBObject> buildImagenes(List<ImagenMetadataDTO> list) {
		return list.stream().filter(imMetadata->imMetadata!=null).map(imMetadata-> new BasicDBObject("resId",imMetadata.getId()).append("tipo", imMetadata.getTipo())).collect(Collectors.toList());
	}

}
