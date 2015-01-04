package de.supplyTool.ManagedBeans.disposition;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.supplyTool.ManagedBeans.ErgebnisBean;
import de.supplyTool.dao.Dao;
import de.supplyTool.domain.Bestellung;
import de.supplyTool.domain.MengenStueckliste;
import de.supplyTool.domain.MengenStuecklisteItem;
import de.supplyTool.domain.Teil;
import de.supplyTool.domain.TeilLieferdaten;
import de.supplyTool.util.ContextHelper;

/**
 * 
 * @author Tobias
 * 
 */
@ManagedBean
@ViewScoped
public class DispositionBean implements Serializable {

    private static final long                      serialVersionUID = 8535993958346608925L;
    private final Dao                              dao              = new Dao();
    private Produktionsprogramm                    produktionsProgramm;
    private ArrayList<KaufTeilDispositionErgebnis> ergebnisse       = new ArrayList<KaufTeilDispositionErgebnis>();
    private ArrayList<KaufTeilDispositionErgebnis> result           = new ArrayList<KaufTeilDispositionErgebnis>();
    private boolean                                resultBerechnet  = false;

    public DispositionBean() {

    }

    @PostConstruct
    public void init() {
        produktionsProgramm = ContextHelper
                .getManagedBean(Produktionsprogramm.class);
        final ErgebnisBean managedBean = ContextHelper
                .getManagedBean(ErgebnisBean.class);
        if (managedBean.getKaufErgebnisse() != null
                || managedBean.getKaufErgebnisse().size() != 0) {
            result = managedBean.getKaufErgebnisse();
            resultBerechnet = true;
        }
    }

    public String weiter() {
        if (resultBerechnet) {
            final ErgebnisBean managedBean = ContextHelper
                    .getManagedBean(ErgebnisBean.class);
            managedBean.setKaufErgebnisse(result);
            System.out.println("ergebnisse gesetzt");
            return "einkaufUebersicht.xhtml?faces-redirect=true";
        } else {
            return null;
        }
    }

    public Produktionsprogramm getProduktionsProgramm() {
        return produktionsProgramm;
    }

    public void setProduktionsProgramm(final Produktionsprogramm programm) {
        this.produktionsProgramm = programm;
    }

    public void einkaufProgrammBerechnen() {
        final HashMap<Integer, TeilLieferdaten> teilLieferdaten = dao
                .getTeilLieferdaten();
        final MengenStueckliste mengenStueckliste = dao.getMengenStueckliste();
        ergebnisse = new ArrayList<KaufTeilDispositionErgebnis>();
        result = new ArrayList<KaufTeilDispositionErgebnis>();
        berechnen(teilLieferdaten, mengenStueckliste);
    }

