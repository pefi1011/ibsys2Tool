package de.supplyTool.domain;

import java.io.Serializable;

/**
 * Tabelle "arbeitsplatz_daten"
 * 
 * @author Tobias
 * 
 */
public class ArbeitsplatzKosten implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4931912081182014102L;
    private double            nummer;
    private double            lohnSchicht1;
    private double            lohnSchicht2;
    private double            lohnSchicht3;
    private double            lohnUeberstunden;
    private double            variableMaschinenKosten;
    private double            fixeMaschinenKosten;

    public double getNummer() {
        return nummer;
    }

    public void setNummer(final double nummer) {
        this.nummer = nummer;
    }

    public double getLohnSchicht1() {
        return lohnSchicht1;
    }

    public void setLohnSchicht1(final double lohnSchicht1) {
        this.lohnSchicht1 = lohnSchicht1;
    }

    public double getLohnSchicht2() {
        return lohnSchicht2;
    }

    public void setLohnSchicht2(final double lohnSchicht2) {
        this.lohnSchicht2 = lohnSchicht2;
    }

    public double getLohnSchicht3() {
        return lohnSchicht3;
    }

    public void setLohnSchicht3(final double lohnSchicht3) {
        this.lohnSchicht3 = lohnSchicht3;
    }

    public double getLohnUeberstunden() {
        return lohnUeberstunden;
    }

    public void setLohnUeberstunden(final double lohnUeberstunden) {
        this.lohnUeberstunden = lohnUeberstunden;
    }

    public double getVariableMaschinenKosten() {
        return variableMaschinenKosten;
    }

    public void setVariableMaschinenKosten(final double variableMaschinenKosten) {
        this.variableMaschinenKosten = variableMaschinenKosten;
    }

    public double getFixeMaschinenKosten() {
        return fixeMaschinenKosten;
    }

    public void setFixeMaschinenKosten(final double fixeMaschinenKosten) {
        this.fixeMaschinenKosten = fixeMaschinenKosten;
    }

}
