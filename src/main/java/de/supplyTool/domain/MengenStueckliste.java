package de.supplyTool.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author Tobias
 * 
 */
public class MengenStueckliste implements Serializable {

    /**
     * 
     */
    private static final long                       serialVersionUID = -5088058327633061723L;
    private int                                     fahrrad_fk;
    private HashMap<Integer, MengenStuecklisteItem> teileP1          = new HashMap<Integer, MengenStuecklisteItem>();
    private HashMap<Integer, MengenStuecklisteItem> teileP2          = new HashMap<Integer, MengenStuecklisteItem>();
    private HashMap<Integer, MengenStuecklisteItem> teileP3          = new HashMap<Integer, MengenStuecklisteItem>();

    public int getFahrrad_fk() {
        return fahrrad_fk;
    }

    public void setFahrrad_fk(final int fahrrad_fk) {
        this.fahrrad_fk = fahrrad_fk;
    }

    public HashMap<Integer, MengenStuecklisteItem> getTeileP1() {
        return teileP1;
    }

    public void setTeileP1(final HashMap<Integer, MengenStuecklisteItem> teile) {
        this.teileP1 = teile;
    }

    public HashMap<Integer, MengenStuecklisteItem> getTeileP2() {
        return teileP2;
    }

    public void setTeileP2(final HashMap<Integer, MengenStuecklisteItem> teileP2) {
        this.teileP2 = teileP2;
    }

    public HashMap<Integer, MengenStuecklisteItem> getTeileP3() {
        return teileP3;
    }

    public void setTeileP3(final HashMap<Integer, MengenStuecklisteItem> teileP3) {
        this.teileP3 = teileP3;
    }

}
