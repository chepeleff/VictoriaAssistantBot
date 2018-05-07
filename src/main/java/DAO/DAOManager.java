package DAO;

import userInteractions.RobotUser;

public interface DAOManager {
    void saveUser(RobotUser robotUser);
    void saveQuestion(RobotUser robotUser, String question);
    RobotUser getUserById(int telegramId);
    boolean userExists(int telegramId);
}
