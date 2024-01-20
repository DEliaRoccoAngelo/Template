package it.template.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SenderMailServer {
	
final static Logger logger = Logger.getLogger(SenderMailServer.class);
	
	private static final String SMTP_HOST_NAME = "smtps.aruba.it";
	 public static final String SMTP_AUTH_USER = "no_reply@igfgroup.it";
	 private static final String SMTP_AUTH_PWD  = "n0r3ply_01!";
	 private static final String PORT="465";

	 
	 public static void sendMail(String cc, String to,String subject, String text) throws AddressException, MessagingException{
		 	Properties prop = System.getProperties();
	        prop.put("mail.smtp.host", SMTP_HOST_NAME); //optional, defined in SMTPTransport
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.port", "465"); // default port 25

	        Session session = Session.getInstance(prop, null);
	        Message msg = new MimeMessage(session);

	        // from
	        msg.setFrom(new InternetAddress(SMTP_AUTH_USER));

	        // to 
	        msg.setRecipients(Message.RecipientType.TO,
	        		InternetAddress.parse(to, false));

	        // cc
	        msg.setRecipients(Message.RecipientType.CC,
	        		InternetAddress.parse(cc, false));

	        // subject
	        msg.setSubject(subject);

	        // content 
	        msg.setText(text);


	        // Get SMTPTransport
	        SMTPSSLTransport t = (SMTPSSLTransport) session.getTransport("smtps");

	        // connect
	        t.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);

	        // send
	        t.sendMessage(msg, msg.getAllRecipients());

	        logger.info("EMAILINFO Response : " + t.getLastServerResponse()+" invio a:" +to);
	        t.close();


	    }
	 
	 
	 public static boolean sendStandardMail(String from, String cc, String to,String subject, String text){
			boolean ok = true;
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			//props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", PORT);
			//props.put("mail.smtp.starttls.enable", "true");
			Authenticator auth = new SMTPAuthenticator();
			Session session=Session.getDefaultInstance(props,auth);
			try {
				Transport transport = session.getTransport("smtp");
				transport.connect();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				List<InternetAddress> lista = new ArrayList<InternetAddress>();
				if(to!= null){
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(to));
					lista.add(new InternetAddress(to));
				}
				if(cc != null){
					message.setRecipients(Message.RecipientType.CC,
							InternetAddress.parse(cc));
					lista.add(new InternetAddress(cc));
				}
				String testo ="";
				
				message.setSubject(testo + subject);
				message.setText(text);

				if(!lista.isEmpty()){
					Address[] addresses = new Address[lista.size()];
					Iterator itLista = lista.iterator();
					int i = 0;
					while(itLista.hasNext()){
						InternetAddress indirizzo = (InternetAddress) itLista.next();
						addresses[i] = indirizzo;
						i++;
					}
					//transport.send(message);
					transport.sendMessage(message,addresses);
					//Transport.send(message);
					ok = true;
				}
			} catch (MessagingException e) {
				ok = false;
				System.out.println("Errore Comunicazione");
				throw new RuntimeException(e);
			}
			return ok;
		}


		public static boolean sendStandardMailAttachement( String from, String cc, String to,String subject, String text, String absFileName){
			boolean ok = true;
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			Authenticator auth = new SMTPAuthenticator();
			Session session=Session.getDefaultInstance(props,auth);
			try {
				Transport transport = session.getTransport("smtp");
				transport.connect();
				boolean noInvio = true;
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
		         List lista = new ArrayList();
		         if(StringUtils.isNotEmpty(to)){
		        	 message.setRecipients(Message.RecipientType.TO,
		        			 InternetAddress.parse(to));
		        	 lista.add(new InternetAddress(to));
		         }
		         if(StringUtils.isNotEmpty(cc)){
		        	 message.setRecipients(Message.RecipientType.CC,
		        			 InternetAddress.parse(cc));
		        	 lista.add(new InternetAddress(cc));
		         }
		         // Set Subject: header field
		         String testo ="";
					
		         message.setSubject(testo + subject);

		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Fill the message
		         messageBodyPart.setText(text);

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         if(absFileName != null && !"".equals(absFileName))
		        	 {
		        	 // Part two is attachment
		        	 MimeBodyPart fileAttachment = new MimeBodyPart();
		        	 FileDataSource atcFile = new FileDataSource(absFileName);
		        	 fileAttachment.setDataHandler(new DataHandler(atcFile));
		        	 fileAttachment.setFileName(atcFile.getName());
		        	 multipart.addBodyPart(fileAttachment);
		         }

		         // Send the complete message parts
		         message.setContent(multipart );
		         if(!lista.isEmpty()){
		        	 Address[] addresses = new Address[lista.size()];
		        	 Iterator itLista = lista.iterator();
		        	 int i = 0;
		        	 while(itLista.hasNext()){
		        		 InternetAddress indirizzo = (InternetAddress) itLista.next();
		        		 addresses[i] = indirizzo;
		        		 i++;
		        	 }
		        	 //transport.send(message);
		        	 transport.sendMessage(message,addresses);
		        	 //Transport.send(message);
		         }
			}catch (MessagingException mex) {
				ok = false;
				throw new RuntimeException(mex);
			}
			return ok;
		}

		public static boolean sendStandardMailAttachements(String from, String cc, String to,String subject, String text, List<String> filesName){
			boolean ok = true;
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			Authenticator auth = new SMTPAuthenticator();
			Session session=Session.getDefaultInstance(props,auth);
			try {
				Transport transport = session.getTransport("smtp");
				transport.connect();
				boolean noInvio = true;
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
		         List lista = new ArrayList();
		         if(StringUtils.isNotEmpty(to)){
		        	 message.setRecipients(Message.RecipientType.TO,
		        			 InternetAddress.parse(to));
		        	 lista.add(new InternetAddress(to));
		         }
		         if(StringUtils.isNotEmpty(cc)){
		        	 message.setRecipients(Message.RecipientType.CC,
		        			 InternetAddress.parse(cc));
		        	 lista.add(new InternetAddress(cc));
		         }
		         // Set Subject: header field
		         String testo ="";
		         
		         message.setSubject(testo + subject);

		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Fill the message
		         messageBodyPart.setText(text);

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         if(filesName != null && !filesName.isEmpty()){
		        	 for(String absFileName : filesName){
		        		 // Part two is attachment
		        		 MimeBodyPart fileAttachment = new MimeBodyPart();
		        		 FileDataSource atcFile = new FileDataSource(absFileName);
		        		 fileAttachment.setDataHandler(new DataHandler(atcFile));
		        		 fileAttachment.setFileName(atcFile.getName());
		        		 multipart.addBodyPart(fileAttachment);
		        	 }
		         }

		         // Send the complete message parts
		         message.setContent(multipart );
		         if(!lista.isEmpty()){
		        	 Address[] addresses = new Address[lista.size()];
		        	 Iterator itLista = lista.iterator();
		        	 int i = 0;
		        	 while(itLista.hasNext()){
		        		 InternetAddress indirizzo = (InternetAddress) itLista.next();
		        		 addresses[i] = indirizzo;
		        		 i++;
		        	 }
		        	 //transport.send(message);
		        	 //Transport.send(message);
		        	 transport.sendMessage(message,addresses);
		        	 //Transport.send(message);
		         }
			}catch (MessagingException mex) {
				ok = false;
				throw new RuntimeException(mex);
			}
			return ok;
		}

		public static boolean sendStandardMailAttachment(String from, String cc, String to,String subject, String text,
				byte[] attachment, String attachmentName) throws Exception{
			boolean ok = true;
			Properties prop = System.getProperties();
	        prop.put("mail.smtp.host", SMTP_HOST_NAME); //optional, defined in SMTPTransport
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.port", "465"); // default port 25
			
	        Session session = Session.getInstance(prop, null);
			try {
				SMTPSSLTransport transport = (SMTPSSLTransport) session.getTransport("smtps");
				transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		         MimeMessage message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
		         
		        
		         // Set To: header field of the header.
		         if(StringUtils.isNotEmpty(to)){
		        	
		        	 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		        	
		         }
		         if(StringUtils.isNotEmpty(cc)){
		        	 message.setRecipients(Message.RecipientType.CC,
		        			 InternetAddress.parse(cc));
		         }
		         // Set Subject: header field
		         String testo ="";
		         message.setSubject(testo + subject);

		         // Create the message part
		         MimeBodyPart messageBodyPart = new MimeBodyPart();

		         // Fill the message
		         messageBodyPart.setText(text,"UTF-8","html");

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);


		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		         DataSource source = new ByteArrayDataSource(attachment, "application/pdf");
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName(attachmentName);
		         multipart.addBodyPart(messageBodyPart);

		         // Send the complete message parts
		         message.setContent(multipart );

		         // Send message
		         transport.sendMessage(message, message.getAllRecipients());
		         System.out.println("Sent message successfully....");
		         ok = true;
			}catch (MessagingException mex) {
				ok = false;
				mex.printStackTrace();
				throw new Exception();
			}
			return ok;
		}
		
		private static class SMTPAuthenticator extends javax.mail.Authenticator {
	        public PasswordAuthentication getPasswordAuthentication() {
	           String username = SMTP_AUTH_USER;
	           String password = SMTP_AUTH_PWD;
	           return new PasswordAuthentication(username, password);
	        }
	    }
}
