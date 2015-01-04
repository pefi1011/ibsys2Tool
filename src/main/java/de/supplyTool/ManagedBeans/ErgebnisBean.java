package de.supplyTool.ManagedBeans;

import de.supplyTool.generated2.Results;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.supplyTool.ManagedBeans.disposition.KaufTeilDispositionErgebnis;
import de.supplyTool.domain.APArbeitsplatz;
import de.supplyTool.generated.PeriodResults;
import de.supplyTool.generated2.Results;

@ManagedBean
@ApplicationScoped
public class ErgebnisBean implements Serializable {

    /**
     * 
     */
    private static final long                      serialVersionUID = 1356383244954406879L;
    private ArrayList<KaufTeilDispositionErgebnis> kaufErgebnisse   = new ArrayList<KaufTeilDispositionErgebnis>();
    private static PeriodResults                   xmlResult        = null;
    private Results			                       xmlResult2      = null;
    private ArrayList<APArbeitsplatz>              arbeitsplaetze   = new ArrayList<APArbeitsplatz>();

    public ArrayList<KaufTeilDispositionErgebnis> getKaufErgebnisse() {
        return kaufErgebnisse;
    }

    public void setKaufErgebnisse(
            final ArrayList<KaufTeilDispositionErgebnis> kaufErgebnisse) {
        this.kaufErgebnisse = kaufErgebnisse;
    }

    public PeriodResults getXmlResult() {
        return xmlResult;
    }

    public void setXmlResult(final PeriodResults xmlResult) {
        this.xmlResult = xmlResult;
    }
    
    public void setXmlResult2(final Results xmlResult2) {
        this.xmlResult2 = xmlResult2;
    }
    
    public Results getXmlResult2() {
        return xmlResult2;
    }

    public ArrayList<APArbeitsplatz> getArbeitsplaetze() {
        return arbeitsplaetze;
    }

    public void setArbeitsplaetze(ArrayList<APArbeitsplatz> arbeitsplaetze) {
        this.arbeitsplaetze = arbeitsplaetze;
    }

}
