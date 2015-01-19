package de.supplyTool.ManagedBeans.AP;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import de.supplyTool.ManagedBeans.ErgebnisBean;
import de.supplyTool.dao.Dao;
import de.supplyTool.domain.APArbeitsplatz;
import de.supplyTool.domain.APHelp;
import de.supplyTool.domain.APOption;
import de.supplyTool.domain.APTeil;
import de.supplyTool.generated.Entry;
import de.supplyTool.generated.PeriodResults;
import de.supplyTool.generated2.Results;
import de.supplyTool.generated2.Results.Ordersinwork.Workplace;
import de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace.Waitinglist;
import de.supplyTool.util.ContextHelper;

@ManagedBean
@SessionScoped
public class APBean {

	/**
	 * @author diva1012
	 *
	 *	Das ist eine Hilfsklasse um die Struktur der alten XML Datei zu simulieren 
	 *
	 * 	So haben wir alle WaitingListEntries in einer gemeinsamen Liste.
	 */
	public class Paar {
		private BigInteger arbeitsplatz;
		private Waitinglist waitinListEntry;
		
		public Paar(BigInteger arbeitsplatz, Waitinglist waitinListEntry) {
			super();
			this.arbeitsplatz = arbeitsplatz;
			this.waitinListEntry = waitinListEntry;
		}
		
		public Paar(Integer arbeitsplatz, Waitinglist waitinListEntry) {
			super();
			this.arbeitsplatz = BigInteger.valueOf(arbeitsplatz);
			this.waitinListEntry = waitinListEntry;
		}

		public BigInteger getArbeitsplatz() {
			return arbeitsplatz;
		}

		public void setArbeitsplatz(BigInteger arbeitsplatz) {
			this.arbeitsplatz = arbeitsplatz;
		}

		public Waitinglist getWaitinListEntry() {
			return waitinListEntry;
		}

		public void setWaitinListEntry(Waitinglist waitinListEntry) {
			this.waitinListEntry = waitinListEntry;
		} 
	}
	
    List<Entry>                     feld;
    ArrayList<APArbeitsplatz>       arbeitsplaetze = null;
    APOption                        one;
    APOption                        two;
    APOption                        three;
    Boolean                         first          = true;

    final ArrayList<APArbeitsplatz> arbeit         = new ArrayList<APArbeitsplatz>();

    public APBean() {
    }

    public void setEntry(final List<Entry> feld) {
        this.feld = feld;
    }

