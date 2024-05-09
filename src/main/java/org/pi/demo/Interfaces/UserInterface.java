package org.pi.demo.Interfaces;

import org.pi.demo.Exceptions.*;
import org.pi.demo.entities.User;

import java.util.List;

public interface UserInterface {
    void addUser(User user) throws EmptyFieldException, InvalidPhoneNumberException, InvalidEmailException, IncorrectPasswordException;

    void updateUser(User user) throws EmptyFieldException, InvalidPhoneNumberException, InvalidEmailException, IncorrectPasswordException, UserNotFoundException;

    void deleteUser(User user);

    List<User> getUsers();

    User getUserbyID(int id) throws UserNotFoundException;

    User getUserbyEmail(String email) throws UserNotFoundException;
}
