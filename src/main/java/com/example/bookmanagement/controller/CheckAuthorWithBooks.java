package com.example.bookmanagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.bookmanagement.dao.AuthorDAO;
import com.example.bookmanagement.dao.BookDAO;
import com.example.bookmanagement.model.Author;
import com.example.bookmanagement.model.Book;
import com.google.gson.Gson;

public class CheckAuthorWithBooks extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(AuthorServlet.class);

    private final AuthorDAO authorDAO = new AuthorDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final Gson gson = new Gson();

    //http://localhost:8080/book-management/api/books
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.info("Recieved a input to checkAuthorWithBookServlet");
        
    	resp.setContentType("application/json");
       	String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
              List<Author> author = authorDAO.findAll();  
              List<Book> book = bookDAO.findAll(); 
              logger.info("Recieved a input to all Authoird from book and author table");
              
              String final_author = null;
              for(int i=0;i<author.size();i++) {
            	  logger.info("Entered in to first foor loop");
                  
            	  String author_name=author.get(i).getAuthorName();

            	  for(int j=0;j<book.size();j++) {
            		  logger.info("Entered in to first foor loop");
            		  String book_author_name=book.get(j).getAuthor();
            		  
            		  if(author_name.equals(book_author_name))
            		  {
            			  //found a match
            			  final_author=author_name;
            		
            		  }
            		  logger.info("checked with author name of each table");
            	  }
              }
              try (PrintWriter out = resp.getWriter()) {
                  out.print(gson.toJson(final_author));
              }
        
          
              if(final_author==null) {
            	  resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
              }
              logger.info("didnt find the author");
        }
       
        logger.info("Succesfully got the author");
    }
}
        
    



