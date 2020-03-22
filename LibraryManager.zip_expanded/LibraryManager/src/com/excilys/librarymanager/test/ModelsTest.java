package com.excilys.librarymanager.test;
import java.time.LocalDate;

import com.excilys.librarymanager.modele.*;

public class ModelsTest {
	
	public static void main(String ...Args) {
	Membre user1 = new Membre(1, "Bornachot", "Yohan", "ENSTA", "yohan.bornachot@gmail.com", "0648756082", Abonnement.VIP);
	System.out.println(user1.toString());
	
	Livre book1 = new Livre(1, "Harry-Potter", "J.K. Rowling", "123456789");
	System.out.println(book1.toString());
	
	LocalDate DateEmprunt = LocalDate.of(2020,01,01);
	LocalDate DateRetour = LocalDate.of(2020, 01, 20);
	Emprunt emprunt1 = new Emprunt(1, user1, book1, DateEmprunt, DateRetour);
	System.out.println(emprunt1.toString());
	}
}
