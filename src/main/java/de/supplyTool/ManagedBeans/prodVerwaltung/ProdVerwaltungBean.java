package de.supplyTool.ManagedBeans.prodVerwaltung;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import de.supplyTool.ManagedBeans.ErgebnisBean;
import de.supplyTool.ManagedBeans.disposition.Produktionsprogramm;
import de.supplyTool.dao.Dao;
import de.supplyTool.dao.Util.JaxbMarshalUnmarshalUtil;
import de.supplyTool.domain.Teil_dispohelp;
import de.supplyTool.generated.Entry;
import de.supplyTool.generated.PeriodResults;
import de.supplyTool.generated2.Results;
import de.supplyTool.generated2.Results.Ordersinwork.Workplace;
import de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace.Waitinglist;
import de.supplyTool.generated2.Results.Warehousestock.Article;
import de.supplyTool.util.ContextHelper;

@ManagedBean
@ApplicationScoped
public class ProdVerwaltungBean implements Serializable {

    private static final long               serialVersionUID         = -4143422761481106118L;
    public static final String              XML_SCHEMA_FILE_PATH     = "D:/dev/workspace4.3/supplyTool/ausgabe.xsd";
    private final ArrayList<Teil_dispohelp> ergebnisseInsert         = new ArrayList<Teil_dispohelp>();
    private ArrayList<Teil_dispohelp>       ergebnisseBerechnern     = null;
    private ArrayList<Teil_dispohelp>       ergebnisseBerechnernzwei = null;
    private ArrayList<Teil_dispohelp>       ergebnisseBerechnerndrei = null;

    private transient Part                  file;

    private String                          fileContent;
    private File                            currentFile;

    public Part getFile() {
        return file;
    }

    public void setFile(final Part file) {
        this.file = file;
    }

