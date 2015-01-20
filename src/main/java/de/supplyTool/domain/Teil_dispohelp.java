package de.supplyTool.domain;

import java.io.Serializable;

public class Teil_dispohelp implements Serializable {

	private static final long	serialVersionUID	= 9019471197249718406L;
	Integer						reihe;
	Integer						nummer;
	Integer						vertriebswunsch;
	Integer						helpint;
	Integer						geplante_lagermenge;
	Integer						lagerbestand_ende_vorperiode;
	Integer						auftraege_warteschlange;
	Integer						auftrage_bearbeitung;
	Integer						produktionsauftrag_naechste_periode;
	Integer						warteschlangeHlep = -1;

	public Integer getWarteschlangeHlep() {
		return getProduktionsauftrag_naechste_periode() - getVertriebswunsch() - getGeplante_lagermenge() + getLagerbestand_ende_vorperiode() + getAuftraege_warteschlange() + getAuftrage_bearbeitung();
	}

	public void setWarteschlangeHlep(Integer warteschlangeHlep) {
		this.warteschlangeHlep = warteschlangeHlep;
	}

	public Teil_dispohelp() {

	}

	public Integer getReihe() {
		return reihe;
	}

	public void setReihe(Integer reihe) {
		this.reihe = reihe;
	}

	public Integer getNummer() {
		return nummer;
	}

	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}

	public Integer getGeplante_lagermenge() {
		return geplante_lagermenge;
	}

	public void setGeplante_lagermenge(Integer geplante_lagermenge) {
		this.geplante_lagermenge = geplante_lagermenge;
	}

	public Integer getLagerbestand_ende_vorperiode() {
		return lagerbestand_ende_vorperiode;
	}

	public void setLagerbestand_ende_vorperiode(
			Integer lagerbestand_ende_vorperiode) {
		this.lagerbestand_ende_vorperiode = lagerbestand_ende_vorperiode;
	}

	public Integer getAuftraege_warteschlange() {
		return auftraege_warteschlange;
	}

	public void setAuftraege_warteschlange(Integer auftraege_warteschlange) {
		this.auftraege_warteschlange = auftraege_warteschlange;
	}

	public Integer getAuftrage_bearbeitung() {
		return auftrage_bearbeitung;
	}

	public void setAuftrage_bearbeitung(Integer auftrage_bearbeitung) {
		this.auftrage_bearbeitung = auftrage_bearbeitung;
	}

	public Integer getProduktionsauftrag_naechste_periode() {

		return produktionsauftrag_naechste_periode;
	}

	public void setProduktionsauftrag_naechste_periode(
			Integer produktionsauftrag_naechste_periode) {
		if (produktionsauftrag_naechste_periode > 0)
			this.produktionsauftrag_naechste_periode = produktionsauftrag_naechste_periode;
		else {
			this.produktionsauftrag_naechste_periode = 0;
		}
	}

	@Override
	public String toString() {
		return "Teil_dispohelp [reihe=" + reihe + ", nummer=" + nummer
				+ ", vertriebswunsch=" + vertriebswunsch + ", helpint="
				+ helpint + ", geplante_lagermenge=" + geplante_lagermenge
				+ ", lagerbestand_ende_vorperiode="
				+ lagerbestand_ende_vorperiode + ", auftraege_warteschlange="
				+ auftraege_warteschlange + ", auftrage_bearbeitung="
				+ auftrage_bearbeitung
				+ ", produktionsauftrag_naechste_periode="
				+ produktionsauftrag_naechste_periode + "]";
	}

	public Integer getVertriebswunsch() {
		return vertriebswunsch;
	}

	public void setVertriebswunsch(Integer vertriebswunsch) {
		if (vertriebswunsch < 0) {
			this.vertriebswunsch = 0;
		} else {
			this.vertriebswunsch = vertriebswunsch;
		}
	}

	public Integer getHelpint() {
		return helpint;
	}

	public void setHelpint(Integer helpint) {
		this.helpint = helpint;
	}

}
