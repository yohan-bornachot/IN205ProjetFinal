package com.excilys.librarymanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.EmpruntDaoImpl;
import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Abonnement;
import com.excilys.librarymanager.modele.Emprunt;
import com.excilys.librarymanager.modele.Livre;
import com.excilys.librarymanager.modele.Membre;

public class EmpruntServiceImpl implements EmpruntService{
	private static EmpruntService instance;
	private EmpruntServiceImpl() { }	

	public static EmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntServiceImpl();
		}
		return instance;
	}
	
	@Override
	public List<Emprunt> getList() throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getList();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return empruntList;
	}
	
	@Override
	public List<Emprunt> getListCurrent() throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getListCurrent();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return empruntList;
	}
	
	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getListCurrentByMembre(idMembre);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return empruntList;
	}
	
	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getListCurrentByLivre(idLivre);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return empruntList;
	}
	
	@Override
	public Emprunt getById(int id) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return emprunt;
	}
	
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public void returnBook(int id) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			Emprunt emprunt = empruntDao.getById(id);
			emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public int count() throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		int count=-1;
		try {
			count = empruntDao.count();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return count;
	}
	
	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getListCurrentByLivre(idLivre);
			if(empruntList.isEmpty()) {
				return true;
			}
			else return false;
		}
		catch(DaoException e1) {
			throw new ServiceException("Problème lors de la requête 'isLivreDispo' ");
		}
	}
	
	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException{
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> empruntList = new ArrayList<Emprunt>();
		try {
			empruntList = empruntDao.getListCurrentByMembre(membre.getId());
			int n = empruntList.size(); 
			Abonnement abo = membre.getAbonnement();
			switch(abo) {
			case BASIC :
				if(n<2) {
					return true;
				}
				else return false;
			case PREMIUM :
				if(n<5) {
					return true;
				}
				else return false;
			case VIP :
				if(n<20) {
					return true;
				}
				else return false;
			}
		}
		catch(DaoException e1) {
			throw new ServiceException("Problème lors de la requête 'isEmpruntPossible'");
		}
		return false;
	}
}