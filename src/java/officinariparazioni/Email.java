package officinariparazioni;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.*;
/**
 * Classe di utilità,ci permette accedere al servizio di invio mail in java.
 */
public class Email {
        /**
         * 
         * @param destinatario destinatario del nostro messaggio
         * @param oggetto oggetto della mail
         * @param testo messaggio da inviare
         * 
         */
	public static void sendEmail(String destinatario,String oggetto,String testo) {

		final String username = "testappdeveloper@yahoo.com";
		final String password = "password94";

		Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.host","smtp.mail.yahoo.com");
            props.put("mail.smtp.port","465");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("testappdeveloper@yahoo.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(destinatario));
			message.setSubject(oggetto);
			message.setText(testo);

			Transport.send(message);
			JOptionPane.showMessageDialog(null,"Email inviata al cliente");


		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null,"ERRORE : invio email non riuscito");
		}

	}
}