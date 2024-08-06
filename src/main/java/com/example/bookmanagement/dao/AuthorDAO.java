package com.example.bookmanagement.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.example.bookmanagement.model.Author;
import com.example.bookmanagement.util.DatabaseUtil;

public class AuthorDAO {
	
	private static Connection getConnection() throws SQLException {
		return DatabaseUtil.getConnection();
	}
	public List<Author> findAll(){
	     
	     
		List<Author> authors=new ArrayList<>();
		String sql="SELECT * FROM authors";
		try(Connection connnection=getConnection();
			Statement statement=connnection.createStatement();
			ResultSet resultset=statement.executeQuery(sql);){
			while(resultset.next()) {
				Author author=new Author();
				author.setID(resultset.getLong("author_id"));
				author.setTitle(resultset.getString("author_name"));
				author.setAuthor(resultset.getString("author_phonenumber"));
				authors.add(author);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();;
		}
		return authors;
		
	}
	public Author findByID(long id) {
		Author author =null;
		String sql="SELECT * FROM authors WHERE author_id = ?";
		try(Connection connection =getConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(sql);){
			preparedstatement.setLong(1, id);
			try(ResultSet resultset=preparedstatement.executeQuery()){
				if(resultset.next()) {
					author=new Author();
					author.setID(resultset.getLong("author_id"));
					author.setTitle(resultset.getString("author_name"));
					author.setAuthor(resultset.getString("author_phonenumber"));
					
					
				}
			}
			
		}catch(SQLException e) {
			 e.printStackTrace();;	
		}
		return author;
	}
		public boolean save(Author author) {
			String sql="INSERT INTO authors (author_id, author_name, author_phonenumber) VALUES (?, ?, ?)";
			try(Connection connection =getConnection();
				PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setLong(1, author.getauthorId());
				preparedstatement.setString(2, author.getAuthorName());
				preparedstatement.setString(3, author.getAuthorPhonenumber());
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();; 
			}
			return false;
		}
		public static boolean update(Author author) {
			String sql="UPDATE authors SET  author_name = ?, author_phonenumber = ? WHERE author_id = ?";
			try(Connection connection =getConnection();
				PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setString(1, author.getAuthorName());
				preparedstatement.setString(2,author.getAuthorPhonenumber());
				
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
		public static boolean delete(long author_id)
		{
			String sql="DELETE FROM authors WHERE author_id = ?";
			try(Connection connection=getConnection();
					PreparedStatement preparedstatement=connection.prepareStatement(sql)){
				preparedstatement.setLong(1, author_id);
				int rows=preparedstatement.executeUpdate();
				
				return rows>0;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		}
	
	
	

