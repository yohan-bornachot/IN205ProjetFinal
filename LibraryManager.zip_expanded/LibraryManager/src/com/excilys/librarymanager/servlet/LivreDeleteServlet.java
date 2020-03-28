package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

public class LivreDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt> listEmprunt=empruntService.getListCurrent();

            request.setAttribute("listEmprunt", listEmprunt);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_delete.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            LivreService livreService = LivreServiceImpl .getInstance();
            int id=Integer.valueOf(request.getParameter("id"));
            
            livreService.delete(id);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }

        
	}
}