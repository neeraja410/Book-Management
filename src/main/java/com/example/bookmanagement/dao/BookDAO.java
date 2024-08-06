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

import com.example.bookmanagement.model.*;
import com.example.bookmanagement.util.*;
public class BookDAO {
//added my first comment on bookdao program
	private static final Logger logger = LoggerFactory.getLogger(BookDAO.class);
	private Connection getConnection() throws SQLException {
		return DatabaseUtil.getConnection();
	}
	public List<Book> findAll(){
	     logger.info("Received a request for find all method");

		List<Book> books=new ArrayList<>();
		String sql="SELECT * FROM books";
		try(Connection connnection=getConnection();
			Statement statement=connnection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);){
			while(resultset.next()) {
				Book book=new Book();
				book.setID(resultset.getLong("id"));
				book.setTitle(resultset.getString("title"));
				book.setAuthor(resultset.getString("author"));
				book.setIsbn(resultset.getString("isbn"));
				books.add(book);
			}
			logger.info("Find All methond:Found {} books", books.size());
		}catch(SQLException e) {
			logger.error("Error finding all books", e);
		}
		return books;
		
	}
	public Book findByID(long id) {
		Book book =null;
		String sql="SELECT * FROM books WHERE id = ?";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql);){
			preparedstatement.setLong(1, id);
			try(ResultSet resultset=preparedstatement.executeQuery()){
				if(resultset.next()) {
					logger.info("Found book with id {}", id);
					book=new Book();
					book.setID(resultset.getLong("id"));
					book.setTitle(resultset.getString("title"));
					book.setAuthor(resultset.getString("author"));
					book.setIsbn(resultset.getString("isbn"));
				}
			}
		}catch(SQLException e) {
			 logger.error("Error finding book by id", e);	
		}
		return book;
	}
	public boolean save(Book book) {
		String sql="INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql)){
			preparedstatement.setString(1, book.getTitle());
			preparedstatement.setString(2, book.getAuthor());
			preparedstatement.setString(3, book.getIsbn());
			int rows=preparedstatement.executeUpdate();
			logger.info("Saved book: {}", book);
			return rows>0;
		}catch(SQLException e) {
			logger.error("Error saving book", e); 
		}
		return false;
	}
	public boolean update(Book book) {
		String sql="UPDATE books SET title = ?, author = ?, isbn = ? WHERE id = ?";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql)){
			preparedstatement.setString(1, book.getTitle());
			preparedstatement.setString(2,book.getAuthor());
			preparedstatement.setString(3, book.getIsbn());
			int rows=preparedstatement.executeUpdate();
			logger.info("Updated book: {}", book);
			return rows>0;
		}catch(SQLException e) {
			logger.error("Error updating book", e);
		}
		return false;
		
	}
	public boolean delete(long id)
	{
		String sql="DELETE FROM books WHERE id = ?";
		try(Connection connection=getConnection();
				PreparedStatement preparedstatement=connection.prepareStatement(sql)){
			preparedstatement.setLong(1, id);
			int rows=preparedstatement.executeUpdate();
			logger.info("Deleted book with id {}", id);
			return rows>0;
		}catch(SQLException e) {
			logger.error("Error deleting book", e);
		}
		return false;
	}
}
 
