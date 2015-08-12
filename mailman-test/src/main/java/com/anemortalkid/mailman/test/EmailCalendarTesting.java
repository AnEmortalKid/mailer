package com.anemortalkid.mailman.test;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
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
import javax.mail.util.ByteArrayDataSource;

/**
 * Setup works well through google, just gotta update username and password,
 * also go throuh into: https://www.google.com/settings/security/lesssecureapps
 * and Allow that since our app is sketchy AF
 * 
 * @author JMonterrubio
 *
 */
public class EmailCalendarTesting {

	private static final String $2 = "password";
	private static final String $1 = "username";
	private static String to = "janmonterrubio+testsend@gmail.com";
	private static String from = "janmonterrubio+testsend@gmail.com";

	public static void main(String[] args) throws IOException {

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
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=VEVENT");

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("Test Outlook message");

			// Now set the actual message
			message.setText("This is actual message");

			StringBuffer sb = new StringBuffer();

			StringBuffer buffer = sb
					.append("BEGIN:VCALENDAR\n"
							+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
							+ "VERSION:2.0\n"
							+ "METHOD:REQUEST\n"
							+ "BEGIN:VEVENT\n"
							+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n"
							+ "ORGANIZER:MAILTO:xx@xx.com\n"
							+ "DTSTART:20051208T053000Z\n"
							+ "DTEND:20051208T060000Z\n"
							+ "LOCATION:Conference room\n"
							+ "TRANSP:OPAQUE\n"
							+ "SEQUENCE:0\n"
							+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
							+ " 000004377FE5C37984842BF9440448399EB02\n"
							+ "DTSTAMP:20051206T120102Z\n"
							+ "CATEGORIES:Meeting\n"
							+ "DESCRIPTION:This the description of the meeting.\n\n"
							+ "SUMMARY:Test meeting request\n" + "PRIORITY:5\n"
							+ "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
							+ "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n"
							+ "DESCRIPTION:Reminder\n" + "END:VALARM\n"
							+ "END:VEVENT\n" + "END:VCALENDAR");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setHeader("Content-Class",
					"urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(
							buffer.toString(), "text/calendar")));// very
																	// important

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
