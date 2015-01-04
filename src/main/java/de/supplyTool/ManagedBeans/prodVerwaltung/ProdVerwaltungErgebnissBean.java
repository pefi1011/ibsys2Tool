package de.supplyTool.ManagedBeans.prodVerwaltung;

import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.supplyTool.dao.Dao;
import de.supplyTool.domain.APTeil;

@ManagedBean
@ApplicationScoped
public class ProdVerwaltungErgebnissBean {

    ArrayList<APTeil> teile = new ArrayList<APTeil>();
    Boolean           first = true;
    Integer           split = 100;

    public void setSplit(final Integer i) {
        split = i;
        System.out.println("set " + i);
    }

    public Integer getSplit() {
        return split;
    }

    public void init() {
        System.out.println("init");
        final Dao dao = new Dao();

        teile = dao.getDispoHelpErgebnissGut();

    }

    public ArrayList<APTeil> getTeile() {
        System.out.println("getteile");
        if (first) {
            init();
            doLogic();
        }
        first = false;

        return teile;
    }

    public void reload() {
        System.out.println("reload");
        init();
        doLogic();
    }

    public void doLogic() {
        System.out.println("logic");
        System.out.println("split " + split);
        final Integer altesize = teile.size();
        for (int n = 0; n < altesize; n++) {

            if (teile.get(n).getAnzahl() > split + 1) {
                final APTeil tmp = new APTeil();
                tmp.setNummer(teile.get(n).getNummer());
                tmp.setAnzahl(teile.get(n).getAnzahl() - split);
                teile.get(n).setAnzahl(split);
                teile.add(tmp);

            }
        }

        for (int n = 0; n < teile.size(); n++) {
            System.out.println("teile " + teile.get(n));
        }
    }

    public void setTeile(final ArrayList<APTeil> t) {
        System.out.println("setteile");
        teile = t;
    }
}
