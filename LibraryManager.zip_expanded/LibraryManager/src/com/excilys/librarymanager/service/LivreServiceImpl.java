package com.excilys.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Livre;


public class LivreServiceImpl implements LivreService {
	
	private static LivreService instance;
	private LivreServiceImpl() { }	
	
	public static LivreService getInstance() {
		if(instance == null) {
			instance = new LivreServiceImpl();
		}
		return instance;
	}
	
	@Override
	public List<Livre> getList() throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livreList = new ArrayList<Livre>();
		try {
			livreList = livreDao.getList();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return livreList;
	}
	
	@Override
	public List<Livre> getListDispo() throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		List<Livre> livreList = new ArrayList<Livre>();
		List<Livre> possibleLivreList = new ArrayList<Livre>();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		try {
			livreList = livreDao.getList(); 
			for(int i=0; i<livreList.size();i++) {
				if(empruntService.isLivreDispo(livreList.get(i).getId())) {
					possibleLivreList.add(livreList.get(i));
				}
			}
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		catch(ServiceException e2) {
			System.out.println(e2.getMessage());
		}
		return possibleLivreList;
	}
	
	@Override
	public Livre getById(int id) throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return livre;
	}
	
	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		int idLivre=-1;
		try {
			if(titre==null) {
				throw new ServiceException("Erreur : Titre du livre non renseigné.");
			}
			idLivre = livreDao.create(titre, auteur, isbn);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return idLivre;
	}
	
	@Override
	public void update(Livre livre) throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		try {
			if(livre.getTitre()==null) {
				throw new ServiceException("Erreur : Titre du livre non renseigné.");
			}
			livreDao.update(livre);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public void delete(int id) throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		try {
			livreDao.delete(id);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public int count() throws ServiceException{
		LivreDao livreDao = LivreDaoImpl.getInstance();
		int nb = -1;
		try {
			nb = livreDao.count();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return nb;
	}
}