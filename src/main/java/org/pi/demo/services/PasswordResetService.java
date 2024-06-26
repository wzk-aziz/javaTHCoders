package org.pi.demo.services;

import jakarta.mail.MessagingException;
import org.pi.demo.Exceptions.*;

import org.pi.demo.entities.User;
import org.pi.demo.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.prefs.Preferences;

public class PasswordResetService {
    Connection connection = MyConnection.getInstance().getConnection();

    Map<String, String> verificationCodes = new HashMap<>();

    ValidationService validationService = new ValidationService();
    UserService userService = UserService.getInstance();
    private static PasswordResetService instance;

    public static PasswordResetService getInstance() {
        if (instance == null) {
            instance = new PasswordResetService();
        }
        return instance;
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generate a 4-digit code
        return String.valueOf(code);
    }

    // Send SMS with verification code (Replace this with actual SMS sending code)
    public void sendVerificationCode(String email) throws UserNotFoundException {
        String verificationCode = generateVerificationCode();
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put(email, verificationCode);
        String retrievedValue = userPreferences.get("remember", "defaultValue");

        // Store the verification code
        mailing mailer = new mailing();
        User user = UserService.getInstance().getUserbyEmail(email);
        try {
            // Call the sendEmail method of the mailing instance, passing the user as parameter
            mailer.sendEmail(user,verificationCode);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Verify SMS code
    public boolean verifySMSCode(String email, String enteredCode) {
        Preferences userPreferences = Preferences.userRoot();
        String storedCode = userPreferences.get(email, "defaultValue");
        System.out.println("stored code"+storedCode);
        System.out.println(email);
        System.out.println(storedCode);
        if (storedCode != null) {
            return enteredCode.equals(storedCode);

        }
        return false; // If no stored code found for the phone number
    }

    // Check if the new password is different from the old password
    private boolean isNewPasswordDifferent(String email, String newPassword) {
        String request = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String oldPassword = resultSet.getString("password");
                return !newPassword.equals(oldPassword);
            }
        } catch (SQLException e) {
            System.err.println();
        }
        return true; // Assume new password is different by default or handle error case
    }/*

    // Reset password
    public void resetPassword(String email, String newPassword) throws SamePasswordException, IncorrectPasswordException {
        if (!isNewPasswordDifferent(email, newPassword)) {
            throw new SamePasswordException("New password must be different from the old password.");
        }
        if (!validationService.isValidPassword(newPassword)) {
            throw new IncorrectPasswordException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 6 characters long.");
        }
        String request = "UPDATE user SET password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, UserService.cryptPassword(newPassword));
            preparedStatement.setString(2, email);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Password reset successfully for email: " + email);
                // You can send an email notification here if required
            } else {
                System.out.println("Failed to reset password for email: " + email);
            }
        } catch (SQLException e) {
            System.err.println();
        }
    }*/


/*
    // Complete password reset process
    public void resetPasswordProcess(String phoneNumber, String newPassword) throws SamePasswordException, IncorrectPasswordException, UserNotFoundException {
        User user = userService.getUserbyPhoneNumber(phoneNumber);
        resetPassword(user.getEmail(), newPassword);
        sendEmailNotification(user);
        System.out.println("Password reset successful.");
    }*/
}
