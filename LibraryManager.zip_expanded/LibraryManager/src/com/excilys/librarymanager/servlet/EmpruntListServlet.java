package com.excilys.librarymanager.servlet;

import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;


public class EmpruntListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt> listEmprunt;
            String show=request.getParameter("show");
          
            if(show!=null && show.equals("all")) listEmprunt=empruntService.getList();
            else listEmprunt=empruntService.getListCurrent();

            request.setAttribute("listEmprunt", listEmprunt);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }
}
