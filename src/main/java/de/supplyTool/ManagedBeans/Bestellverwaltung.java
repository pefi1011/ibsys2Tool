package de.supplyTool.ManagedBeans;

import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.supplyTool.dao.Dao;
import de.supplyTool.domain.Bestellung;

@ManagedBean
@ApplicationScoped
public class Bestellverwaltung {

	private Dao dao = new Dao();
	
	public ArrayList<Bestellung> getEingetroffeneBestellungen(){
		return dao.getBestellung(true);
	}
	
	public ArrayList<Bestellung> getOffeneBestellungen(){
		return dao.getBestellung(false);
	}
	
}
