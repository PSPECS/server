package com.velociteam.pspecs.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	public void send() {
		final String username = "delallave.martin@gmail.com";
		final String password = "giwblbrgrnvttmhu";

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
			message.setFrom(new InternetAddress("velociteam@noreply.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("delallave.martin@gmail.com"));
			message.setSubject("Reporte PSPECS");
			message.setText("Hola,"
				+ "\n\n El reporte esta adjunto,"
				+ "\n\n Velociteam");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

}
