package org.pi.demo.services;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.pi.demo.entities.User;

import java.util.Properties;
public class mailing {
    Properties prop = new Properties();
    Session session;
    public mailing() {
        // SMTP Properties
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        String email =    "8b9b3e01fca3c9";
        String password = "cccbe58ecdb1b7";
        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public void sendEmail(User to,String code ) throws MessagingException {
        // Create a MimeMessage
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@5oudhwhet.tn"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.getEmail()));
        message.setSubject("Password reset request: " + to.getFirstname());

        // Customize the email message
        String msg = "<html>"
                + "<body>"
                + "<p>Dear " + to.getFirstname() + " " + to.getName() + ",</p>"
                + "<p>You forgot your password.</p>"
                + "<p>Here is the code to retrieve it:</p>"
                + "<p style='font-size: 18px; font-weight: bold;'>" + code + "</p>"
                + "</body>"
                + "</html>";

        // Set the message content as HTML
        message.setContent(msg, "text/html");

        // Send the email
        Transport.send(message);
    }
}



