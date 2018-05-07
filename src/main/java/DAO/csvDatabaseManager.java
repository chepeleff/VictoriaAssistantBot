package DAO;

import userInteractions.RobotUser;

import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class csvDatabaseManager implements DAOManager {
    // TODO: to config file
    private String csvDirectory;
    private Connection connection;

    public csvDatabaseManager() {
        initialize();
    }

    private void initialize() {
        try {
            csvDirectory = getCsvDirectory();
            connection = DriverManager.getConnection("jdbc:relique:csv:" + csvDirectory);
        } catch (SQLException e) {
            // TODO: log
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(RobotUser robotUser) {
        Statement statement = connection.createStatement();
        //telegramUserId,userName,userRole
        String query =  "INSERT INTO users " +
                        "SET ";


    }

    @Override
    public void saveQuestion(RobotUser robotUser, String question) {

    }

    @Override
    public RobotUser getUserById(int telegramId) {
        Statement statement = connection.createStatement();
        String query = "SELECT "
        return null;
    }

    @Override
    public boolean userExists(int telegramId) {
        return false;
    }

    private String getCsvDirectory() {
        Properties properties = new Properties();

        try {
            InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream("csvDbConfig.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String csvStoragePath = properties.getProperty("csvStoragePath");

        return csvStoragePath;
    }
}
