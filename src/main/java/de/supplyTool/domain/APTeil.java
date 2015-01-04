package de.supplyTool.domain;

import java.io.Serializable;

public class APTeil implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1218896372681112422L;

    public APTeil(final Integer nummer, final Integer anzahl,
            final Integer zeitProTeil) {
        super();
        this.nummer = nummer;
        this.anzahl = anzahl;
        this.zeitProTeil = zeitProTeil;
    }

    public APTeil() {
        // TODO Auto-generated constructor stub
    }

    Integer nummer;
    Integer anzahl;
    Integer zeitProTeil;

    public Integer getNummer() {
        return nummer;
    }

    public void setNummer(final Integer nummer) {
        this.nummer = nummer;
    }

    public Integer getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(final Integer anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    public String toString() {
        return "APTeil [nummer=" + nummer + ", anzahl=" + anzahl
                + ", zeitProTeil=" + zeitProTeil + "]";
    }

    public Integer getZeitProTeil() {
        return zeitProTeil;
    }

    public void setZeitProTeil(final Integer zeitProTeil) {
        this.zeitProTeil = zeitProTeil;
    }
}
