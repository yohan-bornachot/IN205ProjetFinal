package com.excilys.librarymanager.servlet;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

public class MembreDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {
            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_delete.jsp").forward(request, response);
            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            MembreService membreService = MembreServiceImpl.getInstance();
            int id=Integer.valueOf(request.getParameter("id"));
            
            membreService.delete(id);

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }
	}
}
