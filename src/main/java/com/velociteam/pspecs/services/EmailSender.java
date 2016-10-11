package com.velociteam.pspecs.services;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velociteam.pspecs.dao.UsuariosDao;
import com.velociteam.pspecs.exception.BussinessException;

@Service
public class EmailSender {
	
	@Autowired
	private UsuariosDao usDao;

	public void send(String userId, String filename) {
		final String username = "delallave.martin@gmail.com";
		final String password = "giwblbrgrnvttmhu";
		
		final String to = usDao.getUserInfoById(userId).getEmail();
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();
			
			message.setFrom(new InternetAddress("velociteam@noreply.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject("Reporte PSPECS");
			String msg ="Hola,"
					+ "\n\n El reporte esta adjunto,"
					+ "\n\n Velociteam";
			message.setText(msg);
			
			// creates body part for the message
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(msg, "text/html");
			 
			// creates body part for the attachment
			MimeBodyPart attachPart = new MimeBodyPart();
			 
			try {
				attachPart.attachFile(filename);
			} catch (IOException e) {
				throw new BussinessException("No se encontro el reporte generado para enviar el e-mail",e);
			}
			 
			// adds parts to the multipart
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachPart);
			 
			// sets the multipart as message's content
			message.setContent(multipart);
			
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

}