    public void init() {
    	
        one = new APOption();
        one.setId("1");
        one.setOptionOne("2400");
        one.setOptionTwo("2400");
        two = new APOption();
        two.setId("2");
        two.setOptionOne("4000");
        two.setOptionTwo("4800");
        three = new APOption();
        three.setId("3");
        three.setOptionOne("6400");
        three.setOptionTwo("7200");

        arbeit.add(new APArbeitsplatz(null, 1, null, null, 60, null, null, 1.0));
        final ArrayList<APTeil> a1 = new ArrayList<APTeil>();
        a1.add(new APTeil(49, null, 6));
        a1.add(new APTeil(54, null, 6));
        a1.add(new APTeil(29, null, 6));
        arbeit.get(0).setTeile(a1);

        arbeit.add(new APArbeitsplatz(null, 2, null, null, 80, null, null, 1.0));
        final ArrayList<APTeil> a2 = new ArrayList<APTeil>();
        a2.add(new APTeil(50, null, 5));
        a2.add(new APTeil(55, null, 5));
        a2.add(new APTeil(30, null, 5));
        arbeit.get(1).setTeile(a2);

        arbeit.add(new APArbeitsplatz(null, 3, null, null, 60, null, null, 1.0));
        final ArrayList<APTeil> a3 = new ArrayList<APTeil>();
        a3.add(new APTeil(51, null, 5));
        a3.add(new APTeil(56, null, 6));
        a3.add(new APTeil(31, null, 6));
        arbeit.get(2).setTeile(a3);

        arbeit.add(new APArbeitsplatz(null, 4, null, null, 80, null, null, 1.20));
        final ArrayList<APTeil> a4 = new ArrayList<APTeil>();
        a4.add(new APTeil(1, null, 6));
        a4.add(new APTeil(2, null, 7));
        a4.add(new APTeil(3, null, 7));
        arbeit.get(3).setTeile(a4);

        arbeit.add(new APArbeitsplatz(null, 6, null, null, 90, null, null, 1.0));
        final ArrayList<APTeil> a5 = new ArrayList<APTeil>();
        a5.add(new APTeil(16, null, 2));
        a5.add(new APTeil(18, null, 3));
        a5.add(new APTeil(19, null, 3));
        a5.add(new APTeil(20, null, 3));
        arbeit.get(4).setTeile(a5);

        arbeit.add(new APArbeitsplatz(null, 7, null, null, 210, null, null, 1.0));
        final ArrayList<APTeil> a6 = new ArrayList<APTeil>();
        a6.add(new APTeil(10, null, 2));
        a6.add(new APTeil(11, null, 2));
        a6.add(new APTeil(12, null, 2));
        a6.add(new APTeil(13, null, 2));
        a6.add(new APTeil(14, null, 2));
        a6.add(new APTeil(15, null, 2));
        a6.add(new APTeil(18, null, 2));
        a6.add(new APTeil(19, null, 2));
        a6.add(new APTeil(20, null, 2));
        a6.add(new APTeil(26, null, 2));
        arbeit.get(5).setTeile(a6);

        arbeit.add(new APArbeitsplatz(null, 8, null, null, 155, null, null, 1.0));
        final ArrayList<APTeil> a7 = new ArrayList<APTeil>();
        a7.add(new APTeil(10, null, 1));
        a7.add(new APTeil(11, null, 2));
        a7.add(new APTeil(12, null, 2));
        a7.add(new APTeil(13, null, 1));
        a7.add(new APTeil(14, null, 2));
        a7.add(new APTeil(15, null, 2));
        a7.add(new APTeil(18, null, 3));
        a7.add(new APTeil(19, null, 3));
        a7.add(new APTeil(20, null, 3));

        arbeit.get(6).setTeile(a7);

        arbeit.add(new APArbeitsplatz(null, 9, null, null, 140, null, null, 1.0));
        final ArrayList<APTeil> a8 = new ArrayList<APTeil>();
        a8.add(new APTeil(10, null, 3));
        a8.add(new APTeil(11, null, 3));
        a8.add(new APTeil(12, null, 3));
        a8.add(new APTeil(13, null, 3));
        a8.add(new APTeil(14, null, 3));
        a8.add(new APTeil(15, null, 3));
        a8.add(new APTeil(18, null, 2));
        a8.add(new APTeil(19, null, 2));
        a8.add(new APTeil(20, null, 2));

        arbeit.get(7).setTeile(a8);

        arbeit.add(new APArbeitsplatz(null, 10, null, null, 120, null, null, 1.0));
        final ArrayList<APTeil> a9 = new ArrayList<APTeil>();
        a9.add(new APTeil(4, null, 4));
        a9.add(new APTeil(5, null, 4));
        a9.add(new APTeil(6, null, 4));
        a9.add(new APTeil(7, null, 4));
        a9.add(new APTeil(8, null, 4));
        a9.add(new APTeil(9, null, 4));

        arbeit.get(8).setTeile(a9);

        arbeit.add(new APArbeitsplatz(null, 11, null, null, 120, null, null, 1.0));
        final ArrayList<APTeil> a10 = new ArrayList<APTeil>();
        a10.add(new APTeil(4, null, 3));
        a10.add(new APTeil(5, null, 3));
        a10.add(new APTeil(6, null, 3));
        a10.add(new APTeil(7, null, 3));
        a10.add(new APTeil(8, null, 3));
        a10.add(new APTeil(9, null, 3));

        arbeit.get(9).setTeile(a10);

        arbeit.add(new APArbeitsplatz(null, 12, null, null, 0, null, null, 1.0));
        final ArrayList<APTeil> a11 = new ArrayList<APTeil>();
        a11.add(new APTeil(10, null, 3));
        a11.add(new APTeil(11, null, 3));
        a11.add(new APTeil(12, null, 3));
        a11.add(new APTeil(13, null, 3));
        a11.add(new APTeil(14, null, 3));
        a11.add(new APTeil(15, null, 3));

        arbeit.get(10).setTeile(a11);

        arbeit.add(new APArbeitsplatz(null, 13, null, null, 0, null, null, 1.0));
        final ArrayList<APTeil> a12 = new ArrayList<APTeil>();
        a12.add(new APTeil(10, null, 2));
        a12.add(new APTeil(11, null, 2));
        a12.add(new APTeil(12, null, 2));
        a12.add(new APTeil(13, null, 2));
        a12.add(new APTeil(14, null, 2));
        a12.add(new APTeil(15, null, 2));

        arbeit.get(11).setTeile(a12);

        arbeit.add(new APArbeitsplatz(null, 14, null, null, 0, null, null, 1.0));
        final ArrayList<APTeil> a13 = new ArrayList<APTeil>();
        a13.add(new APTeil(16, null, 3));

        arbeit.get(12).setTeile(a13);

        arbeit.add(new APArbeitsplatz(null, 15, null, null, 30, null, null, 1.0));
        final ArrayList<APTeil> a14 = new ArrayList<APTeil>();
        a14.add(new APTeil(17, null, 3));
        a14.add(new APTeil(26, null, 3));

        arbeit.get(13).setTeile(a14);

        for (int n = 0; n < arbeit.size(); ++n) {
            arbeit.get(n).setInBearbeitung(0);
            arbeit.get(n).setUeberstunden(0);
            arbeit.get(n).setWarteschlange(0);
        }

    }

