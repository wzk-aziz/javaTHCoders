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
        String css = "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f2f2f2;"
                + "}"
                + "p {"
                + "    margin: 10px 0;"
                + "}"
                + ".container {"
                + "    max-width: 600px;"
                + "    margin: 0 auto;"
                + "    background-color: #ffffff;"
                + "    padding: 20px;"
                + "    border-radius: 5px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "}"
                + ".header {"
                + "    background-color: #4CAF50;"
                + "    color: #ffffff;"
                + "    padding: 10px;"
                + "    text-align: center;"
                + "    border-top-left-radius: 5px;"
                + "    border-top-right-radius: 5px;"
                + "}"
                + ".code {"
                + "    font-size: 18px;"
                + "    font-weight: bold;"
                + "    color: #4CAF50;"
                + "}"
                + "table {"
                + "    width: 100%;"
                + "    border-collapse: collapse;"
                + "}"
                + "th, td {"
                + "    padding: 8px;"
                + "    text-align: left;"
                + "    border-bottom: 1px solid #ddd;"
                + "}"
                + "</style>";

        String msg = "<html>"
                + css
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>"
                + "<h2>Password Reset</h2>"
                + "</div>"
                + "<p>Dear " + to.getFirstname() + " " + to.getName() + ",</p>"
                + "<p>You requested a password reset for your account.</p>"
                + "<p>Here is the code to retrieve your new password:</p>"
                + "<p class='code'>" + code + "</p>"
                + "<p>Please find your account details below:</p>"
                + "<table>"
                + "<tr><th>Email</th><td>" + to.getEmail() + "</td></tr>"
                + "<tr><th>Phone</th><td>" + to.getPhone() + "</td></tr>"
                + "<tr><th>Profession</th><td>" + to.getProfession() + "</td></tr>"
                + "</table>"
                + "</div>"
                + "</body>"
                + "</html>";
        // Set the message content as HTML
        message.setContent(msg, "text/html");

        // Send the email
        Transport.send(message);
    }
}