    public void upload() {
        try {
        	System.out.println("BEGINN upload: XML Datei hochladen");
        	System.out.println("PRETTY WOMAN");

            delete();
            
            System.out.println("XML einlesen");
            System.out.println("\n");
            
            
            final InputStream inputStream = file.getInputStream();
            final Scanner scanner = new Scanner(inputStream);

            fileContent = scanner.useDelimiter("\\A").next();

            inputStream.close();
            scanner.close();

            System.out.println("Folgende XML wurde eingelesen");
            System.out.println(fileContent);
            System.out.println("\n");


            System.out.println("FilePath fuer den Speicherort der XML auf dem Server aufbauen");
            System.out.println("\n");
            
            // Pfad zur Klasse (ClassPath)
            final String path = ProdVerwaltungBean.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath();

            // Klassenspezifische sachen abschneiden
            String destinationDir = path.replace(
                    "ManagedBeans/prodVerwaltung/ProdVerwaltungBean.class", "");
            // xmlFiles Ordner anw�hlen
            destinationDir = destinationDir + "xmlFiles";

            // Name der Datei hinzuf�gen
            final String filePath = destinationDir + "/"
                    + file.getName().replace(":", "_") + ".xml";

            System.out.println("FilePath FERTIG: " + filePath);
            System.out.println("\n");


            System.out.println("BEGINN eingelesene Datei als XML auf dem Server speichern");
            System.out.println("\n");

            // Datei wegschreiben
            final File uploadedXmlFile = new File(filePath);
            uploadedXmlFile.createNewFile();
            final FileWriter fileWriter = new FileWriter(uploadedXmlFile);
            fileWriter.append(fileContent);
            fileWriter.close();
            currentFile = uploadedXmlFile;
            
            System.out.println("ENDE eingelesene Datei wurde als XML auf dem Server gespeichert");
            System.out.println("\n");
            
            System.out.println("BEGINN insert");
            System.out.println("\n");
            
            insert();
            
            System.out.println("ENDE alle Daten aus XML wurde in der DB gespeichert");
            System.out.println("\n");
            
            System.out.println("ENDE upload: XML Datei hochladen");
            
            
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private String nameToId(String a) {
        a = a.substring(a.length() - 2, a.length());
        if (a.charAt(0) == ' ') {
            a = String.valueOf(a.charAt(1));
        }

        return a;
    }

    public void insert() {
        if (file == null) {
            FacesContext
                    .getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    "Keine Datei Hochgeldaen. Bitte nochmal hochladen.",
                                    "Bitte die Datei nochmal hochladen"));
            return;
        }
        
        System.out.println("Baue Pfad zu results.xsd");
        System.out.println("\n");

        
        // Pfad zur Klasse (ClassPath)
        final String path = ProdVerwaltungBean.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();

        // Klassenspezifische sachen abschneiden
        String destinationDir = path.replace(
                "ManagedBeans/prodVerwaltung/ProdVerwaltungBean.class", "");
        destinationDir = destinationDir + "xmlFiles" + "/results.xsd";
        
        System.out.println("Pfad gebaut: "+ destinationDir);

        final Dao dao = new Dao();

        //PeriodResults e = null; // Periodenergebnisse aus ausgelesener XML Datei
        
        Results e2 = null; 
        try {
        	System.out.println(currentFile.getAbsolutePath());
            
            
            System.out.println("BEGINN Unmarshalling anhand von results.xsd");
            System.out.println("\n");
            
            //e = JaxbMarshalUnmarshalUtil.unmarshal(destinationDir,currentFile.getAbsolutePath(), PeriodResults.class);
            
            e2 = JaxbMarshalUnmarshalUtil.unmarshal(destinationDir,currentFile.getAbsolutePath(), Results.class);
            
            final ErgebnisBean managedBean = ContextHelper.getManagedBean(ErgebnisBean.class);
            //managedBean.setXmlResult(e);
            managedBean.setXmlResult2(e2);
            
            
            System.out.println("ENDE Unmarshalling anhand von results.xsd erfolgreich");
            System.out.println("\n");
            
        } catch (final JAXBException e1) {
            // TODO Auto-generated catch block
            System.out.println("1");
            e1.printStackTrace();
        } catch (final SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        System.out.println("BEGINN Schreibe in die DB, welche Bestellungen angekommen sind und welche noch ausstehen ");
        System.out.println("\n");

        
        //dao.updateBestellungen(e.getFutureInwardStockMovements(),Integer.valueOf(e.getPeriod().toString()));
        
        
        dao.updateBestellungen2(e2.getFutureinwardstockmovement(),e2.getPeriod());

        
        System.out.println("ENDE ausstehende Bestellungen wurden in der DB aktualisert. ");
        System.out.println("\n");
        
        
//        List<de.supplyTool.generated.Entry> feld = new ArrayList<Entry>();
        List<Article> feld2 = new ArrayList<Article>();

        
        final ArrayList<Teil_dispohelp> ergebnisse = new ArrayList<Teil_dispohelp>();

        
        System.out.println("BEGINN bereite Daten fuer Teil und Lagerbestand vor ");
        System.out.println("\n");
        
//        feld = e.getWarehouseStock().getEntry();
          feld2 = e2.getWarehousestock().getArticle();


        // teil setzen
        // lagerbvestand setzzen

          for (int n = 0; n < feld2.size(); ++n) {

//            String a = feld2.get(n).getItem().substring(feld2.get(n).getItem().length() - 2,feld2.get(n).getItem().length());
//            a = nameToId(a);

            ergebnisse.add(new Teil_dispohelp());
//            ergebnisse.get(n).setNummer(Integer.valueOf(a));
            ergebnisse.get(n).setNummer(feld2.get(n).getId());

            
            ergebnisse.get(n).setLagerbestand_ende_vorperiode(feld2.get(n).getAmount());

        }
        
        System.out.println("ENDE  Teil und Lagerbestand erfolgreich vorbereitet");
        System.out.println("\n");
        
        System.out.println("BEGINN  Auftraege in Bearbeitung vorbereiten");
        System.out.println("\n");

        
        
//      feld = e.getOrdersBeeingProcessed().getEntry();
       List<Workplace> feld3 = e2.getOrdersinwork().getWorkplace();
      
      // auftrage in bearbeitung setzen
      Integer tmp = -1;
      for (int n = 0; n < feld3.size(); ++n) {
          for (int i = 0; i < ergebnisse.size(); i++) {
              // System.out.println("1 "+dispofeld.get(i).getNummer());
              // System.out.println("2 "+name_to_id(feld.get(n).getItem()));
              if (ergebnisse.get(i).getNummer().equals(feld3.get(n).getItem())) {
                  tmp = i;

              }
          }

          ergebnisse.get(tmp).setAuftrage_bearbeitung(feld3.get(n).getAmount());
      }
      
      
      System.out.println("ENDE  Auftraege in Bearbeitung  wurden vorbereitet");
      System.out.println("\n");

      
      
      System.out.println("BEGINN  Auftraege in der Warteschlange vorbereiten");
      System.out.println("\n");

      // auftraege in der warteschlange setzen

     //   feld = e.getWorkplaceWaitinglist().getEntry();
    
    // die daten fuer unsere xml schema anpassen
    List<de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace> feld4 = e2.getWaitinglistworkstations().getWorkplace();
     ArrayList<Waitinglist> feld4b = new ArrayList<Waitinglist>();
     
     for(de.supplyTool.generated2.Results.Waitinglistworkstations.Workplace x : feld4){

    	 feld4b.addAll(x.getWaitinglist());
    }
    		 
    
    for (int n = 0; n < feld4b.size(); n++) {
    	
        for (int i = 0; i < ergebnisse.size(); i++) {
        	
            // System.out.println("1 "+dispofeld.get(i).getNummer());
            // System.out.println("2 "+name_to_id(feld.get(n).getItem()));
            if (ergebnisse.get(i).getNummer().equals(feld4b.get(n).getItem())) {
                tmp = i;

            }
        }
        
        ergebnisse.get(tmp).setAuftraege_warteschlange(feld4b.get(n).getAmount());

    }
    
    System.out.println("ENDE  Auftraege in der Warteschlange wurden vorbereitet");
    System.out.println("\n");

    // Produkt 1 und seine Eigenfertigungsteile
    final ArrayList<Integer> eins = new ArrayList<Integer>();
    eins.add(1);
    eins.add(26);
    eins.add(51);
    eins.add(16);
    eins.add(17);
    eins.add(50);
    eins.add(4);
    eins.add(10);
    eins.add(49);
    eins.add(7);
    eins.add(13);
    eins.add(18);

    // Produkt 2 und seine Eigenfertigungsteile
    final ArrayList<Integer> zwei = new ArrayList<Integer>();
    zwei.add(2);
    zwei.add(26);
    zwei.add(56);
    zwei.add(16);
    zwei.add(17);
    zwei.add(55);
    zwei.add(5);
    zwei.add(11);
    zwei.add(54);
    zwei.add(8);
    zwei.add(14);
    zwei.add(19);

    // Produkt 3 und seine Eigenfertigungsteile
    final ArrayList<Integer> drei = new ArrayList<Integer>();
    drei.add(3);
    drei.add(26);
    drei.add(31);
    drei.add(16);
    drei.add(17);
    drei.add(30);
    drei.add(6);
    drei.add(12);
    drei.add(29);
    drei.add(9);
    drei.add(15);
    drei.add(20);

    System.out.println("BEGINN Dispo_n in  DB aktualisieren");
    System.out.println("\n");
    
    dao.setDispoHelp(ergebnisse, eins, zwei, drei);

    System.out.println("ENDE  Dispo_n wurde in der DB aktualisiert");
    System.out.println("\n");
    
    System.out.println("BEGINN Lagerbestand in der DB aktualisieren");
    System.out.println("\n");
    
    dao.updateLagermenge(ergebnisse);
    
    System.out.println("ENDE Lagerbestand in der DB erfolgreich aktualisiert");
    System.out.println("\n");
    
    }

    // Ergebniss anzeigen
    public ArrayList<Teil_dispohelp> getDispositionsErgebnisseViewEins() {

        final Dao dao = new Dao();
        return dao.getDispoHelpErgebniss(1);

    }

    public ArrayList<Teil_dispohelp> getDispositionsErgebnisseViewZwei() {
        final Dao dao = new Dao();
        return dao.getDispoHelpErgebniss(2);

    }

    public ArrayList<Teil_dispohelp> getDispositionsErgebnisseViewDrei() {
        final Dao dao = new Dao();
        return dao.getDispoHelpErgebniss(3);

    }

    // Anzeigen und brechnen
    public ArrayList<Teil_dispohelp> getDispositionsErgebnisse() {
        final Dao dao = new Dao();
        ergebnisseBerechnern = dao.getTeileDispoHelp(1);

        final Produktionsprogramm managedBean = ContextHelper
                .getManagedBean(Produktionsprogramm.class);
        if (ergebnisseBerechnern.size() > 0) {
            if (ergebnisseBerechnern.get(0).getNummer() == 1) {
                ergebnisseBerechnern.get(0).setVertriebswunsch(
                        managedBean.getP1_1());
                for (int i = 0; i < ergebnisseBerechnern.size(); i++) {
                    ergebnisseBerechnern.get(i).setGeplante_lagermenge(
                            managedBean.getP1_1());
                }

            }

        }
        return ergebnisseBerechnern;
    }

    public ArrayList<Teil_dispohelp> getDispositionsErgebnisseZwei() {
        final Dao dao = new Dao();
        ergebnisseBerechnernzwei = dao.getTeileDispoHelp(2);

        final Produktionsprogramm managedBean = ContextHelper
                .getManagedBean(Produktionsprogramm.class);
        if (ergebnisseBerechnernzwei.size() > 0) {
            ergebnisseBerechnernzwei.get(0).setVertriebswunsch(
                    managedBean.getP2_1());
            for (int i = 0; i < ergebnisseBerechnernzwei.size(); i++) {
                ergebnisseBerechnernzwei.get(i).setGeplante_lagermenge(
                        managedBean.getP2_1());

            }
        }

        return ergebnisseBerechnernzwei;
    }

    public ArrayList<Teil_dispohelp> getDispositionsErgebnisseDrei() {
        final Dao dao = new Dao();
        ergebnisseBerechnerndrei = dao.getTeileDispoHelp(3);

        final Produktionsprogramm managedBean = ContextHelper
                .getManagedBean(Produktionsprogramm.class);
        if (ergebnisseBerechnerndrei.size() > 0) {
            ergebnisseBerechnerndrei.get(0).setVertriebswunsch(
                    managedBean.getP3_1());
            for (int i = 0; i < ergebnisseBerechnerndrei.size(); i++) {
                ergebnisseBerechnerndrei.get(i).setGeplante_lagermenge(
                        managedBean.getP3_1());
            }
        }
        return ergebnisseBerechnerndrei;
    }

    // kp
    public ArrayList<Teil_dispohelp> getTeileHelpDispoList() {
        return ergebnisseBerechnern;
    }

    public ArrayList<Teil_dispohelp> getTeileHelpDispoListZwei() {
        return ergebnisseBerechnernzwei;
    }

    public ArrayList<Teil_dispohelp> getTeileHelpDispoListDrei() {
        return ergebnisseBerechnerndrei;
    }

    // public void setGeplanteLagermenge(Teil_dispohelp a, Integer s) {
    //
    // insert();
    // Integer e = ergebnisse.indexOf(a);
    // ergebnisse.get(e).setGeplante_lagermenge(s);
    //
    // }

    public void delete() {
        System.out
                .println("DEEEEEEEEEEEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLLLEEEEEEEEEEEEEEEEEEETTTTTTTTTTTTTTTEEEEEEEEEEEE");
        final Dao dao = new Dao();
        dao.resetTeileDispo();
        dao.resetTeileDispoErgebniss();
    }

    public String berechnen() {
    	
    	
        System.out.println("BEGIN Berechne Daten fuer P1");
        System.out.println("\n");
        
        final Dao dao = new Dao();
        final ArrayList<Teil_dispohelp> dispoHelpErgebniss = dao
                .getDispoHelpErgebniss(1);

        System.out.println("size gut " + dispoHelpErgebniss.size());

        if (dispoHelpErgebniss.size() > 0) {
            dao.resetTeileDispoErgebniss(1);
        }

        System.out.println("berechnen start");
        // Dao dao = new Dao();
        // dao.resetTeileDispo();
        // ArrayList<Teil_dispohelp> feld = dao.getTeileDispoHelp(1);
        for (int n = 0; n < ergebnisseBerechnern.size(); n++) {

            final Teil_dispohelp e = ergebnisseBerechnern.get(n);
            // System.out.println("davor " + ergebnisseBerechnern.get(n));
            if (n == 0) {
                ergebnisseBerechnern.get(n).setHelpint(0);
            }
            ergebnisseBerechnern.get(n).setProduktionsauftrag_naechste_periode(
                    e.getVertriebswunsch() + e.getGeplante_lagermenge()
                            + e.getHelpint()
                            - e.getLagerbestand_ende_vorperiode()
                            - e.getAuftraege_warteschlange()
                            - e.getAuftrage_bearbeitung());
            // System.out.println("tes88 "
            // + ergebnisseBerechnern.get(n)
            // .getProduktionsauftrag_naechste_periode());

            // abnahme n�chstes teil setzen

            if (n < 2) {
                ergebnisseBerechnern.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnern.get(0)
                                .getProduktionsauftrag_naechste_periode()));
                ergebnisseBerechnern.get(n + 1).setHelpint(
                        ergebnisseBerechnern.get(0)
                                .getAuftraege_warteschlange());
            }
            if (n >= 2 && n < 6) {
                ergebnisseBerechnern.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnern.get(2)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnern.get(n + 1).setHelpint(
                        ergebnisseBerechnern.get(2)
                                .getAuftraege_warteschlange());
            }
            if (n >= 5 && n < 9) {
                ergebnisseBerechnern.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnern.get(5)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnern.get(n + 1).setHelpint(
                        ergebnisseBerechnern.get(5)
                                .getAuftraege_warteschlange());
            }
            if (n >= 8 && n < 11) {
                ergebnisseBerechnern.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnern.get(8)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnern.get(n + 1).setHelpint(
                        ergebnisseBerechnern.get(8)
                                .getAuftraege_warteschlange());
            }

            System.out.println(String.valueOf(n) + "  danach "
                    + ergebnisseBerechnern.get(n));
        }
        
