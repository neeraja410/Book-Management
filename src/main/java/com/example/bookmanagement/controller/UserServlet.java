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
import com.example.bookmanagement.dao.UserDAO;
import com.example.bookmanagement.model.User;
import com.example.bookmanagement.util.SendEmailUtil;
import com.google.gson.Gson;

public class UserServlet extends HttpServlet {
	
		private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

	    private final UserDAO userDAO = new UserDAO();
	    private final Gson gson = new Gson();
	    SendEmailUtil sendemail=new SendEmailUtil();

	    //http://localhost:8080/book-management/api/books
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	       	resp.setContentType("application/json");
	       	logger.info("Recieved a input to userServlet");
	        String pathInfo = req.getPathInfo();
	        if (pathInfo == null || pathInfo.equals("/")) {
	              List<User> user = userDAO.findAll();  
	            try (PrintWriter out = resp.getWriter()) {
	                out.print(gson.toJson(user));
	            }
	        } else {
	            String[] pathParts = pathInfo.split("/");
	            if (pathParts.length > 1) {
	            	  Long user_id = Long.parseLong(pathParts[1]);
	                User user = userDAO.findByID(user_id);
	                if (user != null) {
	                    try (PrintWriter out = resp.getWriter()) {
	                        out.print(gson.toJson(user));
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
	     	logger.info("Recieved a input to create user recors");
	        
	    	resp.setContentType("application/json");
	        User user = gson.fromJson(req.getReader(), User.class);
	        boolean success = userDAO.save(user);
	        if (success) {
	            resp.setStatus(HttpServletResponse.SC_CREATED);
	        } else {
	            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        }
	     	logger.info("Succesfully created a user record");
	     	
	     	sendemail.sendEmail();
	     	logger.info("Succesfully sent the email to Mr.jagadesh");
	     	
	        
	    }

	    @Override
	    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	     	logger.info("Recieved a input to upadte the user record");
	        
	    	resp.setContentType("application/json");
	        String pathInfo = req.getPathInfo();
	        String[] pathParts = pathInfo.split("/");
	        if (pathParts.length > 1) {
	            Long user_id = Long.parseLong(pathParts[1]);
	            User user = gson.fromJson(req.getReader(), User.class);
	            user.setUserId(user_id);
	            boolean success = userDAO.update(user);
	            if (success) {
	                resp.setStatus(HttpServletResponse.SC_OK);
	            } else {
	                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            }
	        } else {
	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        }
	     	logger.info("Sucuessfully upated the record of user");
	        
	    }

	    @Override
	    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	     	logger.info("Recieved a input to delete a record from user");
	        
	    	resp.setContentType("application/json");
	        String pathInfo = req.getPathInfo();
	        String[] pathParts = pathInfo.split("/");
	        if (pathParts.length > 1) {
	            Long user_id = Long.parseLong(pathParts[1]);
	            boolean success = userDAO.delete(user_id);
	            if (success) {
	                resp.setStatus(HttpServletResponse.SC_OK);
	            } else {
	                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            }
	        } else {
	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        }
	     	logger.info("Sucuessfully deleted the record from users table");
	        
	    }

}
