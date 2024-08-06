package com.example.bookmanagement.util;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

public class SendEmailUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SendEmailUtil.class);
	final static String username = "mudireddy95@gmail.com";
    final static String password = "neeraJAgadesh1!";
	
    public boolean sendEmail(){
		logger.info("got the request for send mail");
        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "587");
      prop.put("mail.smtp.port", "465");

      prop.put("mail.smtp.socketFactory.port", "465");
      prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
 
		prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); 
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");//TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mudireddy95@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("rameshdoing@gmail.com ,jagadesh9138@gmail.com")
            );
            message.setSubject("New user alert");
            message.setText("Dear jagadesh ,new user has been created,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);
           

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    

		
		return true;
		
	}

}