    public void doLogic() {
        arbeitsplaetze = new ArrayList<APArbeitsplatz>();

        System.out.println("one one " + one.getOptionOne());
        // 2400 2400
        //
        // 3720
        // 4800
        //
        // 6666
        // 7200

        final Dao dao = new Dao();
        final ArrayList<APTeil> erg = dao.getDispoHelpErgebnissGut();

        // for (int n = 0; n < erg.size(); n++) {
        // System.out.println("erg " + erg.get(n));
        // }
        

		System.out.println("BEGIN Aggregiere die Produktionauftraege und fuege diese Arbeitsplaetzen hinzu");
		System.out.println("\n");
		/*
		 * Wir weisen Arbeitsplaetzen ihre Produktionauftraege zu, um zu wissen
		 * wie viel Zeit je AP benoetigt wird.
		 * 
		 */
        for (int n = 0; n < arbeit.size(); n++) {
            for (int i = 0; i < arbeit.get(n).getTeile().size(); i++) {
                for (int o = 0; o < erg.size(); o++) {

                    if (erg.get(o).getNummer() == arbeit.get(n).getTeile() .get(i).getNummer()) {
                        arbeit.get(n).getTeile().get(i).setAnzahl(erg.get(o).getAnzahl());
                        // System.out.println("99999 " + arbeit.get(n));
                        //
                        // System.out.println("yoasdasd "
                        // + arbeit.get(n).getTeile().get(i));

                    }
                }
            }

        }
        
		
		System.out.println("END Produktionsauftraege und Ihre Zeit wurden Arbeitsplaetzen zugewiesen.");
		System.out.println("\n");
		
		
		System.out.println("BEGIN Aggregiere die Zeit fuer -in der Warteschlange- und fuege diese Arbeitsplaetzen hinzu");
		System.out.println("\n");
		
//		PeriodResults e = null;
//		e = ContextHelper.getManagedBean(ErgebnisBean.class).getXmlResult();
//		
		
		Results e2 = null;
		e2 = ContextHelper.getManagedBean(ErgebnisBean.class).getXmlResult2();
		
		
		// Fuege den Arbeitsplaetzen ueberige Zeiten aus letzer Periode
		// d.h. die Zeit fuer "in Bearbeitung" und fuer "Warteschlage"

//		List<Entry> feld2 = new ArrayList<Entry>();
//		feld2 = e.getWorkplaceWaitinglist().getEntry();
		
		List<de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace> feld3 = e2.getWaitinglistworkstations().getWorkplace();

		ArrayList<Paar> feld3b = new ArrayList<Paar>();
        
		   for(de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace x : feld3){
	        	
	        	for (Waitinglist waitingList  : x.getWaitinglist()){
	        		
	        		Paar waitingListEntry = new Paar(x.getId(), waitingList);
	        		
	        		feld3b.add(waitingListEntry);
	        		
	        	}
	       }
			
        
        
        final ArrayList<APHelp> helps = new ArrayList<APHelp>();

//        for (int n = 0; n < feld2.size(); n++) {
        for (int n = 0; n < feld3b.size(); n++) {

//			final Entry entry = feld3b.get(n).;
//			final APHelp h = new APHelp();
//			
//			h.setNummer(feld3b.get(n).get);
//			h.setWarteschlange(feld2.get(n).getRequiredTime());
//			helps.add(h);
			
			final APHelp h = new APHelp();
			h.setNummer(feld3b.get(n).getArbeitsplatz());
			h.setWarteschlange( BigInteger.valueOf(feld3b.get(n).getWaitinListEntry().getTimeneed()));
			helps.add(h);
		
			System.out.println("AP : "+h.getNummer()+" Time Needed: "+h.getWarteschlange());
			
//			System.out.println("feld 2" + feld2.get(n).getWorkplaceNumber()
//					+ " " + feld2.get(n).getRequiredTime());
        }

        Integer warte = 0;
        Integer arbeitsplatz = -1;
        final ArrayList<APHelp> helpsGUT = new ArrayList<APHelp>();
        arbeitsplatz = helps.get(0).getNummer().intValue();

        // TODODODODO
        for (int n = 0; n < helps.size(); n++) {

            System.out.println("helps " + helps.get(n));
            if (arbeitsplatz == helps.get(n).getNummer().intValue()) {
                warte = warte + helps.get(n).getWarteschlange().intValue();
            } else {

                final APHelp h = new APHelp();
                h.setWarteschlange(BigInteger.valueOf(warte));
                warte = 0;
                h.setNummer(helps.get(n - 1).getNummer());
                helpsGUT.add(h);
                arbeitsplatz = helps.get(n).getNummer().intValue();
                if (!helps.get(n - 1).getNummer().equals(arbeitsplatz)) {
                    warte = warte + helps.get(n).getWarteschlange().intValue();
                }
            }

            if (n == helps.size() - 1) {
                final APHelp h = new APHelp();
                h.setWarteschlange(BigInteger.valueOf(warte));
                warte = 0;
                h.setNummer(helps.get(n - 1).getNummer());
                helpsGUT.add(h);
            }
        }

        System.out.println("END Die Zeit fuer -in Warteschlage- wurde  Arbeitsplaetzen hinzugewiesen");
		System.out.println("\n");
        
        // for (int i = 0; i < helps2.size(); i++) {
        // System.out.println("sasdasdad " + helps2.get(i));
        // }
		
		System.out.println("BEGINN Aggregiere die Zeit fuer -in Bearbeitung - und fuege diese Arbeitsplaetzen hinzu");
		System.out.println("\n");
		

//		List<Entry> feld3 = new ArrayList<Entry>();
//		feld3 = e.getOrdersBeeingProcessed().getEntry();
		
		List<Workplace> feld4 = new ArrayList<Workplace>();
		feld4 = e2.getOrdersinwork().getWorkplace();

//        for (int n = 0; n < helpsGUT.size(); n++) {
//            helpsGUT.get(n).setBearbeitung(BigInteger.valueOf(0));
//
//            if (helpsGUT.get(n).getNummer().equals(feld3.get(0).getWorkplaceNumber())) {
//                helpsGUT.get(n).setBearbeitung(feld3.get(0).getCycleTime());
//            }
//            System.out.println("nigga " + helpsGUT.get(n));
//        }
		for (int n = 0; n < helpsGUT.size(); n++) {
			helpsGUT.get(n).setBearbeitung(BigInteger.valueOf(0));
			
			for (int i = 0; i < feld4.size(); i++){
				
				if (helpsGUT.get(n).getNummer().equals(BigInteger.valueOf(feld4.get(i).getId()))) {
					helpsGUT.get(n).setBearbeitung(BigInteger.valueOf(feld4.get(i).getTimeneed()));
				}
				System.out.println("nigga " + helpsGUT.get(n));
			}

		}

        for (int i = 0; i < arbeit.size(); i++) {
            for (int n = 0; n < helpsGUT.size(); n++) {
                if (arbeit.get(i).getNummer() == helpsGUT.get(n).getNummer() .intValue()) {
                    arbeit.get(i).setInBearbeitung(helpsGUT.get(n).getBearbeitung().intValue());
                    arbeit.get(i).setWarteschlange(helpsGUT.get(n).getWarteschlange().intValue());
                }
            }
        }
    	
		System.out.println("END Die Zeit fuer -in Bearbeitung- wurde  Arbeitsplaetzen hinzugewiesen");
		System.out.println("\n");

		
		System.out.println("BEGINN Berechne die Ueberstuenden und die Schichtanzahl fuer jeden Arbeitsplatz");
		System.out.println("\n");
		
        final Integer uberstunden;
        final Integer schicht;
        for (int i = 0; i < arbeit.size(); i++) {

            System.out.println("888 " + arbeit.get(i));
            arbeit.get(i).setUeberstunden(
                    arbeit.get(i).getInBearbeitung()
                            + arbeit.get(i).getWarteschlange()
                            + arbeit.get(i).getRuestzeit());
            // DUMMESTODO
            for (int n = 0; n < arbeit.get(i).getTeile().size(); n++) {
                // if (arbeit.get(i).getTeile().get(n).getAnzahl() != null) {
                arbeit.get(i).setUeberstunden(
                        arbeit.get(i).getUeberstunden()
                                + arbeit.get(i).getTeile().get(n).getAnzahl()
                                * +arbeit.get(i).getTeile().get(n)
                                        .getZeitProTeil());
                // }
            }

            arbeit.get(i).setUeberstunden(
                    (int) (arbeit.get(i).getUeberstunden() * arbeit.get(i)
                            .getAufschlag()));

            arbeit.get(i)
                    .setUeberstunden(
                            (int) (Math.rint((double) arbeit.get(i)
                                    .getUeberstunden() / 10) * 10));

            if (arbeit.get(i).getUeberstunden() > Integer.valueOf(three
                    .getOptionOne())) {
                arbeit.get(i).setSchicht(3);
                if (arbeit.get(i).getUeberstunden() > Integer.valueOf(three
                        .getOptionTwo())) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden()
                                    - Integer.valueOf(three.getOptionTwo()));

                } else {
                    arbeit.get(i).setUeberstundenGut(0);
                }
                continue;
            }

