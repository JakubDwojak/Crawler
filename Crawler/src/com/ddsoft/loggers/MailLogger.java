package com.ddsoft.loggers;


import com.ddsoft.interfaces.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.PasswordAuthentication;

public class MailLogger implements Logger {

    @Override
    public void log(String status, String student) {

        Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("crawlertesten@gmail.com","asdfghjkl123");
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("crawlertesten@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("crawlertesten@gmail.com"));
        if(!status.equals("NOTHING CHANGED")) {
            message.setSubject("Crawler notification (" + status + " person)");
            if (status.equals("ADDED"))
                message.setText("The FILE or PAGE has been changed!\nTHERE IS A NEW PERSON ! \nNew person is:\n" + student);
            else if (status.equals("REMOVED"))
                message.setText("The FILE or PAGe has been changed!\nMISSING ONE PERSON :(\nOld person was:\n" + student);
        }
        else {
            message.setSubject("Crawler notification (" + status + ")");
            message.setText("The FILE or PAGE hasn't been changed :)");
        }

        Transport.send(message);

    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}

    }
