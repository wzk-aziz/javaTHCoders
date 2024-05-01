package org.pi.demo.controllers;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import org.pi.demo.entities.User;
import org.pi.demo.services.SessionService;
import org.pi.demo.services.UserService;
import org.pi.demo.utils.Type;

public class UserListCell extends ListCell<User> {
        private final UserService userService = UserService.getInstance();
        private final SessionService sessionService = SessionService.getInstance();

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);

            if (empty || user == null) {
                setText(null);
                setGraphic(null);
            } else {
                Type userRole = user.getRoles();
                String roleText = (userRole != null && userRole.equals(Type.ROLE_ADMIN)) ? "admin" : "client";
                String lockedText = (user.getIsActive() != null && !user.getIsActive()) ? " (locked)" : "nada";
                String userText = user.getFirstname() + " " + user.getName() + " " + user.getPhone() + " (" + user.getEmail() + ")\n" +
                        "Role: " + roleText +" "+ lockedText;

                setText(userText);

                if (user.getIsActive() != null && !user.getIsActive()) {
                    setTextFill(Color.RED);
                } else if (user.getIsBanned() != null && user.getIsBanned()) {
                    setTextFill(Color.BLUE);
                } else {
                    setTextFill(Color.BLACK);
                    setGraphic(null);
                }

            }
        }


        public static ListCell<User> create() {
            return new UserListCell();
        }
    }
