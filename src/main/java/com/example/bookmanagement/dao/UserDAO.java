package com.example.bookmanagement.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.bookmanagement.controller.AuthorServlet;
import com.example.bookmanagement.model.Author;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.util.DatabaseUtil;

import jdk.internal.org.jline.utils.Log;

public class UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private static Connection getConnection() throws SQLException {
		return DatabaseUtil.getConnection();
	}
	public List<User> findAll(){
	     
	     
		List<User> users=new ArrayList<>();
		String sql="SELECT * FROM users";
		try(Connection connnection=getConnection();
			Statement statement=connnection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);){
			while(resultset.next()) {
				User user=new User();
				user.setUserId(resultset.getLong("user_id"));
				user.setUserName(resultset.getString("user_name"));
				user.setUserPhonenumber(resultset.getString("user_phonenumber"));
				user.setUserEmail(resultset.getString("user_email"));
				users.add(user);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();;
		}
		return users;
		
	}
	public User findByID(long user_id) {
		User user =null;
		String sql="SELECT * FROM users WHERE user_id = ?";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql);){
			preparedstatement.setLong(1, user_id);
			
			logger.info("printing the prepared statement : " + preparedstatement.toString());
			
			try(ResultSet resultset=preparedstatement.executeQuery()){
  //   			logger.info("Result Size from the SELECT * FROM users WHERE user_id = " + resultset.get);
			
     			if(resultset.next()) {
					user=new User();
					logger.info("Printing the values of the results = " + resultset.getString("user_phonenumber"));
					
					user.setUserName(resultset.getString("user_name"));
					user.setUserPhonenumber(resultset.getString("user_phonenumber"));
					user.setUserEmail(resultset.getString("user_email"));
					
				}
			}
			
		}catch(SQLException e) {
			 e.printStackTrace();;	
		}
		return user;
	}
		public boolean save(User user) {
			String sql="INSERT INTO users (user_id, user_name, user_phonenumber,user_email) VALUES (?, ?, ?,?)";
			try(Connection connection =getConnection();
				PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setLong(1, user.getUserId());
				preparedstatement.setString(2,user.getUserName());
				preparedstatement.setString(3, user.getUserPhonenumber());
				preparedstatement.setString(4, user.getUserEmail());
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();; 
			}
			return false;
		}
		public boolean update(User user) {
			String sql="UPDATE authors SET  user_name = ?, user_phonenumber = ? user_email=? WHERE user_id = ?";
			try(Connection connection =getConnection();
				PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setString(1, user.getUserName());
				preparedstatement.setString(2,user.getUserPhonenumber());
				preparedstatement.setString(4, user.getUserEmail());
				
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
		public boolean delete(long user_id)
		{
			String sql="DELETE FROM users WHERE user_id = ?";
			try(Connection connection=getConnection();
					PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setLong(1, user_id);
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		public int countUsers() {
	        String sql = "SELECT COUNT(*) FROM users";
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql);
	             ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt(1);
	            }
	        } catch (SQLException e) {
	            logger.error("Error counting users", e);
	        }
	        return 0;
	    }
		}
	
	
	

