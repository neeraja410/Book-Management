package com.example.bookmanagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUtil {
private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
private static final String JDBC_URL="jdbc:mysql://localhost:3306/testdb";
private static final String JDBC_USER="root";
private static final String JDBC_PASSWORD="neeraJAgadesh1!";
static {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255), isbn VARCHAR(255))";
        connection.createStatement().execute(createBooksTable);
        logger.info("Books table created successfully.");
        
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (user_id BIGINT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(255), user_phonenumber VARCHAR(255),user_email VARCHAR(255))";
        connection.createStatement().execute(createUsersTable);
        logger.info("Users table created successfully.");
        
        String authors= "CREATE TABLE IF NOT EXISTS authors (author_id BIGINT AUTO_INCREMENT PRIMARY KEY, author_name VARCHAR(255), author_phonenumber VARCHAR(255))";
        connection.createStatement().execute(authors);
        logger.info("authors table created successfully.");
       
        String usersReadBook= "CREATE TABLE IF NOT EXISTS usersReadBook (usersReadBook_id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id VARCHAR(255),user_name VARCHAR(255),book_id VARCHAR(255), book_title VARCHAR(255))";
        connection.createStatement().execute(usersReadBook);
        logger.info("authors table created successfully.");
        
        
    } catch (SQLException e) {
        logger.error("Error creating tables", e);
    }
}
public static Connection getConnection() throws SQLException{
	logger.info("Getting database connection");
	return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	
}

}
