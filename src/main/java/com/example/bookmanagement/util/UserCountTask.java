package com.example.bookmanagement.util;

import com.example.bookmanagement.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class UserCountTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(UserCountTask.class);
    private UserDAO userDAO = new UserDAO();

    @Override
    public void run() {
        logger.info("Running user count task...");
        int userCount = getUserCount();
        logger.info("Number of users: {}", userCount);
    }

    private int getUserCount() {
        try {
            return userDAO.countUsers();
        } catch (Exception e) {
            logger.error("Error counting users", e);
            return 0;
        }
    }
}
