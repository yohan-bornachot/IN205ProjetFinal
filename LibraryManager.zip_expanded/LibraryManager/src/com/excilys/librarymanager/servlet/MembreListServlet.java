package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class MembreListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            MembreService membreService = MembreServiceImpl.getInstance();
            List<Membre> listMembre=membreService.getList();
            request.setAttribute("listMembre", listMembre);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_list.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    
}