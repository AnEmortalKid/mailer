package com.anemortalkid.mailman.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Setup works well through google, just gotta update username and password,
 * also go throuh into: https://www.google.com/settings/security/lesssecureapps
 * and Allow that since our app is sketchy AF
 * 
 * @author JMonterrubio
 *
 */
public class EmailTesting {

	private static final String $2 = "butts";
	private static final String $1 = "buttserson";

	public static void main(String[] args) {
		// Recipient's email ID needs to be mentioned.
		String to = "janmonterrubio+testreceive@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "janmonterrubio+testsend@gmail.com";

		// Get system properties
		Properties properties = new Properties();

		// Setup mail server
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication($1, $2);
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
