package de.supplyTool.ManagedBeans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import de.supplyTool.ManagedBeans.disposition.KaufTeilDispositionErgebnis;
import de.supplyTool.ManagedBeans.disposition.Produktionsprogramm;
import de.supplyTool.ManagedBeans.prodVerwaltung.ProdVerwaltungBean;
import de.supplyTool.dao.Dao;
import de.supplyTool.domain.APArbeitsplatz;
import de.supplyTool.domain.APTeil;
import de.supplyTool.domain.BestellTyp;
import de.supplyTool.util.ContextHelper;

@ManagedBean
@ApplicationScoped
public class Result {

    private String result;

    
    public String createXmlFileContent() {

        StringBuilder sb = new StringBuilder();

        Integer eins = ContextHelper.getManagedBean(Produktionsprogramm.class).getP1_1();
        Integer zwei = ContextHelper.getManagedBean(Produktionsprogramm.class).getP2_1();
        Integer drei = ContextHelper.getManagedBean(Produktionsprogramm.class).getP3_1();

        sb.append("<input> \n");
        sb.append("<qualitycontrol type=\"no\" losequantity=\"0\" delay=\"0\" /> \n");
        
        sb.append("<sellwish> \n");
        
        	sb.append("<item article=\"1\" quantity=\"" + eins + "\" />  \n");
        	sb.append("<item article=\"2\" quantity=\"" + zwei + "\" />  \n");
        	sb.append("<item article=\"3\" quantity=\"" + drei + "\" />  \n");
        	
    	sb.append("</sellwish> \n");
    	
    	
    	sb.append("<selldirect> \n");
    		sb.append("<item article=\"1\" quantity=\"0\" price=\"0.0\" penalty=\"0.0\" /> \n");
    		sb.append("<item article=\"2\" quantity=\"0\" price=\"0.0\" penalty=\"0.0\" /> \n");
    		sb.append("<item article=\"3\" quantity=\"0\" price=\"0.0\" penalty=\"0.0\" /> \n");
		sb.append("</selldirect> \n");
        	
		
		
		
		sb.append("<orderlist> \n");

        Dao dao = new Dao();
        ArrayList<APTeil> dispoHelpErgebnissGut = dao.getDispoHelpErgebnissGut();

        ArrayList<KaufTeilDispositionErgebnis> kaufErgebnisse = ContextHelper.getManagedBean(ErgebnisBean.class).getKaufErgebnisse();

        for (int n = 0; n < kaufErgebnisse.size(); n++) {
            if (kaufErgebnisse.get(n).getBestellmenge() > 0) {
                sb.append("<order article= \"");
                sb.append(String.valueOf(kaufErgebnisse.get(n).getTeil().getNummer()) + "\" ");
                sb.append("quantity=\""
                        + kaufErgebnisse.get(n).getBestellmenge()
                        + "\" ");

                String typ = "";
                if (kaufErgebnisse.get(n).getBestellTyp().equals(BestellTyp.F))

                {
                    typ = "modus = \"4\" /> \n";
                } else {
                	typ = "modus = \"5\" /> \n";
                }
                sb.append(typ);
            }
       }

    	sb.append("</orderlist> \n");


        sb.append("<productionlist> \n");
        

        for (int n1 = 0; n1 < dispoHelpErgebnissGut.size(); n1++) {
            sb.append("<production article=\"" );
            sb.append(dispoHelpErgebnissGut.get(n1).getNummer()
                    + "\" quantity=\"" );
            sb.append(dispoHelpErgebnissGut.get(n1).getAnzahl()
                    + "\" />  \n");
        }

        sb.append("</productionlist> \n");
        
        
        
        sb.append("<workingtimelist> \n");

        ArrayList<APArbeitsplatz> arbeitsplaetze = ContextHelper.getManagedBean(ErgebnisBean.class).getArbeitsplaetze();

        for (int n1 = 0; n1 < arbeitsplaetze.size(); n1++) {
            sb.append("<workingtime station=\"");
            sb.append(arbeitsplaetze.get(n1).getNummer()
                    + "\" shift=\"");
            sb.append(arbeitsplaetze.get(n1).getSchicht()
                    + "\" overtime=\"");
            sb.append(arbeitsplaetze.get(n1).getUeberstundenGut() / 5
                    + "\" /> \n");

        }
        sb.append("</workingtimelist> \n");
        
        sb.append("</input>");
        
        result = sb.toString();
        System.out.println("result \n" + result);
        
        return result;

    }

    
    public String doLogic() {

        StringBuilder sb = new StringBuilder();

        Integer eins = ContextHelper.getManagedBean(Produktionsprogramm.class)
                .getP1_1();
        Integer zwei = ContextHelper.getManagedBean(Produktionsprogramm.class)
                .getP2_1();
        Integer drei = ContextHelper.getManagedBean(Produktionsprogramm.class)
                .getP3_1();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
        sb.append("<PeriodInput xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"> \n");
        sb.append("SalesWishes> \n");

        sb.append("<SalesWish>");
        sb.append("<SalesItemInternalNumber>1</SalesItemInternalNumber> \n");
        sb.append("<SaleQuantity>" + eins + "</SaleQuantity> \n");
        sb.append("<DirectSaleQuantity>0</DirectSaleQuantity> \n");
        sb.append(" <DirectSalePrice>0.0</DirectSalePrice> \n");
        sb.append("<DirectSalePenalty>0.0</DirectSalePenalty> \n");
        sb.append(" </SalesWish> \n");

        sb.append("<SalesWish>");
        sb.append("<SalesItemInternalNumber>2</SalesItemInternalNumber> \n");
        sb.append("<SaleQuantity>" + zwei + "</SaleQuantity> \n");
        sb.append("<DirectSaleQuantity>0</DirectSaleQuantity> \n");
        sb.append(" <DirectSalePrice>0.0</DirectSalePrice> \n");
        sb.append("<DirectSalePenalty>0.0</DirectSalePenalty> \n");
        sb.append(" </SalesWish> \n");

        sb.append("<SalesWish>");
        sb.append("<SalesItemInternalNumber>3</SalesItemInternalNumber> \n");
        sb.append("<SaleQuantity>" + drei + "</SaleQuantity> \n");
        sb.append("<DirectSaleQuantity>0</DirectSaleQuantity> \n");
        sb.append(" <DirectSalePrice>0.0</DirectSalePrice> \n");
        sb.append("<DirectSalePenalty>0.0</DirectSalePenalty> \n");
        sb.append(" </SalesWish> \n");

        sb.append("</SalesWishes> \n");

        sb.append("<ItemOrders> \n");

        Dao dao = new Dao();
        ArrayList<APTeil> dispoHelpErgebnissGut = dao
                .getDispoHelpErgebnissGut();

        ArrayList<KaufTeilDispositionErgebnis> kaufErgebnisse = ContextHelper
                .getManagedBean(ErgebnisBean.class).getKaufErgebnisse();

        for (int n = 0; n < kaufErgebnisse.size(); n++) {
            if (kaufErgebnisse.get(n).getBestellmenge() > 0) {
                sb.append("<ItemOrder> \n");
                sb.append("<ItemInternalNumber>23</ItemInternalNumber> \n");
                sb.append("<Quantity>"
                        + kaufErgebnisse.get(n).getBestellmenge()
                        + "</Quantity> \n");

                String typ = "";
                if (kaufErgebnisse.get(n).getBestellTyp().equals(BestellTyp.F))

                {
                    typ = "Fast";
                } else {
                    typ = "Normal";
                }
                sb.append("<Supplier>" + typ + "Normal</Supplier> \n");
                sb.append("</ItemOrder> \n");
            }
        }

        sb.append("</ItemOrders> \n");

        sb.append("<ProductionOrders> \n");

        for (int n = 0; n < dispoHelpErgebnissGut.size(); n++) {
            sb.append("<ProductionOrder> \n");
            sb.append("<ItemInternalNumber>"
                    + dispoHelpErgebnissGut.get(n).getNummer()
                    + "</ItemInternalNumber> \n");
            sb.append("<Quantity>" + dispoHelpErgebnissGut.get(n).getAnzahl()
                    + "</Quantity> \n");
        }

        sb.append("</ProductionOrders> \n");
        sb.append("<WorkplaceShifts> \n");

        ArrayList<APArbeitsplatz> arbeitsplaetze = ContextHelper
                .getManagedBean(ErgebnisBean.class).getArbeitsplaetze();

        for (int n = 0; n < arbeitsplaetze.size(); n++) {
            sb.append(" <WorkplaceShift> \n");
            sb.append("<WorkplaceName>" + arbeitsplaetze.get(n).getNummer()
                    + "</WorkplaceName> \n");
            sb.append("<Shifts>" + arbeitsplaetze.get(n).getSchicht()
                    + "</Shifts> \n");
            sb.append("<OvertimeInMinutes>"
                    + arbeitsplaetze.get(n).getUeberstundenGut() / 5
                    + "</OvertimeInMinutes> \n");

            sb.append("</WorkplaceShift> \n");
        }
        sb.append("</WorkplaceShifts> \n");
        sb.append("  <MarketplaceTransactions /> \n");
        sb.append("<IsQualityControlEnabled>false</IsQualityControlEnabled> \n");
        sb.append("</PeriodInput> \n");

        result = sb.toString();
        System.out.println("result " + result);
        
        return result;

    }
    
    public void downloadFile() {
    	
    	String fileContent = createXmlFileContent();
    	
    	Integer periodNumber = ContextHelper.getManagedBean(ErgebnisBean.class).getXmlResult2().getPeriod() + 1;
    	
    	File xmlOutputFile = null;
    	
	    try {
	    	//create a temp file
			 xmlOutputFile = File.createTempFile("xmlOutput", "xml");
			
			//write it
    	    BufferedWriter bw = new BufferedWriter(new FileWriter(xmlOutputFile));
    	    bw.write(fileContent);
    	    bw.close();
 
    	    System.out.println("Done");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

        String downloadFileName = "attachment;filename=PDP_xmlOutput_InputForPeriod_" + periodNumber + ".xml";
        
        response.setHeader("Content-Disposition", downloadFileName);  
        response.setContentLength((int) xmlOutputFile.length());  
        ServletOutputStream out = null;  
        try {  
            FileInputStream input = new FileInputStream(xmlOutputFile);  
            byte[] buffer = new byte[1024];  
            out = response.getOutputStream();  
            int i = 0;  
            while ((i = input.read(buffer)) != -1) {  
                out.write(buffer);  
                out.flush();  
            }  
            FacesContext.getCurrentInstance().getResponseComplete();  
        } catch (IOException err) {  
            err.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
            } catch (IOException err) {  
                err.printStackTrace();  
            }  
        }  
    	
    }

    public String getResult() {
        doLogic();
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
