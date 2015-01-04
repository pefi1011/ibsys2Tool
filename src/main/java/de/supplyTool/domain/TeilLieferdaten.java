package de.supplyTool.domain;

import java.io.Serializable;

/**
 * Tabelle "lager_disposition"
 * 
 * @author Tobias
 * 
 */
public class TeilLieferdaten implements Serializable {

	private static final long serialVersionUID = -3532913767234588810L;
	private int lagerMenge;
	private int diskontMenge;
	private double bestellkosten;
	private double wiederbeschaffungszeitPeriode;
	private int wiederbeschaffungszeitTage;
	private double abweichungPeriode;
	private int abweichungtage;
	private int teileNummer;

	public int getLagerMenge() {
		return lagerMenge;
	}

	public void setLagerMenge(int lagerMenge) {
		this.lagerMenge = lagerMenge;
	}

	public int getDiskontMenge() {
		return diskontMenge;
	}

	public void setDiskontMenge(int diskontMenge) {
		this.diskontMenge = diskontMenge;
	}

	public double getBestellkosten() {
		return bestellkosten;
	}

	public void setBestellkosten(double bestellkosten) {
		this.bestellkosten = bestellkosten;
	}

	public double getWiederbeschaffungszeitPeriode() {
		return wiederbeschaffungszeitPeriode;
	}

	public void setWiederbeschaffungszeitPeriode(
			double wiederbeschaffungszeitPeriode) {
		this.wiederbeschaffungszeitPeriode = wiederbeschaffungszeitPeriode;
	}

	public int getWiederbeschaffungszeitTage() {
		return wiederbeschaffungszeitTage;
	}

	public void setWiederbeschaffungszeitTage(int wiederbeschaffungszeitTage) {
		this.wiederbeschaffungszeitTage = wiederbeschaffungszeitTage;
	}

	public double getAbweichungPeriode() {
		return abweichungPeriode;
	}

	public void setAbweichungPeriode(double abweichungPeriode) {
		this.abweichungPeriode = abweichungPeriode;
	}

	public int getAbweichungTage() {
		return abweichungtage;
	}

	public void setAbweichungTage(int abweichungtage) {
		this.abweichungtage = abweichungtage;
	}

	public int getTeileNummer() {
		return teileNummer;
	}

	public void setTeileNummer(int teileNummer) {
		this.teileNummer = teileNummer;
	}

}
