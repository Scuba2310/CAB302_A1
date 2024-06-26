package jesh.project.jeshproject.model;

import java.sql.SQLException;
import java.util.List;

public class UserManager {
    private IUserDAO userDAO;

    public UserManager(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> searchUsers(String query) {
        return userDAO.getAllUsers()
                .stream()
                .filter(user -> isUserMatched(user, query))
                .toList();
    }

    public boolean isUserMatched(User user, String query) {
        if(query == null || query.isEmpty()) return true;
        query = query.toLowerCase();
        String searchString = user.getUsername()
                + " " + user.getPassword();
        return  searchString.toLowerCase().contains(query);
    }

    public boolean addUser(User user) throws SQLException {
        if (userDAO.getUser(user.getId()) != null) {
            return false; // User already exists
        } else {
            return userDAO.addUser(user); // Return the result of addUser from the DAO
        }
    }

    public void deleteUser() {
        User user = userDAO.getLoggedInUser();
        userDAO.deleteUser(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser((user));
    }

    public void logIn(User user) { userDAO.logIn(user); }
    public void logOut() { userDAO.logOut(); }
    public User getLoggedInUser() {
        return userDAO.getLoggedInUser();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public User getUser(String email, UserIdentifierType userIdentifierType) {
        return userDAO.getUser(email, userIdentifierType);
    }
}