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

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.model.UserReadsBook;
import com.example.bookmanagement.util.DatabaseUtil;

public class UserReadsBookDAO  {
	private static final Logger logger = LoggerFactory.getLogger(UserReadsBookDAO.class);
	private Connection getConnection() throws SQLException {
		return DatabaseUtil.getConnection();
	}
	public List<UserReadsBook> findAll(){
	     logger.info("Received a request for find all method");
	     
		List<UserReadsBook> user_books=new ArrayList<>();
		String sql="SELECT * FROM usersReadBook";
		try(Connection connnection=getConnection();
			Statement statement=connnection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);){
			while(resultset.next()) {
				UserReadsBook user_book=new UserReadsBook();
				user_book.setusersReadBook_id(resultset.getLong("usersReadBook_id"));
				user_book.setUserId(resultset.getString("user_id"));
				user_book.setUserName(resultset.getString("user_name"));
				user_book.setBookId(resultset.getString("book_id"));
				user_book.setBookTitle(resultset.getString("book_title"));
				user_books.add(user_book);
			}
			logger.info("got all the records from usersReadBook table" );
		}catch(SQLException e) {
			logger.error("Error finding all books", e);
		}
		return user_books;
	}
	public UserReadsBook findByID(String book_id) {
		UserReadsBook user_books =null;
		String sql="SELECT * FROM usersReadBook WHERE book_id = ?";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql);){
			preparedstatement.setString(1, book_id);
			try(ResultSet resultset=preparedstatement.executeQuery()){
				if(resultset.next()) {
					user_books=new UserReadsBook();
					user_books.setusersReadBook_id(resultset.getLong("usersReadBook_id"));
					user_books.setUserId(resultset.getString("user_id"));
					user_books.setUserName(resultset.getString("user_name"));
					user_books.setBookId(resultset.getString("book_id"));
					user_books.setBookTitle(resultset.getString("book_title"));
				
				}
			}
			
		}catch(SQLException e) {
			 e.printStackTrace();;	
		}
		return user_books;
	}
	public boolean save(UserReadsBook user_book) {
		String sql="INSERT INTO usersReadBook ( user_id, user_name,book_id,book_title) VALUES (?, ?, ?,?)";
		User user=new User();
		Book book1=new Book();
		UserReadsBook user_books=new UserReadsBook();
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql)){
			preparedstatement.setString(1,user_book.getUserId() );
			preparedstatement.setString(2,user_book.getUserName());
			preparedstatement.setString(3, user_book.getBookId());
			preparedstatement.setString(4,user_book.getBookTitle());
			int rows=preparedstatement.executeUpdate();
			logger.info("Succesfully inserted a row in to usersReadBook table");
			return rows>0;
		}catch(SQLException e) {
			logger.error("Error saving book", e); 
		}
		return false;
	}
}
