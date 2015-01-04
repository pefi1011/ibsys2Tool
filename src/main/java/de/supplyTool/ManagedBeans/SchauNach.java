package de.supplyTool.ManagedBeans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.supplyTool.generated.PeriodResults;
import de.supplyTool.generated2.Results;
import de.supplyTool.util.ContextHelper;

@ManagedBean
@RequestScoped
public class SchauNach {

    public void ueberpruefen() {
        final ErgebnisBean ergebnisBean = ContextHelper.getManagedBean(ErgebnisBean.class);
        
       
       // final PeriodResults xmlResult = ergebnisBean.getXmlResult();
        final Results xmlResult = ergebnisBean.getXmlResult2();     
        
        System.out.println("SchaueNach Klasse: xmlResut Abfrage:");
        System.out.println();
        
        if (xmlResult == null) {
            System.out.println("xmlResut ist null");
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Bitte die Datei Hochladen."));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("produktionsprogramm.xhtml?faces-redirect=true");
           
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("xmlResut ist nicht null");
        }
    }
}
