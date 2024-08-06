package com.example.bookmanagement.controller;
import com.example.bookmanagement.dao.BookDAO;
import com.example.bookmanagement.model.Book;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/api/books/*")
public class BookServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(BookServlet.class);
    private final BookDAO bookDAO = new BookDAO();
    private final Gson gson = new Gson();

    //http://localhost:8080/book-management/api/books
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       logger.info("Received a request from user with URL  :  " + req.getRequestURL());
    	resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            logger.info("Received a request and making a find all call: " + req.getRequestURL());
            List<Book> books = bookDAO.findAll();            
            try (PrintWriter out = resp.getWriter()) {
                out.print(gson.toJson(books));
            }
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length > 1) {
            	 logger.info("Received a request from one users with ID  :  " + pathParts[1]);
                Long id = Long.parseLong(pathParts[1]);
                Book book = bookDAO.findByID(id);
                if (book != null) {
                    try (PrintWriter out = resp.getWriter()) {
                        out.print(gson.toJson(book));
                    }
                } else {
                  	 logger.info("Received a request from one users with ID Not found  :  ");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
        logger.info("Replied to the request for reuqest with URL  :  " + req.getRequestURL());
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Book book = gson.fromJson(req.getReader(), Book.class);
        boolean success = bookDAO.save(book);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            Long id = Long.parseLong(pathParts[1]);
            Book book = gson.fromJson(req.getReader(), Book.class);
            book.setID(id);
            boolean success = bookDAO.update(book);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            Long id = Long.parseLong(pathParts[1]);
            boolean success = bookDAO.delete(id);
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
