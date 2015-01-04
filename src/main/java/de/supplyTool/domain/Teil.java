package de.supplyTool.domain;

import java.io.Serializable;

/**
 * Tabelle "teil"
 * 
 * @author Tobias
 * 
 */
public class Teil implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8912889355105295255L;
    private int               nummer;
    private String            bezeichnung;
    private Verwendung        verwendung;
    private String            buchstabe;
    private double            wert;

    public Teil() {

    }

    public Teil(final int nummer, final String bezeichnung,
            final Verwendung verwendung, final String buchstabe,
            final double wert) {
        this.nummer = nummer;
        this.bezeichnung = bezeichnung;
        this.buchstabe = buchstabe;
        this.wert = wert;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(final int nummer) {
        this.nummer = nummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(final String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Verwendung getVerwendung() {
        return verwendung;
    }

    public void setVerwendung(final Verwendung verwendung) {
        this.verwendung = verwendung;
    }

    public String getBuchstabe() {
        return buchstabe;
    }

    public void setBuchstabe(final String buchstabe) {
        if (buchstabe.length() > 1)
            throw new RuntimeException(
                    "Die Laenge der Variable buchstabe ist groesser 1");
        this.buchstabe = buchstabe;
    }

    public double getWert() {
        return wert;
    }

    public void setWert(final double wert) {
        this.wert = wert;
    }

}
