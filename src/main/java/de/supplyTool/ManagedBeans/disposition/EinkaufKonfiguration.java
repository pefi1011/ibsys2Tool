package de.supplyTool.ManagedBeans.disposition;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EinkaufKonfiguration implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID         = -8752698074052240692L;
    private double            diskontmengeAbweichung   = 10;
    private int               sicherheitsbestand       = 33;
    private boolean           genauNachBedarfBerechnen = false;

    public double getDiskontmengeAbweichung() {
        return diskontmengeAbweichung;
    }

    public void setDiskontmengeAbweichung(final double diskontmengeAbweichung) {
        this.diskontmengeAbweichung = diskontmengeAbweichung;
    }

    public int getSicherheitsbestand() {
        return sicherheitsbestand;
    }

    public void setSicherheitsbestand(final int sicherheitsbestand) {
        this.sicherheitsbestand = sicherheitsbestand;
    }

    public boolean isGenauNachBedarfBerechnen() {
        return genauNachBedarfBerechnen;
    }

    public void setGenauNachBedarfBerechnen(
            final boolean genauNachBedarfBerechnen) {
        this.genauNachBedarfBerechnen = genauNachBedarfBerechnen;
    }

}
