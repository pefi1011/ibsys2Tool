package de.supplyTool.domain;

import java.io.Serializable;
import java.math.BigInteger;

public class APHelp implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6761644902479459762L;
    BigInteger                nummer;
    BigInteger                warteschlange;
    BigInteger                bearbeitung;

    public BigInteger getNummer() {
        return nummer;
    }

    public void setNummer(final BigInteger nummer) {
        this.nummer = nummer;
    }

    public BigInteger getWarteschlange() {
        return warteschlange;
    }

    public void setWarteschlange(final BigInteger warteschlange) {
        this.warteschlange = warteschlange;
    }

    public BigInteger getBearbeitung() {
        return bearbeitung;
    }

    public void setBearbeitung(final BigInteger bearbeitung) {
        this.bearbeitung = bearbeitung;
    }

    @Override
    public String toString() {
        return "APHelp [nummer=" + nummer + ", warteschlange=" + warteschlange
                + ", bearbeitung=" + bearbeitung + "]";
    }

}
