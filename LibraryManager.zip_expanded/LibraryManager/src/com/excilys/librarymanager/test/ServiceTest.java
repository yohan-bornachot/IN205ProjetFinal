package com.excilys.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.modele.*;

public class ServiceTest {
	
	public static void main(String[] Args) throws Exception{
		
		// ---------- Test implÃ©mentation service livre ----------

		LivreService livre = LivreServiceImpl.getInstance();
		List<Livre> livreList;
		
		try {
			DaoTest.printHeader("# # # # # # # # Tests d'implÃ©mentation service pour les livres # # # # # # # #");
			DaoTest.printHeader("CrÃ©ation d'un livre");
			int idNewLivre = livre.create("Le livre de la jungle", "Rudyard Kipling","111-3141592654");
			
			DaoTest.printHeader("RÃ©cupÃ©ration d'un livre par son identifiant");
			Livre newLivre = livre.getById(idNewLivre);
			System.out.println(newLivre);
			
			DaoTest.printHeader("Mise Ã  jour d'un livre");
			newLivre.setIsbn("111-1111111111");
			livre.update(newLivre);
			
			DaoTest.printHeader("Liste de tous les livres");
			livreList = livre.getList();
			for (int i=0; i<livreList.size();i++) 
				System.out.println(livreList.get(i));
			
			DaoTest.printHeader("Comptage du nombre de livres");
			System.out.println("nombre de livres : " + livre.count());
			
			DaoTest.printHeader("Suppression d'un livre");
			livre.delete(idNewLivre);
			
			DaoTest.printHeader("Comptage du nombre de livres");
			System.out.println("nombre de livres : " + livre.count());
			
			DaoTest.printHeader("Liste de tous les livres disponibles");
			livreList = livre.getListDispo();
			for (int i=0; i<livreList.size();i++) 
				System.out.println(livreList.get(i));

 			System.out.println("\nNB : 7 livres sont disponibles.\nDans la BD initiale qui possÃ¨de 10 livres,\n" + 
						"3 livres n'ont pas encore Ã©tÃ© rendus. Ã‡a paraÃ®t cohÃ©rent.");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		// ---------- Test implémentation dao membre ----------
		
		
		MembreService membre = MembreServiceImpl.getInstance();
		List<Membre> membreList;
		
		try {
			DaoTest.printHeader("# # # # # # # # Tests d'implémentation service pour les membres # # # # # # # #");
			DaoTest.printHeader("Création d'un membre");
			int idNewMembre = membre.create("Vador","Dark","13 bis rue de l'étoile noire","dark.vador@ensta-paris.fr","0101010101",Abonnement.VIP);
						
			DaoTest.printHeader("Récupération d'un membre par son identifiant");
			Membre newMembre = membre.getById(idNewMembre);
			System.out.println(newMembre);
			
			DaoTest.printHeader("Mise à  jour d'un membre");
			newMembre.setAbonnement(Abonnement.BASIC);
			membre.update(newMembre);
			
			DaoTest.printHeader("Liste de tous les membres");
			membreList = membre.getList();
			for (int i=0; i<membreList.size();i++) 
				System.out.println(membreList.get(i));
			
			DaoTest.printHeader("Comptage du nombre de membres");
			System.out.println("nombre de membres : " + membre.count());
			
			DaoTest.printHeader("Suppression d'un membre");
			membre.delete(idNewMembre);
			
			DaoTest.printHeader("Comptage du nombre de membres");
			System.out.println("nombre de membres : " + membre.count());
			
			DaoTest.printHeader("Comptage du nombre de membres qui peuvent encore emprunter");
			membreList = membre.getListMembreEmpruntPossible();
			for (int i=0; i<membreList.size();i++) 
				System.out.println(membreList.get(i));
			
 			System.out.println("\nNB : 11 membres peuvent encore emprunter un livre.\n" + 
 					"dans la BD initiale qui possède 12 membres, la seule personne qui semble ne plus pouvoir emprunter\n"+ 
 					"est Sylvie : elle a emprunté 2 livres et a un abonnement BASIC.\n"+
 					"Ca paraît cohérent.");
			
		} catch (Exception e) {
			
		System.out.println(e);
		
		}
		
		EmpruntService emprunt = EmpruntServiceImpl.getInstance();
		List<Emprunt> empruntList;
		
		try {
			DaoTest.printHeader("# # # # # # # # Tests d'implémentation de la dao pour les emprunts # # # # # # # #");

			DaoTest.printHeader("Comptage du nombre d'emprunts");
			System.out.println("nombre d'emprunts : " + emprunt.count());
			
			DaoTest.printHeader("Création d'un emprunt");
			int idNewEmprunt = emprunt.create(10, 10, LocalDate.of(1963, 07, 18));
			
			DaoTest.printHeader("Comptage du nombre d'emprunts");
			System.out.println("nombre d'emprunts : " + emprunt.count());
			
			DaoTest.printHeader("Récupération d'un emprunt par son identifiant");
			Emprunt newEmprunt = emprunt.getById(idNewEmprunt);
			System.out.println(newEmprunt);
			
			DaoTest.printHeader("Liste de tous les emprunts");
			empruntList = emprunt.getList();
			for (int i=0; i<empruntList.size();i++) 
				System.out.println(empruntList.get(i));

			
			DaoTest.printHeader("Liste des emprunts pas encore rendus");
			empruntList = emprunt.getListCurrent();
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			
			DaoTest.printHeader("Liste des emprunts pas encore rendus pour un membre donnÃ©");
			empruntList = emprunt.getListCurrentByMembre(4);
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			
			DaoTest.printHeader("Liste des emprunts pas encore rendus pour un livre donnÃ©");
			empruntList = emprunt.getListCurrentByLivre(2);
			for (int i=0; i<empruntList.size();i++)
				System.out.println(empruntList.get(i));
			
			DaoTest.printHeader("Test de retour d'emprunt");
			System.out.println("L'emprunt qui va être rendu est : ");
			System.out.println(emprunt.getById(idNewEmprunt));
			System.out.println("\nLe livre " + 10 + " n'est pas dispo car emprunt.isLivreDispo(10) = " + emprunt.isLivreDispo(10));
			System.out.println("On rend le livre 10.");
			emprunt.returnBook(idNewEmprunt);
			System.out.println("\nLe livre " + 10 + " est désormais dispo car emprunt.isLivreDispo(10) = " + emprunt.isLivreDispo(10));
			System.out.println("L'emprunt qui a été rendu devient : ");
			System.out.println(emprunt.getById(idNewEmprunt));


			DaoTest.printHeader("Test de disponibilité sur des livres");
			System.out.println("Le livre 3 n'est pas dispo, effectivement emprunt.isLivreDispo(3) = " + emprunt.isLivreDispo(3));
			System.out.println("Le livre 4 est dispo, effectivement emprunt.isLivreDispo(4) = " + emprunt.isLivreDispo(4));
			
			DaoTest.printHeader("Test de possibilité d'emprunt sur des membres");
			Membre membre5 = membre.getById(5);
			System.out.println("Le membre 5 (Sylvie) ne peut plus emprunter.\n"
					+ "En effet, emprunt.isEmpruntPossible(membre.getById(5)) = " + emprunt.isEmpruntPossible(membre5));
			Membre membre6 = membre.getById(6);
			System.out.println("Le membre 6 peut encore emprunter.\n"
					+ "En effet, emprunt.isEmpruntPossible(membre.getById(6)) = " + emprunt.isEmpruntPossible(membre6));


		} catch (Exception e) {
			System.out.println(e);
		}
		

	}
}
