import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.modele.*;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;

public class LivreDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        try {

            int id=Integer.valueOf(request.getParameter("id"));
            LivreService livreService = LivreServiceImpl.getInstance();
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt> listEmprunt=empruntService.getListCurrentByLivre(id);
            request.setAttribute("listEmprunt", listEmprunt);
            Livre livre=livreService.getById(id);
            request.setAttribute("id", livre.getId());
            request.setAttribute("titre", livre.getTitre());
            request.setAttribute("auteur", livre.getAuteur());
            request.setAttribute("isbn", livre.getIsbn());
            

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(request, response);

            
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new ServletException();
        }   
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);

            LivreService livreService = LivreServiceImpl.getInstance();
            int id=Integer.valueOf(request.getParameter("id"));
            String titre=request.getParameter("titre");
            String auteur=request.getParameter("auteur");
            String isbn=request.getParameter("isbn");


            livreService.update(new Livre(id,titre,auteur,isbn));

            this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException();
        }

        
	}
}
