package de.supplyTool.domain;

import java.io.Serializable;

/**
 * Tabelle "bestelung"
 * 
 * @author Tobias,Thomas
 * 
 */
public class Bestellung implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4230614940684073463L;
    private int               periode;
    private BestellTyp        bestellTyp;
    private int               menge;
    private Teil              teil;
    private boolean           eingetroffen;

    public Bestellung() {

    }

    public Bestellung(final int periode, final BestellTyp bestellTyp,
            final int menge, final Teil teil, final boolean eingetroffen) {
        this.periode = periode;
        this.bestellTyp = bestellTyp;
        this.menge = menge;
        this.teil = teil;
        this.eingetroffen = eingetroffen;
    }

    public boolean isEingetroffen() {
        return eingetroffen;
    }

    public void setEingetroffen(final boolean eingetroffen) {
        this.eingetroffen = eingetroffen;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(final int periode) {
        this.periode = periode;
    }

    public BestellTyp getBestellTyp() {
        return bestellTyp;
    }

    public void setBestellTyp(final BestellTyp bestellTyp) {
        this.bestellTyp = bestellTyp;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(final int menge) {
        this.menge = menge;
    }

    public Teil getTeil() {
        return teil;
    }

    public void setTeil(final Teil teil) {
        this.teil = teil;
    }

}