    private void berechnen(final HashMap<Integer, TeilLieferdaten> lieferDaten,
            final MengenStueckliste stueckListe) {
    	
    	
    	System.out.println("BEGINN Berechne Einkaufsprogramm");
    	System.out.println();
    	
    	
        ergebnisse = new ArrayList<KaufTeilDispositionErgebnis>();
        /**
         * Stuecklisten f�r alle 3 Produkte P1, P2, P3
         */
        final HashMap<Integer, MengenStuecklisteItem> mengenstuecklisteP1 = stueckListe.getTeileP1();
        final HashMap<Integer, MengenStuecklisteItem> mengenstuecklisteP2 = stueckListe.getTeileP2();
        final HashMap<Integer, MengenStuecklisteItem> mengenstuecklisteP3 = stueckListe.getTeileP3();

        final Set<Entry<Integer, TeilLieferdaten>> entrySet = lieferDaten.entrySet();
        final Iterator<Entry<Integer, TeilLieferdaten>> iterator = entrySet.iterator();

        while (iterator.hasNext()) {
            /**
             * Ergebnisse
             */
            int bruttoBedarfPeriode1 = 0;
            int bruttoBedarfPeriode2 = 0;
            int bruttoBedarfPeriode3 = 0;
            int bruttoBedarfPeriode4 = 0;

            final Entry<Integer, TeilLieferdaten> next = iterator.next();

            /**
             * Die TeileNummer zum finden des MengenstuecklistenItems
             */
            final Integer teilenummer = next.getKey();

            final TeilLieferdaten currentLieferDaten = next.getValue();

            final MengenStuecklisteItem mengenStuecklisteItemP1 = mengenstuecklisteP1.get(teilenummer);
            final MengenStuecklisteItem mengenStuecklisteItemP2 = mengenstuecklisteP2.get(teilenummer);
            final MengenStuecklisteItem mengenStuecklisteItemP3 = mengenstuecklisteP3.get(teilenummer);

            /**
             * Periode  1 - jetzt
             */
            if (mengenStuecklisteItemP1 != null) {
                bruttoBedarfPeriode1 += mengenStuecklisteItemP1.getMenge() * produktionsProgramm.getP1_1();
            }
            if (mengenStuecklisteItemP2 != null) {
                bruttoBedarfPeriode1 += mengenStuecklisteItemP2.getMenge()  * produktionsProgramm.getP2_1();
            }
            if (mengenStuecklisteItemP3 != null) {
                bruttoBedarfPeriode1 += mengenStuecklisteItemP3.getMenge() * produktionsProgramm.getP3_1();
            }

            /**
             * Periode 2 - n + 1
             */
            if (mengenStuecklisteItemP1 != null) {
                bruttoBedarfPeriode2 += mengenStuecklisteItemP1.getMenge()  * produktionsProgramm.getP1_2();
            }
            if (mengenStuecklisteItemP2 != null) {
                bruttoBedarfPeriode2 += mengenStuecklisteItemP2.getMenge()  * produktionsProgramm.getP2_2();
            }
            if (mengenStuecklisteItemP3 != null) {
                bruttoBedarfPeriode2 += mengenStuecklisteItemP3.getMenge()  * produktionsProgramm.getP3_2();
            }

            /**
             * Periode 3 - n +2
             */
            if (mengenStuecklisteItemP1 != null) {
                bruttoBedarfPeriode3 += mengenStuecklisteItemP1.getMenge()    * produktionsProgramm.getP1_3();
            }
            if (mengenStuecklisteItemP2 != null) {
                bruttoBedarfPeriode3 += mengenStuecklisteItemP2.getMenge() * produktionsProgramm.getP2_3();
            }
            if (mengenStuecklisteItemP3 != null) {
                bruttoBedarfPeriode3 += mengenStuecklisteItemP3.getMenge() * produktionsProgramm.getP3_3();
            }

            /**
             * Periode 4 - n + 3
             */
            if (mengenStuecklisteItemP1 != null) {
                bruttoBedarfPeriode4 += mengenStuecklisteItemP1.getMenge() * produktionsProgramm.getP1_4();
            }
            if (mengenStuecklisteItemP2 != null) {
                bruttoBedarfPeriode4 += mengenStuecklisteItemP2.getMenge() * produktionsProgramm.getP2_4();
            }
            if (mengenStuecklisteItemP3 != null) {
                bruttoBedarfPeriode4 += mengenStuecklisteItemP3.getMenge() * produktionsProgramm.getP3_4();
            }


            /**
             * Werte f�r das Ergebnis setzen
             */
            // kaufTeilDispositionErgebnis
            // .setBruttoBedarfPeriode1(bruttoBedarfPeriode1);
            // kaufTeilDispositionErgebnis
            // .setBruttoBedarfPeriode2(bruttoBedarfPeriode2);
            // kaufTeilDispositionErgebnis
            // .setBruttoBedarfPeriode3(bruttoBedarfPeriode3);
            // kaufTeilDispositionErgebnis
            // .setBruttoBedarfPeriode4(bruttoBedarfPeriode4);
            //
            // kaufTeilDispositionErgebnis.setLieferDaten(currentLieferDaten);
            //
            // kaufTeilDispositionErgebnis
            // .setMengenStuecklisteitemP1(mengenStuecklisteItemP1);
            // kaufTeilDispositionErgebnis
            // .setMengenStuecklisteitemP2(mengenStuecklisteItemP2);
            // kaufTeilDispositionErgebnis
            // .setMengenStuecklisteitemP3(mengenStuecklisteItemP3);

            /**
             * Das Teil um das es gerade geht dem ergbenis hinzuf�gen
             */
            Teil verwendetesTeil = null;

            if (mengenStuecklisteItemP1 != null)
                verwendetesTeil = mengenStuecklisteItemP1.getVerwendetesTeil();
            if (mengenStuecklisteItemP2 != null)
                verwendetesTeil = mengenStuecklisteItemP2.getVerwendetesTeil();
            if (mengenStuecklisteItemP3 != null)
                verwendetesTeil = mengenStuecklisteItemP3.getVerwendetesTeil();

            // kaufTeilDispositionErgebnis.setTeil(verwendetesTeil);

            final KaufTeilDispositionErgebnis kaufTeilDispositionErgebnis = new KaufTeilDispositionErgebnis(
                    verwendetesTeil, mengenStuecklisteItemP1,
                    mengenStuecklisteItemP2, mengenStuecklisteItemP3,
                    currentLieferDaten, bruttoBedarfPeriode1,
                    bruttoBedarfPeriode2, bruttoBedarfPeriode3,
                    bruttoBedarfPeriode4);

            /**
             * Ergebnis f�r die Liste setzen
             */
            ergebnisse.add(kaufTeilDispositionErgebnis);

            
        }
        
        System.out.println("ENDE Einkaufsprogramm wurde berechnet");
    	System.out.println();

        generateResults();
    }

    private void generateResults() {
    	
    	System.out.println("BEGINN Generiere anzuzeigende Daten");
    	System.out.println();
    	
    	
        final ArrayList<Bestellung> bestellung = dao.getBestellung(true);
        final Integer maxPeriode = dao.getMaxPeriode();
        boolean hinzufuegen = true;
        
        System.out.println("Start Resu: " + new Time(System.currentTimeMillis()));
        
        for (int i = 0; i < ergebnisse.size(); i++) {
        	
        	// Wir schauen ob die Teile die wir brauchen schon in der Vorperiode bestellt wurden. 

            for (int j = 0; j < bestellung.size(); j++) {
            	
                if (bestellung.get(j).getPeriode() == maxPeriode  && bestellung.get(j).getTeil().getNummer() == ergebnisse.get(i).getTeil().getNummer()) {
                    hinzufuegen = false;
                }
            }
            if (hinzufuegen)
                result.add(ergebnisse.get(i)); // Result ist Teilmenge von Ergebnisse

            hinzufuegen = true;
        }
        Collections.sort(result);
        resultBerechnet = true;
        
        System.out.println("ENDE  Anzuzeigende Daten wurden generiert");
    	System.out.println();
        
    }
    
   

    /**
     * Zeigt Daten auf der Seite an, wenn die Daten vorgehr berechnet wurden
     * @return
     */
    public List<KaufTeilDispositionErgebnis> getDispositionsErgebnisse() {
        if (resultBerechnet)
            return result;
        else
            return null;
    }

}
