package com.excilys.librarymanager.test;

import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.modele.Livre;

public class DaoTest {
	
	private static LivreDao livreInstance = LivreDaoImpl.getInstance();
	
}