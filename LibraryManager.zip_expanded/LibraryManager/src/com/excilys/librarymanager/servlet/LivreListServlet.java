package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDate;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

public class LivreListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            LivreService livreService = LivreServiceImpl.getInstance();
            List<Livre> listLivre=livreService.getList();
            request.setAttribute("listLivre", listLivre);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    
}