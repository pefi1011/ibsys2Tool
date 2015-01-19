package de.supplyTool.ManagedBeans.disposition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import de.supplyTool.domain.BestellTyp;
import de.supplyTool.domain.MengenStuecklisteItem;
import de.supplyTool.domain.Teil;
import de.supplyTool.domain.TeilLieferdaten;
import de.supplyTool.util.ContextHelper;

/**
 * 
 * @author Tobias
 * 
 */
public class KaufTeilDispositionErgebnis implements Serializable,
        Comparable<KaufTeilDispositionErgebnis> {

    private static final long     serialVersionUID         = -3992168390527886551L;

    private Teil                  teil;
    private TeilLieferdaten       lieferDaten;

    private MengenStuecklisteItem mengenStuecklisteitemP1;
    private MengenStuecklisteItem mengenStuecklisteitemP2;
    private MengenStuecklisteItem mengenStuecklisteitemP3;

    private int                   bruttoBedarfPeriode1     = 0;
    private int                   bruttoBedarfPeriode2     = 0;
    private int                   bruttoBedarfPeriode3     = 0;
    private int                   bruttoBedarfPeriode4     = 0;

    private double                maxLieferdauerPeriode    = -1;
    private int                   maxLieferdauerTage       = -1;

    private BestellTyp            bestellTyp               = BestellTyp.N;
    private double                materialGehtAusAmTag     = -999.0; //19.9;
    private int                   materialGehtAusInPeriode = -999;// 4;
    private int                   bestellPeriode;
    
    private boolean 			  gehtNIEAus			   = false;

    DecimalFormat                 df                       = new DecimalFormat(
                                                                   "#.##");

    EinkaufKonfiguration          config;

    int[]                         bruttoBedarfe            = new int[4];
    int                           bestellmenge             = 0;

    public KaufTeilDispositionErgebnis(final Teil teil,
            final MengenStuecklisteItem mengenStuecklisteitemP1,
            final MengenStuecklisteItem mengenStuecklisteitemP2,
            final MengenStuecklisteItem mengenStuecklisteitemP3,
            final TeilLieferdaten lieferdaten, final int bruttoBedarfPeriode1,
            final int bruttoBedarfPeriode2, final int bruttoBedarfPeriode3,
            final int bruttoBedarfPeriode4) {

        config = ContextHelper.getManagedBean(EinkaufKonfiguration.class);

        this.teil = teil;
        this.mengenStuecklisteitemP1 = mengenStuecklisteitemP1;
        this.mengenStuecklisteitemP2 = mengenStuecklisteitemP2;
        this.mengenStuecklisteitemP3 = mengenStuecklisteitemP3;

        this.bruttoBedarfPeriode1 = bruttoBedarfPeriode1;
        bruttoBedarfe[0] = bruttoBedarfPeriode1;
        this.bruttoBedarfPeriode2 = bruttoBedarfPeriode2;
        bruttoBedarfe[1] = bruttoBedarfPeriode2;
        this.bruttoBedarfPeriode3 = bruttoBedarfPeriode3;
        bruttoBedarfe[2] = bruttoBedarfPeriode3;
        this.bruttoBedarfPeriode4 = bruttoBedarfPeriode4;
        bruttoBedarfe[3] = bruttoBedarfPeriode4;

        this.lieferDaten = lieferdaten;

        /**
         * Reihenfolge wichtig
         */
        System.out.println();
        System.out.println("RUFE MaterialGehtAusBerechnen();");
        MaterialGehtAusBerechnen();
        // bestellPeriodeBerechnen();
        
        System.out.println("RUFE bestellVorschlagBerechnen();");
        bestellVorschlagBerechnen();
    }

    public List<SelectItem> getSelectItems() {
        final ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        items.add(new SelectItem(BestellTyp.F, "Fast"));
        items.add(new SelectItem(BestellTyp.N, "Normal"));
        return items;
    }

    private void bestellVorschlagBerechnen() {
        final double gesamtAnzahltage = 25d;

        final double prozentTag = materialGehtAusAmTag / gesamtAnzahltage;
        final double gesamtBedarf = bruttoBedarfPeriode1 + bruttoBedarfPeriode2
                + bruttoBedarfPeriode3 + bruttoBedarfPeriode4;

        final double bedarfBisMaterialAusGeht = prozentTag * gesamtBedarf;
        final double restBedarf = gesamtBedarf - bedarfBisMaterialAusGeht;

        final int gesamtLieferdauerTage = getMaxLieferdauerTage();
        final int wiederbeschaffungszeitTage = lieferDaten
                .getWiederbeschaffungszeitTage();
        if (teil.getNummer() == 48)
            System.out.println("47");

        /**
         * BestellTyp berechnen
         */

        /**
         * Benutzen um die Bestellmenge anzupassen damit sie erhöht wird damit
         * so lange produziert werden kann bis nur noch die konfigurierte
         * abweichung übrigbleibt. Beispiel (Abweichung 3 Tage, Konfigurierte
         * Abweichung 1 Tag) = Material für 2 Tage mehr bestellen damit
         * Abweichung berücksichtigt ist
         */
        final int abweichung = (gesamtLieferdauerTage - wiederbeschaffungszeitTage);

        final double dauerAVG = (Double.valueOf(abweichung) / 2d)
                + wiederbeschaffungszeitTage;

        if (wiederbeschaffungszeitTage >= materialGehtAusAmTag
                || dauerAVG >= materialGehtAusAmTag || gesamtLieferdauerTage >= materialGehtAusAmTag ) {
            bestellTyp = BestellTyp.F;
        } else {
            bestellTyp = BestellTyp.N;
        }

        /**
         * Bestellmenge
         */
        final double diskontmengeAbweichung = config.getDiskontmengeAbweichung();
        final int sicherheitsbestand = config.getSicherheitsbestand();
        final double nachkommastellenMaterialGehtAusAmTag = materialGehtAusAmTag - Math.floor(materialGehtAusAmTag);
        
        System.out.print("materialGehtAusInPeriode: "+ materialGehtAusInPeriode);
        System.out.print("\n");
        
        	
    	// Material muss gleich bestellet werden
    	if (materialGehtAusInPeriode == 0){
    		
    		// TODO Bestellen vom Market
    		bestellTyp = BestellTyp.F;
    		bestellmenge = lieferDaten.getDiskontMenge();
    		System.out.println("GEHT GLEICH AUS!!!!");
    		System.out.println("diskontmenge genommen");
    		
    	} else {
    		
    		double restBedarfVomTag = (bruttoBedarfe[materialGehtAusInPeriode - 1] / 5) * nachkommastellenMaterialGehtAusAmTag;
        	restBedarfVomTag = Math.ceil(restBedarfVomTag);
        	bestellmenge += restBedarfVomTag;
        	
        	if ((materialGehtAusAmTag / dauerAVG) < 2d) {
        		int aktuellerTag = (int) Math.ceil(materialGehtAusAmTag);
        		final double aktuellePeriode = materialGehtAusInPeriode;
        		for (int i = 1; i <= dauerAVG; i++) {
        			aktuellerTag += 1;
        			
        			if ((aktuellerTag / 5) <= 1) {
        				bestellmenge += bruttoBedarfe[0] / 5;
        			} else if ((aktuellerTag / 5) <= 2) {
        				bestellmenge += bruttoBedarfe[1] / 5;
        			} else if ((aktuellerTag / 5) <= 3) {
        				bestellmenge += bruttoBedarfe[2] / 5;
        			} else if ((aktuellerTag / 5) <= 4) {
        				bestellmenge += bruttoBedarfe[3] / 5;
        			} else if ((aktuellerTag / 5) > 4) {
        				bestellmenge += (bruttoBedarfe[0] + bruttoBedarfe[1]
        						+ bruttoBedarfe[2] + bruttoBedarfe[3]) / 20;
        			}
        		}
        		
        		/**
        		 * diskontmenge berechnen
        		 */
        		final int diskontMenge = lieferDaten.getDiskontMenge();
        		final double wert = 1d - (bestellmenge / Double
        				.valueOf(diskontMenge));
        		
        		final double sicherheitsbestandProzent = (Double
        				.valueOf(sicherheitsbestand) / 100d) + 1d;
        		bestellmenge *= sicherheitsbestandProzent;
        		
        		if (bestellmenge < diskontMenge
        				&& wert <= (diskontmengeAbweichung / 100d)) {
        			bestellmenge = diskontMenge;
        			System.out.println("diskontmenge genommen");
        		}
        		
        	} else {
        		
        		// Man braucht nicht bestellen
        		bestellmenge = -1;
        	}
        	
    	}
    	
    }

    public BestellTyp getBestellTyp() {
        return bestellTyp;
    }

    public void setBestellTyp(final BestellTyp bestellTyp) {
        this.bestellTyp = bestellTyp;
    }

    public int getBestellmenge() {
        return bestellmenge;
    }

    public void setBestellmenge(final int bestellmenge) {
        this.bestellmenge = bestellmenge;
    }

    private void MaterialGehtAusBerechnen() {

        /**
         * Nur f�r die Schleife zum besser iterieren
         */
        final int[] bruttoeBedarfe = new int[4];
        bruttoeBedarfe[0] = bruttoBedarfPeriode1;
        bruttoeBedarfe[1] = bruttoBedarfPeriode2;
        bruttoeBedarfe[2] = bruttoBedarfPeriode3;
        bruttoeBedarfe[3] = bruttoBedarfPeriode4;
        
        System.out.println("EINS bedarfe" + bruttoeBedarfe[0] + " " + bruttoeBedarfe[1] + " " + bruttoeBedarfe[2] + " " + bruttoeBedarfe[3]);

        /**
         * Der gesamtBedarf in der akutellen periode (i in der schleife. i = 0
         * ist Periode 1)
         */
        int gesamtBedarf = 0;
        for (int i = 0; i < 4; i++) {
            gesamtBedarf += bruttoeBedarfe[i];
            System.out.println("ZWEI gesamt bedarf " + gesamtBedarf);


            if (gesamtBedarf >= lieferDaten.getLagerMenge()) {
                System.out.println("DREI in if" + gesamtBedarf);

            	
                final int differenzGesamtBedarf__LagerMenge = gesamtBedarf
                        - lieferDaten.getLagerMenge();

                final double prozent = Double.valueOf(1)
                        - (Double.valueOf(differenzGesamtBedarf__LagerMenge) / Double
                                .valueOf(bruttoeBedarfe[i]));

                double materialGehtAusAmTag = prozent * 5;
                
                System.out.println("BEVOR SET MAT: " + String.valueOf(materialGehtAusAmTag));
                

                if (i > 0)
                    System.out.println("VIEW in secondIF if" + gesamtBedarf);

                    materialGehtAusAmTag += i * 5;
                    setMaterialGehtAusAmTag(materialGehtAusAmTag);
                
                    System.out.println("Mat geht aus: " + String.valueOf(materialGehtAusAmTag));
                break;
            }
            
            System.out.println("Material geht NICHT aus!!!");
            gehtNIEAus = true;
            materialGehtAusAmTag = 25; 
            materialGehtAusInPeriode = 4;
        }

        
    }

    public Teil getTeil() {
        return teil;
    }

    public void setTeil(final Teil teil) {
        this.teil = teil;
    }

    public int getBruttoBedarfPeriode1() {
        return bruttoBedarfPeriode1;
    }

    public void setBruttoBedarfPeriode1(final int bruttoBedarfPeriode1) {
        this.bruttoBedarfPeriode1 = bruttoBedarfPeriode1;
    }

    public int getBruttoBedarfPeriode2() {
        return bruttoBedarfPeriode2;
    }

    public void setBruttoBedarfPeriode2(final int bruttoBedarfPeriode2) {
        this.bruttoBedarfPeriode2 = bruttoBedarfPeriode2;
    }

    public int getBruttoBedarfPeriode3() {
        return bruttoBedarfPeriode3;
    }

    public void setBruttoBedarfPeriode3(final int bruttoBedarfPeriode3) {
        this.bruttoBedarfPeriode3 = bruttoBedarfPeriode3;
    }

    public int getBruttoBedarfPeriode4() {
        return bruttoBedarfPeriode4;
    }

    public void setBruttoBedarfPeriode4(final int bruttoBedarfPeriode4) {
        this.bruttoBedarfPeriode4 = bruttoBedarfPeriode4;
    }

    public TeilLieferdaten getLieferDaten() {
        return lieferDaten;
    }

    public void setLieferDaten(final TeilLieferdaten lieferDaten) {
        this.lieferDaten = lieferDaten;
    }

    public MengenStuecklisteItem getMengenStuecklisteitemP1() {
        return mengenStuecklisteitemP1;
    }

    public void setMengenStuecklisteitemP1(
            final MengenStuecklisteItem mengenStuecklisteitemP1) {
        this.mengenStuecklisteitemP1 = mengenStuecklisteitemP1;
    }

    public MengenStuecklisteItem getMengenStuecklisteitemP2() {
        return mengenStuecklisteitemP2;
    }

    public void setMengenStuecklisteitemP2(
            final MengenStuecklisteItem mengenStuecklisteitemP2) {
        this.mengenStuecklisteitemP2 = mengenStuecklisteitemP2;
    }

    public MengenStuecklisteItem getMengenStuecklisteitemP3() {
        return mengenStuecklisteitemP3;
    }

    public void setMengenStuecklisteitemP3(
            final MengenStuecklisteItem mengenStuecklisteitemP3) {
        this.mengenStuecklisteitemP3 = mengenStuecklisteitemP3;
    }

    public double getMaxLieferdauerPeriode() {
        if (maxLieferdauerPeriode < 0 && lieferDaten == null) {
            throw new RuntimeException(
                    "<<<<<<<<<<<<<< Setz die Lieferdauer. Die darf nicht null sein!>>>>>>>>>>>>>>>>>>>");
        }

        if (maxLieferdauerPeriode < 0) {
            maxLieferdauerPeriode = lieferDaten
                    .getWiederbeschaffungszeitPeriode()
                    + lieferDaten.getAbweichungPeriode();
            maxLieferdauerPeriode = KaufTeilDispositionErgebnis.round(
                    maxLieferdauerPeriode, 1);
        }
        return maxLieferdauerPeriode;
    }

    public int getMaxLieferdauerTage() {
        if (maxLieferdauerTage < 0 && lieferDaten == null) {
            throw new RuntimeException(
                    "<<<<<<<<<<<<<< Setz die Lieferdauer. Die darf nicht null sein!>>>>>>>>>>>>>>>>>>>");
        }
        if (maxLieferdauerTage < 0) {
            maxLieferdauerTage = lieferDaten.getWiederbeschaffungszeitTage()
                    + lieferDaten.getAbweichungTage();
        }
        return maxLieferdauerTage;
    }

    public void setMaxLieferdauerTage(final int tage) {
        maxLieferdauerTage = tage;
    }

    public void setMaxLieferdauerPeriode(final int maxLieferdauerPeriode) {
        this.maxLieferdauerPeriode = maxLieferdauerPeriode;
    }

    public BestellTyp getVorgeschlagenerBestellTye() {
        return bestellTyp;
    }

    public void setVorgeschlagenerBestellTye(
            final BestellTyp vorgeschlagenerBestellTye) {
        this.bestellTyp = vorgeschlagenerBestellTye;
    }

    public double getMaterialGehtAusAmTag() {
        // return Double.valueOf(df.format(materialGehtAusAmTag).replace(",",
        // "."));
        return KaufTeilDispositionErgebnis.truncate(materialGehtAusAmTag, 2);
    }

    public static double truncate(double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        final long factor = (long) Math.pow(10, places);
        value = value * factor;
        final long tmp = (long) value;
        return (double) tmp / factor;
    }

    /**
     * Setzt implizit materialGetAusInPeriode!!!!!!!!!!!
     * 
     * @param materialGehtAus
     */
    public void setMaterialGehtAusAmTag(final double materialGehtAus) {
        this.materialGehtAusAmTag = materialGehtAus;
        setMaterialGehtAusInPeriode((int) Math.ceil(materialGehtAusAmTag / 5));
    }

    /**
     * Die Periode in der Bestellt werden soll
     * 
     * @return
     */
    public int getBestellPeriode() {
        return bestellPeriode;
    }

    /**
     * Die periode in der bestellt werden soll
     * 
     * @param bestellPeriode
     */
    public void setBestellPeriode(final int bestellPeriode) {
        this.bestellPeriode = bestellPeriode;
    }

    public BestellTyp getVorgeschlagenerBestellTyp() {
        return bestellTyp;
    }

    public void setVorgeschlagenerBestellTyp(
            final BestellTyp vorgeschlagenerBestellTyp) {
        this.bestellTyp = vorgeschlagenerBestellTyp;
    }

    public int getMaterialGehtAusInPeriode() {
        return materialGehtAusInPeriode;
    }

    public void setMaterialGehtAusInPeriode(final int materialGehtAusInPeriode) {
        this.materialGehtAusInPeriode = materialGehtAusInPeriode;
    }

    public static double round(final double d, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public int compareTo(final KaufTeilDispositionErgebnis o) {
        if (o.getTeil().getNummer() == this.teil.getNummer())
            return 0;

        if (this.teil.getNummer() < o.getTeil().getNummer())
            return -1;

        if (this.teil.getNummer() > o.getTeil().getNummer())
            return 1;

        return 0;
    }

}
