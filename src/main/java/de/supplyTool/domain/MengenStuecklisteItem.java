package de.supplyTool.domain;

import java.io.Serializable;

public class MengenStuecklisteItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8647304527112258179L;
    private Teil              fahrrad;
    private Teil              verwendetesTeil;
    private int               menge;

    public MengenStuecklisteItem() {
    }

    public MengenStuecklisteItem(final Teil fahrrad,
            final Teil verwendetesTeil, final int menge) {
        this.menge = menge;
        this.verwendetesTeil = verwendetesTeil;
        this.fahrrad = fahrrad;
    }

    public Teil getFahrrad() {
        return fahrrad;
    }

    public void setFahrrad(final Teil fahrrad) {
        this.fahrrad = fahrrad;
    }

    public Teil getVerwendetesTeil() {
        return verwendetesTeil;
    }

    public void setVerwendetesTeil(final Teil verwendetesTeil) {
        this.verwendetesTeil = verwendetesTeil;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(final int menge) {
        this.menge = menge;
    }

}
