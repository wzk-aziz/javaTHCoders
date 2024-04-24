package org.pi.demo.Interfaces;

import org.pi.demo.Exceptions.EmptyFieldException;
import org.pi.demo.Exceptions.IncorrectPasswordException;
import org.pi.demo.Exceptions.InvalidEmailException;
import org.pi.demo.Exceptions.InvalidPhoneNumberException;
import org.pi.demo.entities.User;

public interface UserInterface {
    void addUser(User user) throws EmptyFieldException, InvalidPhoneNumberException, InvalidEmailException, IncorrectPasswordException;
}
