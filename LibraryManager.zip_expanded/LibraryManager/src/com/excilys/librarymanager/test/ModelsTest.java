package com.excilys.librarymanager.test;
import java.time.LocalDate;

import com.excilys.librarymanager.modele.*;

public class ModelsTest {
	
	public static void main(String ...Args) {
		Livre livre1 = new Livre(1,"Harry Potter", "JK Rowling", "123-456-789");
		Livre livre2 = new Livre(2,"L'Odyssée", "Homere", "123-456-000");
		Livre livre3 = new Livre(3,"L'Iliade", "Homere", "314-159-265");
		
		Membre membre1 = new Membre(1,"McGregor", "Conor", "13 Rue du Soleil Levant",
				"Conor.McGregor@ensta-paris.fr", "0123456789", Abonnement.VIP);
		Membre membre2 = new Membre(2,"Chan", "Jackie", "11 Avenue des Champs Elysées",
				"Jacquie.Chan@ensta-paris.fr", "0112233445", Abonnement.PREMIUM);
		
		Emprunt emprunt1 = new Emprunt(1,membre1,livre1,LocalDate.of(1998, 11, 13),LocalDate.of(1999, 11, 14));
		Emprunt emprunt2 = new Emprunt(2,membre1,livre2,LocalDate.of(2000, 1, 1),LocalDate.of(2021, 10, 5));
		Emprunt emprunt3 = new Emprunt(3,membre2,livre1,LocalDate.of(2005, 12, 31),LocalDate.of(2015, 10, 5));
		Emprunt emprunt4 = new Emprunt(4,membre2,livre3,LocalDate.of(2010, 3, 24),LocalDate.of(2016, 1, 1));

		System.out.println(emprunt1);
		System.out.println(emprunt1.getMembre());
		System.out.println(emprunt1.getLivre());
		
		System.out.println(emprunt2);
		System.out.println(emprunt3);
		System.out.println(emprunt4);

	}
	
}