        System.out.println("ENDE Daten fuer P1 wurden berechnet");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Fuege berechnete Daten fuer P1 in dispo_eins_ergebniss ein.");
        System.out.println("\n");
        
        // final Dao dao = new Dao();
        dao.setDispoHelpErgebniss(ergebnisseBerechnern);
        

        System.out.println("ENDE Berechnete Daten fuer P1 wurden in dispo_eins_ergebniss erfolgreich geschrieben.");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Wechsle zu prodverwaltungP1E.xhtml");
        System.out.println("\n");

        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        try {
            response.sendRedirect("prodverwaltungP1E.xhtml");
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return weiter1();
    }

    public void berechnenZwei() {
    	
  	  System.out.println("BEGIN Berechne Daten fuer P2");
      System.out.println("\n");
      
      
        final Dao dao = new Dao();
        final ArrayList<Teil_dispohelp> dispoHelpErgebniss = dao
                .getDispoHelpErgebniss(2);

        if (dispoHelpErgebniss.size() > 0) {
            dao.resetTeileDispoErgebniss(2);
        }

        System.out.println("berechnen start");
        // Dao dao = new Dao();
        // dao.resetTeileDispo();
        // ArrayList<Teil_dispohelp> feld = dao.getTeileDispoHelp(1);
        for (int n = 0; n < ergebnisseBerechnernzwei.size(); n++) {

            final Teil_dispohelp e = ergebnisseBerechnernzwei.get(n);
            // System.out.println("davor " + ergebnisseBerechnernzwei.get(n));
            if (n == 0) {
                ergebnisseBerechnernzwei.get(n).setHelpint(0);
            }
            ergebnisseBerechnernzwei.get(n)
                    .setProduktionsauftrag_naechste_periode(
                            e.getVertriebswunsch() + e.getGeplante_lagermenge()
                                    + e.getHelpint()
                                    - e.getLagerbestand_ende_vorperiode()
                                    - e.getAuftraege_warteschlange()
                                    - e.getAuftrage_bearbeitung());
            // System.out.println("tes88 "
            // + ergebnisseBerechnernzwei.get(n)
            // .getProduktionsauftrag_naechste_periode());

            // abnahme n�chstes teil setzen

            if (n < 2) {
                ergebnisseBerechnernzwei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnernzwei.get(0)
                                .getProduktionsauftrag_naechste_periode()));
                ergebnisseBerechnernzwei.get(n + 1).setHelpint(
                        ergebnisseBerechnernzwei.get(0)
                                .getAuftraege_warteschlange());
            }
            if (n >= 2 && n < 6) {
                ergebnisseBerechnernzwei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnernzwei.get(2)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnernzwei.get(n + 1).setHelpint(
                        ergebnisseBerechnernzwei.get(2)
                                .getAuftraege_warteschlange());
            }
            if (n >= 5 && n < 9) {
                ergebnisseBerechnernzwei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnernzwei.get(5)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnernzwei.get(n + 1).setHelpint(
                        ergebnisseBerechnernzwei.get(5)
                                .getAuftraege_warteschlange());
            }
            if (n >= 8 && n < 11) {
                ergebnisseBerechnernzwei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnernzwei.get(8)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnernzwei.get(n + 1).setHelpint(
                        ergebnisseBerechnernzwei.get(8)
                                .getAuftraege_warteschlange());
            }

