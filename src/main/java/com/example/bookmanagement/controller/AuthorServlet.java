package com.example.bookmanagement.controller;
import com.example.bookmanagement.dao.AuthorDAO;
import com.example.bookmanagement.model.Author;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/api/books/*")
public class AuthorServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AuthorServlet.class);

    private final AuthorDAO authorDAO = new AuthorDAO();
    private final Gson gson = new Gson();

    //http://localhost:8080/book-management/api/books
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       	resp.setContentType("application/json");
       	logger.info("Recieved a input to AuthorServlet");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
              List<Author> author = authorDAO.findAll();  
            try (PrintWriter out = resp.getWriter()) {
                out.print(gson.toJson(author));
            }
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length > 1) {
            	  Long author_id = Long.parseLong(pathParts[1]);
                Author author = authorDAO.findByID(author_id);
                if (author != null) {
                    try (PrintWriter out = resp.getWriter()) {
                        out.print(gson.toJson(author));
                    }
                } else {
                  	  resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } 
        
        logger.info("Replied to request to get method ");
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     	logger.info("Recieved a input to create author recors");
        
    	resp.setContentType("application/json");
        Author author = gson.fromJson(req.getReader(), Author.class);
        boolean success = authorDAO.save(author);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
     	logger.info("Succesfully created a author record");
     	
//sendEmail
        
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     	logger.info("Recieved a input to upadte the author record");
        
    	resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            Long author_id = Long.parseLong(pathParts[1]);
            Author author = gson.fromJson(req.getReader(), Author.class);
            author.setID(author_id);
            boolean success = AuthorDAO.update(author);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
     	logger.info("Sucuessfully upated the record of author");
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     	logger.info("Recieved a input to delete a record from author");
        
    	resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            Long author_id = Long.parseLong(pathParts[1]);
            boolean success = AuthorDAO.delete(author_id);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
     	logger.info("Sucuessfully deleted the record from authors table");
        
    }
}
