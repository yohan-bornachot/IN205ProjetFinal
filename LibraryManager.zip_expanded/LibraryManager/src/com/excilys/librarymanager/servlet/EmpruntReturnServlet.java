package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;

public class EmpruntReturnServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {
            EmpruntService empruntService = EmpruntServiceImpl .getInstance();
            List<Emprunt> listEmprunt=empruntService.getListCurrent();

            request.setAttribute("listEmprunt", listEmprunt);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            EmpruntService empruntService = EmpruntServiceImpl .getInstance();
            int id=Integer.valueOf(request.getParameter("id"));

            empruntService.returnBook(id);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }

        
	}
}