            System.out.println(String.valueOf(n) + "  danach "
                    + ergebnisseBerechnernzwei.get(n));
        }
        
        System.out.println("ENDE Daten fuer P2 wurden berechnet");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Fuege berechnete Daten fuer P2 in dispo_zwei_ergebniss ein.");
        System.out.println("\n");
        
        // final Dao dao = new Dao();
        dao.setDispoHelpErgebniss(ergebnisseBerechnernzwei);
        
        System.out.println("ENDE Berechnete Daten fuer P2 wurden in dispo_zwei_ergebniss erfolgreich geschrieben.");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Wechsle zu prodverwaltungP2E.xhtml");
        System.out.println("\n");
        

        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        try {
            response.sendRedirect("prodverwaltungP2E.xhtml");
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void berechnenDrei() {
    	
    	System.out.println("BEGIN Berechne Daten fuer P3");
        System.out.println("\n");
        
        final Dao dao = new Dao();
        final ArrayList<Teil_dispohelp> dispoHelpErgebniss = dao
                .getDispoHelpErgebniss(3);

        if (dispoHelpErgebniss.size() > 0) {
            dao.resetTeileDispoErgebniss(3);
        }
        System.out.println("berechnen start");
        // Dao dao = new Dao();
        // dao.resetTeileDispo();
        // ArrayList<Teil_dispohelp> feld = dao.getTeileDispoHelp(1);
        for (int n = 0; n < ergebnisseBerechnerndrei.size(); n++) {

            final Teil_dispohelp e = ergebnisseBerechnerndrei.get(n);
            // System.out.println("davor " + ergebnisseBerechnerndrei.get(n));
            if (n == 0) {
                ergebnisseBerechnerndrei.get(n).setHelpint(0);
            }
            ergebnisseBerechnerndrei.get(n)
                    .setProduktionsauftrag_naechste_periode(
                            e.getVertriebswunsch() + e.getGeplante_lagermenge()
                                    + e.getHelpint()
                                    - e.getLagerbestand_ende_vorperiode()
                                    - e.getAuftraege_warteschlange()
                                    - e.getAuftrage_bearbeitung());
            // System.out.println("tes88 "
            // + ergebnisseBerechnerndrei.get(n)
            // .getProduktionsauftrag_naechste_periode());

            // abnahme n�chstes teil setzen

            if (n < 2) {
                ergebnisseBerechnerndrei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnerndrei.get(0)
                                .getProduktionsauftrag_naechste_periode()));
                ergebnisseBerechnerndrei.get(n + 1).setHelpint(
                        ergebnisseBerechnerndrei.get(0)
                                .getAuftraege_warteschlange());
            }
            if (n >= 2 && n < 6) {
                ergebnisseBerechnerndrei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnerndrei.get(2)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnerndrei.get(n + 1).setHelpint(
                        ergebnisseBerechnerndrei.get(2)
                                .getAuftraege_warteschlange());
            }
            if (n >= 5 && n < 9) {
                ergebnisseBerechnerndrei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnerndrei.get(5)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnerndrei.get(n + 1).setHelpint(
                        ergebnisseBerechnerndrei.get(5)
                                .getAuftraege_warteschlange());
            }
            if (n >= 8 && n < 11) {
                ergebnisseBerechnerndrei.get(n + 1).setVertriebswunsch(
                        (ergebnisseBerechnerndrei.get(8)
                                .getProduktionsauftrag_naechste_periode()));

                ergebnisseBerechnerndrei.get(n + 1).setHelpint(
                        ergebnisseBerechnerndrei.get(8)
                                .getAuftraege_warteschlange());
            }

            System.out.println(String.valueOf(n) + "  danach "
                    + ergebnisseBerechnerndrei.get(n));
        }
        
        
        System.out.println("ENDE Daten fuer P3 wurden berechnet");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Fuege berechnete Daten fuer P3 in dispo_drei_ergebniss ein.");
        System.out.println("\n");
        
        
        // final Dao dao = new Dao();
        dao.setDispoHelpErgebniss(ergebnisseBerechnerndrei);
        
        System.out.println("ENDE Berechnete Daten fuer P3 wurden in dispo_drei_ergebniss erfolgreich geschrieben.");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Fuege berechnete Daten fuer P1,P2 und P3 in dispo_ergebniss ein.");
        System.out.println("\n");
        
        
        dao.setDispoErgebnissGut();
        
        System.out.println("ENDE  Berechnete Daten fuer P1,P2 und P3 wurden erfolgreich in dispo_ergebniss eingetragen.");
        System.out.println("\n");
        
        
        
        System.out.println("BEGIN ");
        System.out.println("\n");
        
        dao.OrderDispoHelpErgebniss();
        
        System.out.println("ENDE  ");
        System.out.println("\n");
        
        
        System.out.println("BEGIN Wechsle zu prodverwaltungP3E.xhtml");
        System.out.println("\n");
        
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        try {
            response.sendRedirect("prodverwaltungP3E.xhtml");
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String weiter1() {
        return "prodverwaltung_ergebniss.xhtml";
        // final FacesContext context = FacesContext.getCurrentInstance();
        // final HttpServletResponse response = (HttpServletResponse) context
        // .getExternalContext().getResponse();
        // try {
        // response.sendRedirect("prodverwaltungzwei.xhtml");
        // } catch (final IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    public String weiter2() {
        return "prodverwaltungdrei.xhtml";
        // final FacesContext context = FacesContext.getCurrentInstance();
        // final HttpServletResponse response = (HttpServletResponse) context
        // .getExternalContext().getResponse();
        // try {
        // response.sendRedirect("prodverwaltungdrei.xhtml");
        // } catch (final IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

    }
}
