package de.supplyTool.ManagedBeans;

import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.supplyTool.ManagedBeans.disposition.KaufTeilDispositionErgebnis;
import de.supplyTool.ManagedBeans.disposition.Produktionsprogramm;
import de.supplyTool.dao.Dao;
import de.supplyTool.domain.APArbeitsplatz;
import de.supplyTool.domain.APTeil;
import de.supplyTool.domain.BestellTyp;
import de.supplyTool.util.ContextHelper;

@ManagedBean
@ApplicationScoped
public class Result {

    private String result;

    public void doLogic() {

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

    }

    public String getResult() {
        doLogic();
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
