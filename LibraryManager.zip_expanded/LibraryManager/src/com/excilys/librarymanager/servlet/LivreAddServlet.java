package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDate;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

public class LivreAddServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            LivreService livreService = LivreServiceImpl .getInstance();
            String titre=request.getParameter("titre");
            String auteur=request.getParameter("auteur");
            String isbn=request.getParameter("isbn");

            livreService.create(titre, auteur, isbn);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }

        
	}
}
