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

    public void sendEmail(User to) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@greenta.tn"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to.getEmail())
        );
        message.setSubject("Password reset request:" + to.getFirstname());

        String msg = "Dear " + to.getFirstname() + " " + to.getName() + ", your password has been updated successfully." +
                " If you didn't request this change, please contact us immediately. Thank you.";

        message.setText(msg);

        Transport.send(message);
    }
}