            if (arbeit.get(i).getUeberstunden() > Integer.valueOf(two
                    .getOptionOne())) {
                arbeit.get(i).setSchicht(2);
                if (arbeit.get(i).getUeberstunden() > Integer.valueOf(two
                        .getOptionTwo())) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden()
                                    - Integer.valueOf(two.getOptionTwo()));

                } else {
                    arbeit.get(i).setUeberstundenGut(0);
                }
                continue;
            }

            if (arbeit.get(i).getUeberstunden() > Integer.valueOf(one
                    .getOptionOne())) {
                arbeit.get(i).setSchicht(1);
                if (arbeit.get(i).getUeberstunden() > Integer.valueOf(one
                        .getOptionTwo())) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden()
                                    - Integer.valueOf(one.getOptionTwo()));

                } else {
                    arbeit.get(i).setUeberstundenGut(0);
                }
                continue;
            }

            if (arbeit.get(i).getUeberstunden() < 2401) {
                arbeit.get(i).setSchicht(1);
                arbeit.get(i).setUeberstundenGut(0);

            }

        }

        ContextHelper.getManagedBean(ErgebnisBean.class).setArbeitsplaetze(
                arbeit);
        
        // TODO VASSIL Quickfix
        for (int n = 0; n < arbeit.size(); n++) {
        	if(arbeit.get(n).getUeberstunden() > 7200){
        		arbeit.get(n).setUeberstunden(7200);
        		arbeit.get(n).setUeberstundenGut(0);
        		arbeit.get(n).setSchicht(3);
        	}
        	
        	
        }
        
        
        for (int n = 0; n < arbeit.size(); n++) {
            arbeitsplaetze.add(arbeit.get(n));
            System.out.println(arbeit.get(n).getAufschlag());
        }
        

		System.out.println("ENDE Die Ueberstuenden und die Schichtanzahl wurden berechnet");
		System.out.println("\n");

        // final FacesContext context = FacesContext.getCurrentInstance();
        // final HttpServletResponse response = (HttpServletResponse) context
        // .getExternalContext().getResponse();
        // try {
        // response.sendRedirect("einkauf.xhtml");
        // } catch (final IOException ea) {
        // // TODO Auto-generated catch block
        // ea.printStackTrace();
        // }

    }

    public ArrayList<APArbeitsplatz> getArbeitsplaetze() {
        if (first) {
			System.out.print("BEGINN Initalisiere Rüstzeiten, Zeit pro Teil Daten, Schichtendauer und welche Teile an welchem Arbeitsplatz bearbeitet werden");
			System.out.println();

			init();
			
			System.out.print("ENDE Rüstzeiten, Zeit pro Teil Daten, Schichtendauer und welche Teile an welchem Arbeitsplatz wurden initialisiert");
			System.out.println();

        }
        first = false;
        doLogic();

        for (int n = 0; n < arbeitsplaetze.size(); n++) {
            System.out.println("getAP " + arbeitsplaetze.get(n));
        }
        return arbeitsplaetze;
    }

    public void setOptionOne(final APOption a) {
        one = a;
    }

    public void setOptionTwo(final APOption a) {
        two = a;
    }

    public void setOptionThree(final APOption a) {
        three = a;
    }

    public APOption getOptionOne() {
        return one;
    }

    public APOption getOptionTwo() {
        return two;
    }

    public APOption getOptionThree() {
        return three;
    }
}
