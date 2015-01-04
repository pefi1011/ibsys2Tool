package de.supplyTool.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class APArbeitsplatz implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4639144052213869254L;

    public APArbeitsplatz(final ArrayList<APTeil> teile, final Integer nummer,
            final Integer schicht, final Integer ueberstunden,
            final Integer ruestzeit, final Integer warteschlange,
            final Integer inBearbeitung, final Double aufschlag) {
        super();
        this.teile = teile;
        this.nummer = nummer;

        this.schicht = schicht;
        this.ueberstunden = ueberstunden;
        this.ruestzeit = ruestzeit;
        this.warteschlange = warteschlange;
        this.inBearbeitung = inBearbeitung;
        Aufschlag = aufschlag;
    }

    ArrayList<APTeil> teile;
    Integer           nummer;

    Integer           schicht;
    Integer           ueberstunden;
    Integer           ruestzeit;
    Integer           warteschlange;
    Integer           inBearbeitung;
    Double            Aufschlag;
    Integer           ueberstundenGut;

    public ArrayList<APTeil> getTeile() {
        return teile;
    }

    public void setTeile(final ArrayList<APTeil> teile) {
        this.teile = teile;
    }

    public Integer getNummer() {
        return nummer;
    }

    public void setNummer(final Integer nummer) {
        this.nummer = nummer;
    }

    public Integer getSchicht() {
        return schicht;
    }

    public void setSchicht(final Integer schicht) {
        this.schicht = schicht;
    }

    public Integer getUeberstunden() {
        return ueberstunden;
    }

    public void setUeberstunden(final Integer ueberstunden) {
        this.ueberstunden = ueberstunden;
    }

    public Integer getRuestzeit() {
        return ruestzeit;
    }

    public void setRuestzeit(final Integer ruestzeit) {
        this.ruestzeit = ruestzeit;
    }

    public Integer getWarteschlange() {
        return warteschlange;
    }

    public void setWarteschlange(final Integer warteschlange) {
        this.warteschlange = warteschlange;
    }

    public Integer getInBearbeitung() {
        return inBearbeitung;
    }

    public void setInBearbeitung(final Integer inBearbeitung) {
        this.inBearbeitung = inBearbeitung;
    }

    public Double getAufschlag() {
        return Aufschlag;
    }

    public void setAufschlag(final Double aufschlag) {
        Aufschlag = aufschlag;
    }

    @Override
    public String toString() {
        return "APArbeitsplatz [nummer=" + nummer + ", schicht=" + schicht
                + ", ueberstundenGut=" + ueberstundenGut + ", ueberstunden="
                + ueberstunden + ", ruestzeit=" + ruestzeit
                + ", warteschlange=" + warteschlange + ", inBearbeitung="
                + inBearbeitung + ", Aufschlag=" + Aufschlag + ", teile="
                + teile + "]";
    }

    public Integer getUeberstundenGut() {
        return ueberstundenGut;
    }

    public void setUeberstundenGut(final Integer ueberstundenGut) {
        this.ueberstundenGut = ueberstundenGut;
    }
}
