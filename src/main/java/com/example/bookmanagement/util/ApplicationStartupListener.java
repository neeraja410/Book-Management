package com.example.bookmanagement.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true); // Daemon thread
        TimerTask userCountTask = new UserCountTask();
        // Schedule task to run every minute
        long delay = 0; // No delay
        long period = 60 * 1000; // 60 seconds in milliseconds
        timer.scheduleAtFixedRate(userCountTask, delay, period);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
    }
}
