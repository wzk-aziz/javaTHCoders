package org.pi.demo.services;

import org.pi.demo.Exceptions.*;

import org.pi.demo.Interfaces.UserInterface;
import org.pi.demo.entities.User;
import org.pi.demo.utils.MyConnection;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class UserService implements UserInterface {
    Connection connection = MyConnection.getInstance().getConnection();
    ValidationService validationService = new ValidationService();
    private static UserService instance;
    private UserService() {
    }
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    @Override
    public void addUser(User user) throws EmptyFieldException, InvalidPhoneNumberException, InvalidEmailException, IncorrectPasswordException {
        // Vérifier que les champs obligatoires ne sont pas vides
        if (user.getFirstname().isEmpty() || user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()|| user.getProfession().isEmpty()) {
            throw new EmptyFieldException("Please fill in all required fields.");
        }
        // Valider le format de l'email
        if (!validationService.isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Invalid email, please check your email address.");
        }
        // Valider le format du numéro de téléphone (s'il est fourni)
        if (!user.getPhone().isEmpty() && !validationService.isValidPhoneNumber(user.getPhone())) {
            throw new InvalidPhoneNumberException("Invalid phone number format.");
        }
        // Valider le format du mot de passe
        if (!validationService.isValidPassword(user.getPassword())) {
            throw new IncorrectPasswordException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 6 characters long.");
        }
        String request = "INSERT INTO `user`(`firstname`, `lastname`, `email` ,`phone`,`password`, `roles`,`is_banned`,`is_active`) VALUES (?,?,?,?,?,?,false,true)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, cryptPassword(user.getPassword()));
            preparedStatement.setString(6, user.getRoles().toString());
            preparedStatement.executeUpdate();
            System.out.println("User added successfully !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public String cryptPassword(String passwordToCrypt) {
        char[] bcryptChars = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(13, passwordToCrypt.toCharArray());
        return Stream
                .of(bcryptChars)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}
