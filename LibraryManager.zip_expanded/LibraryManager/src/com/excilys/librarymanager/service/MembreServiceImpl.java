package com.excilys.librarymanager.service;

import java.util.ArrayList;
import java.util.List;


import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.MembreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.modele.Membre;

public class MembreServiceImpl implements MembreService {
	
	private static MembreService instance;
	private MembreServiceImpl() { }	
	
	public static MembreService getInstance() {
		if(instance == null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}
	
	@Override
	public List<Membre> getList() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membreList = new ArrayList<Membre>();
		try {
			membreList = membreDao.getList();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return membreList;
	}
	
	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		List<Membre> membreList = new ArrayList<Membre>();
		List<Membre> possibleMembreList = new ArrayList<Membre>();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		try {
			membreList = membreDao.getList(); 
			for(int i=0; i<membreList.size();i++) {
				if(empruntService.isEmpruntPossible(membreList.get(i))) {
					possibleMembreList.add(membreList.get(i));
				}
			}
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		catch(ServiceException e2) {
			System.out.println(e2.getMessage());
		}
		return possibleMembreList;
	}
	
	@Override
	public Membre getById(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return membre;
	}
	
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int membre=-1;
		try {
			if(nom==null || prenom==null) {
				throw new ServiceException("Erreur : Nom ou prénom non renseigné.");
			}
			membre = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return membre;
	}
	
	@Override
	public void update(Membre membre) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			if(membre.getNom()==null || membre.getPrenom()==null) {
				throw new ServiceException("Erreur : Nom ou prénom non renseigné.");
			}
			membre.setNom(membre.getNom().toUpperCase());
			membreDao.update(membre);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public void delete(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			membreDao.delete(id);
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	@Override
	public int count() throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int nb = -1;
		try {
			nb = membreDao.count();
		}
		catch(DaoException e1) {
			System.out.println(e1.getMessage());
		}
		return nb;
	}
}
