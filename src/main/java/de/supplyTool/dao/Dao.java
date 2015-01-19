package de.supplyTool.dao;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.supplyTool.dao.Util.BestellungSpalten;
import de.supplyTool.dao.Util.DbHelper;
import de.supplyTool.dao.Util.TeilSpalten;
import de.supplyTool.domain.APTeil;
import de.supplyTool.domain.BestellTyp;
import de.supplyTool.domain.Bestellung;
import de.supplyTool.domain.MengenStueckliste;
import de.supplyTool.domain.MengenStuecklisteItem;
import de.supplyTool.domain.Teil;
import de.supplyTool.domain.TeilLieferdaten;
import de.supplyTool.domain.Teil_dispohelp;
import de.supplyTool.domain.Verwendung;
import de.supplyTool.generated.Entry;
import de.supplyTool.generated.FutureInwardStockMovements;
import de.supplyTool.generated2.Results.Futureinwardstockmovement;
import de.supplyTool.generated2.Results.Futureinwardstockmovement.Order;

/**
 *
 * @author Tobias
 *
 */
public class Dao implements Serializable {

    private static final long serialVersionUID = -5871893125483310565L;

    public Dao() {
        // try {
        // Class.forName("com.mysql.jdbc.Driver");
        // } catch (final ClassNotFoundException e) {
        // e.printStackTrace();
        // }
    }

    private Connection getConnection() {
        try {
            return ConnectionPool.getDataSource().getConnection();
        } catch (final SQLException e) {

            e.printStackTrace();
            return null;
        }
        // try {
        // return DriverManager
        // .getConnection("jdbc:mysql://81.169.220.219/ibsys?"
        // + "user=ibsysuser&password=hDZM7Mv51oSAVacApXe8");
        // } catch (final SQLException e) {
        // e.printStackTrace();
        // return null;
        // }
    }

    // try {
    // return DriverManager
    // .getConnection("jdbc:mysql://81.169.220.219/ibsys?"
    // + "user=ibsysuser&password=hDZM7Mv51oSAVacApXe8");
    // } catch (final SQLException e) {
    // e.printStackTrace();
    // return null;
    // }

    public ArrayList<Bestellung> getBestellungen() {
        final ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from bestellung");
            while (rs.next()) {

                bestellungen.add(new Bestellung(rs
                        .getInt(BestellungSpalten.PERIODE), BestellTyp
                        .valueOf(rs.getString(BestellungSpalten.BESTELL_TYP)),
                        rs.getInt(BestellungSpalten.MENGE), this.getTeil(rs
                                .getInt(BestellungSpalten.TEILE_NUMMER)), rs
                                .getBoolean(BestellungSpalten.EINGETROFFEN)));

            }
            return bestellungen;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public ArrayList<Bestellung> getBestellung(final BestellTyp bestellTyp) {
        final ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from bestellung where "
                    + BestellungSpalten.BESTELL_TYP + " = '"
                    + bestellTyp.toString() + "'");
            while (rs.next()) {
                bestellungen.add(new Bestellung(rs
                        .getInt(BestellungSpalten.PERIODE), BestellTyp
                        .valueOf(rs.getString(BestellungSpalten.BESTELL_TYP)),
                        rs.getInt(BestellungSpalten.MENGE), this.getTeil(rs
                                .getInt(BestellungSpalten.TEILE_NUMMER)), rs
                                .getBoolean(BestellungSpalten.EINGETROFFEN)));
            }
            return bestellungen;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public Integer getMaxPeriode() {
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement
                    .executeQuery("select max(periode) as periode from bestellung");
            if (rs.next()) {
                return rs.getInt("periode");
            } else {
                return null;
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }

    public ArrayList<Bestellung> getBestellung(final boolean eingetroffen) {
        final ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        int eingetroffenDB = -9;
        if (eingetroffen)
            eingetroffenDB = 1;
        else
            eingetroffenDB = 0;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from bestellung where "
                    + BestellungSpalten.EINGETROFFEN + " = '" + eingetroffenDB
                    + "'");
            while (rs.next()) {
                bestellungen.add(new Bestellung(rs
                        .getInt(BestellungSpalten.PERIODE), BestellTyp
                        .valueOf(rs.getString(BestellungSpalten.BESTELL_TYP)),
                        rs.getInt(BestellungSpalten.MENGE), this.getTeil(rs
                                .getInt(BestellungSpalten.TEILE_NUMMER)), rs
                                .getBoolean(BestellungSpalten.EINGETROFFEN)));
            }
            return bestellungen;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public ArrayList<Teil> getTeile() {
        final ArrayList<Teil> teile = new ArrayList<Teil>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from teil");
            while (rs.next()) {
                teile.add(new Teil(rs.getInt(TeilSpalten.NUMMER), rs
                        .getString(TeilSpalten.BEZEICHNUNG), Verwendung
                        .valueOf(rs.getString(TeilSpalten.VERWENDUNG)), rs
                        .getString(TeilSpalten.BUCHSTABE), rs
                        .getDouble(TeilSpalten.WERT)));
            }
            return teile;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public boolean updateBestellungen(final FutureInwardStockMovements fism,
            final int periode) {
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;
        System.out.println(fism.toString());
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            final List<Entry> entry = fism.getEntry();
            final Iterator<Entry> iterator = entry.iterator();

            while (iterator.hasNext()) {
                final Entry next = iterator.next();
                final BigInteger itemNumber = next.getItemNumber();
                final int executeUpdate = createStatement
                        .executeUpdate("update bestellung set eingetroffen=false where teile_nummer_fk = "
                                + itemNumber + " and periode = " + periode);

                if (executeUpdate != 1) {

                    final Bestellung bestellung = new Bestellung();
                    bestellung.setEingetroffen(false);
                    bestellung.setMenge(Integer.valueOf(next.getQuantity()
                            .toString()));
                    bestellung.setPeriode(periode);
                    final Teil teil = new Teil();
                    teil.setNummer(Integer.valueOf(next.getItemNumber()
                            .toString()));
                    bestellung.setTeil(teil);
                    if (next.getSupplier().toLowerCase().equals("normal"))
                        bestellung.setBestellTyp(BestellTyp.N);
                    else
                        bestellung.setBestellTyp(BestellTyp.F);

                    final ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
                    bestellungen.add(bestellung);
                    insertBestellung(bestellungen);
                }
            }
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public boolean updateBestellungen2(final Futureinwardstockmovement fism, final int periode) {
    	
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;
        System.out.println(fism.toString());
        
        
        
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            final List<Order> entry = fism.getOrder();
            final Iterator<Order> iterator = entry.iterator();

            while (iterator.hasNext()) {
            	
                final Order next = iterator.next();
                final Integer itemNumber = next.getArticle();
                final int executeUpdate = createStatement
                        .executeUpdate("update bestellung set eingetroffen=false where teile_nummer_fk = "
                                + itemNumber + " and periode = " + periode);

                if (executeUpdate != 1) {

                    final Bestellung bestellung = new Bestellung();
                    bestellung.setEingetroffen(false);
                    bestellung.setMenge(Integer.valueOf(next.getAmount().toString()));
                    bestellung.setPeriode(periode);
                    
                    final Teil teil = new Teil();
                    teil.setNummer(Integer.valueOf(next.getArticle().toString()));
                    bestellung.setTeil(teil);
                    
                    if (next.getMode().equals(5)){
                    	bestellung.setBestellTyp(BestellTyp.N);
                    } else {
                    	// Mode 4 ist eine schnelle Bestellung, aber wir Betrachten alle andere Modi auch so
                    	bestellung.setBestellTyp(BestellTyp.F);
                    }

                    final ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
                    bestellungen.add(bestellung);
                    insertBestellung(bestellungen);
                }
            }
            return true;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    
    
    public boolean insertBestellung(final ArrayList<Bestellung> feld) {
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            String query = "";

            for (int n = 0; n < feld.size(); n++) {
                query = "insert into bestellung(periode, bestell_typ, menge, teile_nummer_fk, eingetroffen) "
                        + "values ("

                        + feld.get(n).getPeriode()
                        + ","
                        + "'"
                        + feld.get(n).getBestellTyp()
                        + "'"
                        + ","
                        + feld.get(n).getMenge()
                        + ","
                        + feld.get(n).getTeil().getNummer()
                        + ","
                        + feld.get(n).isEingetroffen() + ")";
                System.out.println(query);
                createStatement.executeUpdate(query);
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

        return true;
    }

    public Teil getTeil(final int nummer) {
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from teil where "
                    + TeilSpalten.NUMMER + " = " + nummer);
            if (rs.next()) {

                final Teil teil = new Teil(
                        rs.getInt(TeilSpalten.NUMMER),
                        rs.getString(TeilSpalten.BEZEICHNUNG),
                        Verwendung.valueOf(rs.getString(TeilSpalten.VERWENDUNG)),
                        rs.getString(TeilSpalten.BUCHSTABE), rs
                                .getDouble(TeilSpalten.WERT));
                return teil;
            } else {
                return null;
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public ArrayList<Teil> getTeile(final String buchstabe) {
        final ArrayList<Teil> teile = new ArrayList<Teil>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from teil where "
                    + TeilSpalten.BUCHSTABE + " = '" + buchstabe + "'");
            while (rs.next()) {
                teile.add(new Teil(rs.getInt(TeilSpalten.NUMMER), rs
                        .getString(TeilSpalten.BEZEICHNUNG), Verwendung
                        .valueOf(rs.getString(TeilSpalten.VERWENDUNG)), rs
                        .getString(TeilSpalten.BUCHSTABE), rs
                        .getDouble(TeilSpalten.WERT)));
            }
            return teile;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public ArrayList<Teil> getTeile(final Verwendung verwendung) {
        final ArrayList<Teil> teile = new ArrayList<Teil>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement.executeQuery("select * from teil where "
                    + TeilSpalten.VERWENDUNG + " = '" + verwendung.toString()
                    + "'");
            while (rs.next()) {
                teile.add(new Teil(rs.getInt(TeilSpalten.NUMMER), rs
                        .getString(TeilSpalten.BEZEICHNUNG), Verwendung
                        .valueOf(rs.getString(TeilSpalten.VERWENDUNG)), rs
                        .getString(TeilSpalten.BUCHSTABE), rs
                        .getDouble(TeilSpalten.WERT)));
            }
            return teile;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public HashMap<Integer, TeilLieferdaten> getTeilLieferdaten() {
        final HashMap<Integer, TeilLieferdaten> teile = new HashMap<Integer, TeilLieferdaten>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            rs = createStatement
                    .executeQuery("select lager_menge, diskont_menge, bestellkosten, wiederbeschaffungszeit_periode, "
                            + "wiederbeschaffungszeit_tage, abweichung_periode, abweichung_tage,teile_nummer_fk "
                            + "from lager_disposition;");
            while (rs.next()) {
                final TeilLieferdaten teilLieferdaten = new TeilLieferdaten();
                teilLieferdaten.setAbweichungPeriode(rs
                        .getDouble("abweichung_periode"));
                teilLieferdaten.setAbweichungTage(rs.getInt("abweichung_tage"));
                teilLieferdaten.setBestellkosten(rs.getDouble("bestellkosten"));
                teilLieferdaten.setLagerMenge(rs.getInt("lager_menge"));
                teilLieferdaten.setDiskontMenge(rs.getInt("diskont_menge"));
                teilLieferdaten.setWiederbeschaffungszeitPeriode(rs
                        .getDouble("wiederbeschaffungszeit_periode"));
                teilLieferdaten.setWiederbeschaffungszeitTage(rs
                        .getInt("wiederbeschaffungszeit_tage"));
                teilLieferdaten.setTeileNummer(rs.getInt("teile_nummer_fk"));
                teile.put(teilLieferdaten.getTeileNummer(), teilLieferdaten);
            }
            return teile;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public MengenStueckliste getMengenStueckliste() {
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        final MengenStueckliste liste = new MengenStueckliste();
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            for (int i = 1; i <= 3; i++) {
                final String query = "select ms.fahrrad_nummer, ms.teile_nummer, ms.anzahl, "
                        + "fahrrad.bezeichnung as fbezeichnung, fahrrad.nummer as fnummer, fahrrad.verwendung as fverwendung, fahrrad.wert as fwert,fahrrad.buchstabe as fbuchstabe, "
                        + "t.bezeichnung as tbezeichnung, t.nummer as tnummer, t.verwendung as tverwendung, t.wert as twert, t.buchstabe as tbuchstabe "
                        + "from mengen_stueckliste ms "
                        + "join teil fahrrad on fahrrad.nummer = "
                        + i
                        + " "
                        + "join teil t on t.nummer = ms.teile_nummer "
                        + "where fahrrad_nummer = " + i + " order by tnummer";

                rs = createStatement.executeQuery(query);
                final HashMap<Integer, MengenStuecklisteItem> teile = new HashMap<Integer, MengenStuecklisteItem>();

                while (rs.next()) {
                    final MengenStuecklisteItem mengenStuecklisteItem = new MengenStuecklisteItem();
                    mengenStuecklisteItem.setMenge(rs.getInt("anzahl"));
                    mengenStuecklisteItem.setFahrrad(new Teil(rs
                            .getInt("fnummer"), rs.getString("fbezeichnung"),
                            Verwendung.valueOf(rs.getString("fverwendung")), rs
                                    .getString("fbuchstabe"), rs
                                    .getDouble("fwert")));
                    mengenStuecklisteItem.setVerwendetesTeil(new Teil(rs
                            .getInt("tnummer"), rs.getString("tbezeichnung"),
                            Verwendung.valueOf(rs.getString("tverwendung")), rs
                                    .getString("tbuchstabe"), rs
                                    .getDouble("twert")));
                    teile.put(mengenStuecklisteItem.getVerwendetesTeil()
                            .getNummer(), mengenStuecklisteItem);
                }

                liste.setFahrrad_fk(999);
                if (i == 1)
                    liste.setTeileP1(teile);
                if (i == 2)
                    liste.setTeileP2(teile);
                if (i == 3)
                    liste.setTeileP3(teile);
            }

            return liste;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public MengenStueckliste getMengenStueckliste(final int fahrrad_nummer) {
        if (fahrrad_nummer < 1 || fahrrad_nummer > 3)
            throw new RuntimeException(
                    "die nummer muss die nummer eines Fahrrades sein. (1,2,3)");

        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            final String query = "select ms.fahrrad_nummer, ms.teile_nummer, ms.anzahl, "
                    + "fahrrad.bezeichnung as fbezeichnung, fahrrad.nummer as fnummer, fahrrad.verwendung as fverwendung, fahrrad.wert as fwert,fahrrad.buchstabe as fbuchstabe, "
                    + "t.bezeichnung as tbezeichnung, t.nummer as tnummer, t.verwendung as tverwendung, t.wert as twert, t.buchstabe as tbuchstabe "
                    + "from mengen_stueckliste ms "
                    + "join teil fahrrad on fahrrad.nummer = "
                    + fahrrad_nummer
                    + " "
                    + "join teil t on t.nummer = ms.teile_nummer "
                    + "where fahrrad_nummer = "
                    + fahrrad_nummer
                    + " order by tnummer";
            ;

            rs = createStatement.executeQuery(query);
            final HashMap<Integer, MengenStuecklisteItem> teile = new HashMap<Integer, MengenStuecklisteItem>();

            while (rs.next()) {
                final MengenStuecklisteItem mengenStuecklisteItem = new MengenStuecklisteItem();
                mengenStuecklisteItem.setMenge(rs.getInt("anzahl"));
                mengenStuecklisteItem
                        .setFahrrad(new Teil(rs.getInt("fnummer"), rs
                                .getString("fbezeichnung"), Verwendung
                                .valueOf(rs.getString("fverwendung")), rs
                                .getString("fbuchstabe"), rs.getDouble("fwert")));
                mengenStuecklisteItem
                        .setVerwendetesTeil(new Teil(rs.getInt("tnummer"), rs
                                .getString("tbezeichnung"), Verwendung
                                .valueOf(rs.getString("tverwendung")), rs
                                .getString("tbuchstabe"), rs.getDouble("twert")));
                teile.put(mengenStuecklisteItem.getVerwendetesTeil()
                        .getNummer(), mengenStuecklisteItem);
            }

            final MengenStueckliste liste = new MengenStueckliste();
            liste.setFahrrad_fk(fahrrad_nummer);
            liste.setTeileP1(teile);
            return liste;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public MengenStueckliste getMengenStueckliste(final Teil fahrrad) {
        if (fahrrad.getNummer() < 1 || fahrrad.getNummer() > 3)
            throw new RuntimeException(
                    "die nummer muss die nummer eines Fahrrades sein. (1,2,3)");

        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            final String query = "select ms.fahrrad_nummer, ms.teile_nummer, ms.anzahl, "
                    + "fahrrad.bezeichnung as fbezeichnung, fahrrad.nummer as fnummer, fahrrad.verwendung as fverwendung, fahrrad.wert as fwert,fahrrad.buchstabe as fbuchstabe, "
                    + "t.bezeichnung as tbezeichnung, t.nummer as tnummer, t.verwendung as tverwendung, t.wert as twert, t.buchstabe as tbuchstabe "
                    + "from mengen_stueckliste ms "
                    + "join teil fahrrad on fahrrad.nummer = "
                    + fahrrad.getNummer()
                    + " "
                    + "join teil t on t.nummer = ms.teile_nummer "
                    + "where fahrrad_nummer = "
                    + fahrrad.getNummer()
                    + " order by tnummer";
            ;

            rs = createStatement.executeQuery(query);
            final HashMap<Integer, MengenStuecklisteItem> teile = new HashMap<Integer, MengenStuecklisteItem>();

            while (rs.next()) {
                final MengenStuecklisteItem mengenStuecklisteItem = new MengenStuecklisteItem();
                mengenStuecklisteItem.setMenge(rs.getInt("menge"));
                mengenStuecklisteItem
                        .setFahrrad(new Teil(rs.getInt("fnummer"), rs
                                .getString("fbezeichnung"), Verwendung
                                .valueOf(rs.getString("fverwendung")), rs
                                .getString("fbuchstabe"), rs.getDouble("fwert")));
                mengenStuecklisteItem
                        .setVerwendetesTeil(new Teil(rs.getInt("tnummer"), rs
                                .getString("tbezeichnung"), Verwendung
                                .valueOf(rs.getString("tverwendung")), rs
                                .getString("tbuchstabe"), rs.getDouble("twert")));
                teile.put(mengenStuecklisteItem.getVerwendetesTeil()
                        .getNummer(), mengenStuecklisteItem);
            }

            final MengenStueckliste liste = new MengenStueckliste();
            liste.setFahrrad_fk(fahrrad.getNummer());
            liste.setTeileP1(teile);
            return liste;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public void setDispoHelp(final ArrayList<Teil_dispohelp> feld,
            final ArrayList<Integer> eins, final ArrayList<Integer> zwei,
            final ArrayList<Integer> drei) {
        resetTeileDispo();
        final String TMP_geplante_lagermenge = "0";
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        final ArrayList<Teil_dispohelp> tmp = new ArrayList<Teil_dispohelp>();
        Integer lagerebstand_help = null;
        Integer warteschlange_help = null;
        Integer bearbeitung_help = null;
        // eins
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            String query = "";
            for (int n = 0; n < eins.size(); n++) {
                for (int i = 0; i < feld.size(); i++) {
                    if (eins.get(n).equals(feld.get(i).getNummer())) {
                        tmp.add(feld.get(i));
                        // System.out.println(feld.get(i));
                    }
                }
            }
            for (int n = 0; n < tmp.size(); n++) {
                if (tmp.get(n).getNummer() == 26
                        || tmp.get(n).getNummer() == 16
                        || tmp.get(n).getNummer() == 17) {
                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode() / 3;

                    if (tmp.get(n).getAuftraege_warteschlange() != null) {
                        warteschlange_help = tmp.get(n)
                                .getAuftraege_warteschlange() / 3;
                    } else {
                        warteschlange_help = 0;
                    }
                    if (tmp.get(n).getAuftrage_bearbeitung() != null) {
                        bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung() / 3;
                    } else {
                        bearbeitung_help = 0;
                    }
                } else {
                    bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung();
                    warteschlange_help = tmp.get(n)
                            .getAuftraege_warteschlange();

                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode();

                }

                query = "insert into dispo_eins (nummer, geplante_lagermenge, lagerbestand_ende_vorperiode, auftraege_warteschlange, auftrage_bearbeitung)"
                        + "values("
                        + tmp.get(n).getNummer()
                        + ","
                        + TMP_geplante_lagermenge
                        + ","
                        + lagerebstand_help
                        + ","
                        + warteschlange_help
                        + ","
                        + bearbeitung_help
                        + ")";
                System.out.println(query);
                createStatement.executeUpdate(query);
            }
            tmp.clear();
            for (int n = 0; n < zwei.size(); n++) {
                for (int i = 0; i < feld.size(); i++) {
                    if (zwei.get(n).equals(feld.get(i).getNummer())) {
                        tmp.add(feld.get(i));
                    }
                }
            }

            for (int n = 0; n < tmp.size(); n++) {
                if (tmp.get(n).getNummer() == 26
                        || tmp.get(n).getNummer() == 16
                        || tmp.get(n).getNummer() == 17) {
                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode() / 3;

                    if (tmp.get(n).getAuftraege_warteschlange() != null) {
                        warteschlange_help = tmp.get(n)
                                .getAuftraege_warteschlange() / 3;
                    } else {
                        warteschlange_help = 0;
                    }
                    if (tmp.get(n).getAuftrage_bearbeitung() != null) {
                        bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung() / 3;
                    } else {
                        bearbeitung_help = 0;
                    }
                } else {
                    bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung();
                    warteschlange_help = tmp.get(n)
                            .getAuftraege_warteschlange();

                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode();

                }
                query = "insert into dispo_zwei (nummer, geplante_lagermenge, lagerbestand_ende_vorperiode, auftraege_warteschlange, auftrage_bearbeitung)"
                        + "values("
                        + tmp.get(n).getNummer()
                        + ","
                        + TMP_geplante_lagermenge
                        + ","
                        + lagerebstand_help
                        + ","
                        + warteschlange_help
                        + ","
                        + bearbeitung_help
                        + ")";
                createStatement.executeUpdate(query);
            }
            tmp.clear();
            for (int n = 0; n < drei.size(); n++) {
                for (int i = 0; i < feld.size(); i++) {
                    if (drei.get(n).equals(feld.get(i).getNummer())) {
                        tmp.add(feld.get(i));
                    }
                }
            }

            for (int n = 0; n < tmp.size(); n++) {
                if (tmp.get(n).getNummer() == 26
                        || tmp.get(n).getNummer() == 16
                        || tmp.get(n).getNummer() == 17) {
                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode() / 3;

                    if (tmp.get(n).getAuftraege_warteschlange() != null) {
                        warteschlange_help = tmp.get(n)
                                .getAuftraege_warteschlange() / 3;
                    } else {
                        warteschlange_help = 0;
                    }
                    if (tmp.get(n).getAuftrage_bearbeitung() != null) {
                        bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung() / 3;
                    } else {
                        bearbeitung_help = 0;
                    }
                } else {
                    bearbeitung_help = tmp.get(n).getAuftrage_bearbeitung();
                    warteschlange_help = tmp.get(n)
                            .getAuftraege_warteschlange();

                    lagerebstand_help = tmp.get(n)
                            .getLagerbestand_ende_vorperiode();

                }
                query = "insert into dispo_drei (nummer, geplante_lagermenge, lagerbestand_ende_vorperiode, auftraege_warteschlange, auftrage_bearbeitung)"
                        + "values("
                        + tmp.get(n).getNummer()
                        + ","
                        + TMP_geplante_lagermenge
                        + ","
                        + lagerebstand_help
                        + ","
                        + warteschlange_help
                        + ","
                        + bearbeitung_help
                        + ")";
                createStatement.executeUpdate(query);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
        tmp.clear();
    }

    public ArrayList<Teil_dispohelp> getTeileDispoHelp(final Integer i) {
        final ArrayList<Teil_dispohelp> feld = new ArrayList<Teil_dispohelp>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            String tabelle = "";
            if (i == 1) {
                tabelle = "dispo_eins";
            }
            if (i == 2) {
                tabelle = "dispo_zwei";
            }
            if (i == 3) {
                tabelle = "dispo_drei";
            }

            final String query = "select * from " + tabelle + " order by reihe";

            rs = createStatement.executeQuery(query);
            final HashMap<Integer, MengenStuecklisteItem> teile = new HashMap<Integer, MengenStuecklisteItem>();

            while (rs.next()) {
                final Teil_dispohelp teil = new Teil_dispohelp();

                teil.setReihe(rs.getInt("reihe"));
                teil.setNummer(rs.getInt("nummer"));
                teil.setGeplante_lagermenge(rs.getInt("geplante_lagermenge"));
                teil.setLagerbestand_ende_vorperiode(rs
                        .getInt("lagerbestand_ende_vorperiode"));
                teil.setAuftraege_warteschlange(rs
                        .getInt("auftraege_warteschlange"));
                teil.setAuftrage_bearbeitung(rs.getInt("auftrage_bearbeitung"));
                feld.add(teil);
            }

            return feld;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public void resetTeileDispo() {
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        // eins
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            String query = "delete from dispo_eins where nummer > 0";
            createStatement.executeUpdate(query);
            query = "delete from dispo_zwei where nummer > 0";
            createStatement.executeUpdate(query);
            query = "delete from dispo_drei where nummer > 0";
            createStatement.executeUpdate(query);
            query = "delete from dispo_eins_ergebniss where nummer > 0";
            createStatement.executeUpdate(query);
            query = "delete from dispo_zwei_ergebniss where nummer > 0";
            createStatement.executeUpdate(query);
            query = "delete from dispo_drei_ergebniss where nummer > 0";
            createStatement.executeUpdate(query);

        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public void updateLagermenge(final ArrayList<Teil_dispohelp> ergebnisse) {

        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            for (int n = 0; n < ergebnisse.size(); n++) {
                final String query = "update lager_disposition set lager_menge = "
                        + ergebnisse.get(n).getLagerbestand_ende_vorperiode()
                        + " where teile_nummer_fk = "
                        + ergebnisse.get(n).getNummer();
                System.out.println("yo " + query);
                createStatement.addBatch(query);
            }
            try {
                createStatement.executeBatch();
            } catch (final SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }

    public ArrayList<Teil_dispohelp> getDispoHelpErgebniss(final Integer i) {

        final ArrayList<Teil_dispohelp> feld = new ArrayList<Teil_dispohelp>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            String tabelle = "";
            if (i == 1) {
                tabelle = "dispo_eins_ergebniss";
            }
            if (i == 2) {
                tabelle = "dispo_zwei_ergebniss";
            }
            if (i == 3) {
                tabelle = "dispo_drei_ergebniss";
            }

            final String query = "select * from " + tabelle + " order by reihe";

            rs = createStatement.executeQuery(query);

            while (rs.next()) {
                final Teil_dispohelp teil = new Teil_dispohelp();
                teil.setVertriebswunsch(rs.getInt("vertriebswunsch"));
                teil.setReihe(rs.getInt("reihe"));
                teil.setNummer(rs.getInt("nummer"));
                teil.setGeplante_lagermenge(rs.getInt("geplante_lagermenge"));
                teil.setLagerbestand_ende_vorperiode(rs
                        .getInt("lagerbestand_ende_vorperiode"));
                teil.setAuftraege_warteschlange(rs
                        .getInt("auftraege_warteschlange"));
                teil.setAuftrage_bearbeitung(rs.getInt("auftrage_bearbeitung"));
                teil.setProduktionsauftrag_naechste_periode(rs
                        .getInt("produktionsauftrag_naechste_periode"));
                feld.add(teil);

            }

            return feld;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public void setDispoHelpErgebniss(
            final ArrayList<Teil_dispohelp> ergebnisseBerechnern) {
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        //
        // try {
        // connection = getConnection();
        // createStatement = connection.createStatement();
        //
        // for (int n = 0; n < ergebnisseBerechnern.size(); n++) {
        // final String query =
        // "insert into dispo_ergebniss(nummer, menge) values ("
        // + ergebnisseBerechnern.get(n).getNummer()
        // + ","
        // + ergebnisseBerechnern.get(n)
        // .getProduktionsauftrag_naechste_periode() + ")";
        // System.out.println("yo_sdh " + query);
        // createStatement.addBatch(query);
        // }
        // try {
        // createStatement.executeBatch();
        // } catch (final SQLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // } catch (final SQLException e) {
        // e.printStackTrace();
        // return;
        // } finally {
        // DbHelper.closeAll(connection, rs, createStatement);
        // }
        String tabelle = "";

        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            if (ergebnisseBerechnern.get(0).getNummer() == 1) {
                tabelle = "dispo_eins_ergebniss";
            }
            if (ergebnisseBerechnern.get(0).getNummer() == 2) {
                tabelle = "dispo_zwei_ergebniss";
            }

            if (ergebnisseBerechnern.get(0).getNummer() == 3) {
                tabelle = "dispo_drei_ergebniss";
            }

            for (int n = 0; n < ergebnisseBerechnern.size(); n++) {
                final String query = "insert into "
                        + tabelle
                        + "(vertriebswunsch, nummer, geplante_lagermenge, lagerbestand_ende_vorperiode, auftraege_warteschlange, auftrage_bearbeitung, produktionsauftrag_naechste_periode)"
                        + "values("
                        + ergebnisseBerechnern.get(n).getVertriebswunsch()
                        + ","
                        + ergebnisseBerechnern.get(n).getNummer()
                        + ","
                        + ergebnisseBerechnern.get(n).getGeplante_lagermenge()
                        + ","
                        + ergebnisseBerechnern.get(n)
                                .getLagerbestand_ende_vorperiode()
                        + ","
                        + ergebnisseBerechnern.get(n)
                                .getAuftraege_warteschlange()
                        + ","
                        + ergebnisseBerechnern.get(n).getAuftrage_bearbeitung()
                        + ","
                        + ergebnisseBerechnern.get(n)
                                .getProduktionsauftrag_naechste_periode() + ")";
                System.out.println("ergebniss " + query);
                createStatement.addBatch(query);
                createStatement.executeBatch();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }

    public void resetTeileDispoErgebniss() {
        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        // eins
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            final String query = "delete from dispo_ergebniss where nummer > 0";
            createStatement.executeUpdate(query);

        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public ArrayList<APTeil> getDispoHelpErgebnissGut() {

        final ArrayList<APTeil> feld = new ArrayList<APTeil>();
        Connection connection = null;
        Statement createStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            final String tabelle = "dispo_ergebniss";

            final String query = "select * from " + tabelle;

            rs = createStatement.executeQuery(query);

            Integer index = 0; 
            while (rs.next()) {
                final APTeil a = new APTeil();
                a.setAnzahl(rs.getInt("menge"));
                a.setNummer(rs.getInt("nummer"));
                a.setIndex(++index);
                feld.add(a);

            }

            // for (int n = 0; n < feld.size(); n++) {
            // System.out.println("feld " + feld.get(n));
            // }
            return feld;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }
    }

    public void OrderDispoHelpErgebniss() {
        final ArrayList<APTeil> feld = getDispoHelpErgebnissGut();
        final ArrayList<Integer> ints = new ArrayList<Integer>();
        final ArrayList<APTeil> feldOrdered = new ArrayList<APTeil>();
        ints.add(4);
        ints.add(5);
        ints.add(6);
        ints.add(7);
        ints.add(8);
        ints.add(9);
        ints.add(10);
        ints.add(11);
        ints.add(12);
        ints.add(13);
        ints.add(14);
        ints.add(15);
        ints.add(16);
        ints.add(17);
        ints.add(18);
        ints.add(19);
        ints.add(20);

        ints.add(26);
        ints.add(49);
        ints.add(54);
        ints.add(29);
        ints.add(50);
        ints.add(55);
        ints.add(30);
        ints.add(51);
        ints.add(56);
        ints.add(31);
        ints.add(1);
        ints.add(2);
        ints.add(3);
        Integer sechzehn = 0;
        Integer siebzehn = 0;
        Integer sechsundzwanzig = 0;
        System.out.println("size davor " + feld.size());
        for (int n = 0; n < feld.size(); n++) {
            System.out.println("feld bevor " + feld.get(n));
        }
        for (int n = 0; n < ints.size(); n++) {
            for (int i = 0; i < feld.size(); i++) {

                if (ints.get(n).equals(feld.get(i).getNummer())) {
                    feldOrdered.add(feld.get(i));
                }
            }
        }
        for (int i = 0; i < feldOrdered.size(); i++) {
            System.out.println("feld kurz danach " + feldOrdered.get(i));
        }

        final ArrayList<APTeil> feldOrderedGUT = new ArrayList<APTeil>();
        for (int i = 0; i < feldOrdered.size(); i++) {

            if (feldOrdered.get(i).getNummer().equals(16)) {
                System.out.println("16");
                sechzehn += feldOrdered.get(i).getAnzahl();
                sechzehn += feldOrdered.get(i + 1).getAnzahl();
                sechzehn += feldOrdered.get(i + 2).getAnzahl();
                feldOrdered.get(i).setAnzahl(sechzehn);
                feldOrderedGUT.add(feldOrdered.get(i));
                // feldOrdered.remove(i + 1);
                // feldOrdered.remove(i + 2);
                i += 2;
                continue;
            }

            if (feldOrdered.get(i).getNummer().equals(17)) {
                System.out.println("17");
                siebzehn += feldOrdered.get(i).getAnzahl();
                siebzehn += feldOrdered.get(i + 1).getAnzahl();
                siebzehn += feldOrdered.get(i + 2).getAnzahl();
                feldOrdered.get(i).setAnzahl(siebzehn);
                feldOrderedGUT.add(feldOrdered.get(i));
                // feldOrdered.remove(i + 1);
                // feldOrdered.remove(i + 2);
                i += 2;
                continue;
            }

            if (feldOrdered.get(i).getNummer().equals(26)) {
                System.out.println("26");
                sechsundzwanzig += feldOrdered.get(i).getAnzahl();
                sechsundzwanzig += feldOrdered.get(i + 1).getAnzahl();
                sechsundzwanzig += feldOrdered.get(i + 2).getAnzahl();
                feldOrdered.get(i).setAnzahl(sechsundzwanzig);
                feldOrderedGUT.add(feldOrdered.get(i));
                // feldOrdered.remove(i + 1);
                // feldOrdered.remove(i + 2);
                i += 2;
                continue;
            }

            feldOrderedGUT.add(feldOrdered.get(i));

        }

        System.out.println("size danach " + feldOrderedGUT.size());
        for (int i = 0; i < feldOrderedGUT.size(); i++) {
            System.out.println("feld danach GUT" + feldOrderedGUT.get(i));
        }
        resetTeileDispoErgebniss();

        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;
        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            for (int n = 0; n < feldOrderedGUT.size(); n++) {
                final String query = "insert into dispo_ergebniss (nummer, menge) values ( "
                        + feldOrderedGUT.get(n).getNummer()
                        + " , "
                        + feldOrderedGUT.get(n).getAnzahl() + ")";
                System.out.println("yo " + query);
                createStatement.addBatch(query);
            }
            try {
                createStatement.executeBatch();
            } catch (final SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }

    public void resetTeileDispoErgebniss(Integer i) {
        String tabelle = "";
        if (i == 1) {
            tabelle = "dispo_eins_ergebniss";
        }
        if (i == 2) {
            tabelle = "dispo_zwei_ergebniss";
        }
        if (i == 3) {
            tabelle = "dispo_drei_ergebniss";
        }

        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        // eins
        try {
            connection = getConnection();
            createStatement = connection.createStatement();
            final String query = "delete from " + tabelle + " where nummer > 0";
            System.out.println(query);
            createStatement.executeUpdate(query);

        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }

    public void setDispoErgebnissGut() {
        final ArrayList<Teil_dispohelp> eins = getDispoHelpErgebniss(1);
        final ArrayList<Teil_dispohelp> zwei = getDispoHelpErgebniss(2);
        final ArrayList<Teil_dispohelp> drei = getDispoHelpErgebniss(3);

        resetTeileDispoErgebniss();

        Connection connection = null;
        Statement createStatement = null;
        final ResultSet rs = null;

        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            for (int n = 0; n < eins.size(); n++) {
                final String query = "insert into dispo_ergebniss(nummer, menge) values ("
                        + eins.get(n).getNummer()
                        + ","
                        + eins.get(n).getProduktionsauftrag_naechste_periode()
                        + ")";
                System.out.println("yo_sdh " + query);
                createStatement.addBatch(query);
            }
            try {
                createStatement.executeBatch();
            } catch (final SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            for (int n = 0; n < zwei.size(); n++) {
                final String query = "insert into dispo_ergebniss(nummer, menge) values ("
                        + zwei.get(n).getNummer()
                        + ","
                        + zwei.get(n).getProduktionsauftrag_naechste_periode()
                        + ")";
                System.out.println("yo_sdh " + query);
                createStatement.addBatch(query);
            }
            try {
                createStatement.executeBatch();
            } catch (final SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

        try {
            connection = getConnection();
            createStatement = connection.createStatement();

            for (int n = 0; n < drei.size(); n++) {
                final String query = "insert into dispo_ergebniss(nummer, menge) values ("
                        + drei.get(n).getNummer()
                        + ","
                        + drei.get(n).getProduktionsauftrag_naechste_periode()
                        + ")";
                System.out.println("yo_sdh " + query);
                createStatement.addBatch(query);
            }
            try {
                createStatement.executeBatch();
            } catch (final SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            DbHelper.closeAll(connection, rs, createStatement);
        }

    }


	public void resetDatabaseQuery() throws SQLException {
		
		
		ProcessBuilder builder  = new ProcessBuilder("/bin/sh", "-c", "mysql -u root -p ibsys < /home/vif/git/ibsys2Tool/assets/scripts/schema.sql");
		ProcessBuilder builder2  = new ProcessBuilder("/bin/sh", "-c", "mysql -u root -p ibsys < /home/vif/git/ibsys2Tool/assets/scripts/data.sql");
		
		try {
			builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			builder2.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//        Connection connection = null;
//        Statement createStatement = null;
//        
//        try {
//            connection = getConnection();
//            createStatement = connection.createStatement();
//            
//            
//String query =   		"USE `ibsys`;";
////createStatement.executeUpdate(query);
//createStatement.addBatch(query);
//
//String query00 =   		"SET FOREIGN_KEY CHECKS=0;";
////createStatement.executeUpdate(query00);
//createStatement.addBatch(query00);
//
//String query13 ="truncate table `teil`;                      " ;
////createStatement.executeUpdate(query13);
//createStatement.addBatch(query13);
//
//			
//String query1 = " truncate table `arbeitsplatz_daten`; ";
////createStatement.executeUpdate(query1);
//createStatement.addBatch(query1);
//
//String query2 =" truncate table `bestellung`;  ";
////createStatement.executeUpdate(query2);
//createStatement.addBatch(query2);
//
//
//String query3 = "truncate table `dispo_drei`;                " ;
////createStatement.executeUpdate(query3);
//createStatement.addBatch(query3);
//
//String query4 = "truncate table `dispo_drei_ergebniss`;      " ;
////createStatement.executeUpdate(query4);
//createStatement.addBatch(query4);
//
//String query5 = "truncate table `dispo_eins`;                " ;
////createStatement.executeUpdate(query5);
//createStatement.addBatch(query5);
//
//String query6 = "truncate table `dispo_eins_ergebniss`;      " ;
////createStatement.executeUpdate(query6);
//createStatement.addBatch(query6);
//
//String query7 = "truncate table `dispo_ergebniss`;           " ;
////createStatement.executeUpdate(query7);
//createStatement.addBatch(query7);
//
//String query8 = "truncate table `dispo_zwei`;                " ;
////createStatement.executeUpdate(query8);
//createStatement.addBatch(query8);
//
//String query9 = "truncate table `dispo_zwei_ergebniss`;      " ;
////createStatement.executeUpdate(query9);
//createStatement.addBatch(query9);
//
//String query10 ="truncate table `lager_disposition`;         " ;
////createStatement.executeUpdate(query10);
//createStatement.addBatch(query10);
//
//String query11 ="truncate table `mengen_stueckliste`;        " ;
////createStatement.executeUpdate(query11);
//createStatement.addBatch(query11);
//
//String query12 ="truncate table `prognose`;                  " ;
////createStatement.executeUpdate(query12);
//createStatement.addBatch(query12);
//
//
//String query14 = "truncate table `teil_ist_in_teil`;  " ;
////createStatement.executeUpdate(query14);
//createStatement.addBatch(query14);
//
//
//String query01 =   		"SET FOREIGN_KEY CHECKS=1;";
//createStatement.executeQuery(query01);
//createStatement.addBatch(query01);
//
//
//String query15 = "	LOCK TABLES `arbeitsplatz_daten` WRITE;  ";
////createStatement.executeQuery(query15);
//createStatement.addBatch(query15);
//
//String query16 =  "		INSERT INTO `arbeitsplatz_daten` VALUES (1,0.45,0.55,0.7,0.9,0.05,0.01),(2,0.45,0.55,0.7,0.9,0.05,0.01),(3,0.45,0.55,0.7,0.9,0.05,0.01),(4,0.45,0.55,0.7,0.9,0.05,0.01),(6,0.45,0.55,0.7,0.9,0.3,0.1),(7,0.45,0.55,0.7,0.9,0.3,0.1),(8,0.45,0.55,0.7,0.9,0.3,0.1),(9,0.45,0.55,0.7,0.9,0.8,0.25),(10,0.45,0.55,0.7,0.9,0.3,0.1),(11,0.45,0.55,0.7,0.9,0.3,0.1),(12,0.45,0.55,0.7,0.9,0.3,0.1),(13,0.45,0.55,0.7,0.9,0.5,0.15),(14,0.45,0.55,0.7,0.9,0.05,0.01),(15,0.45,0.55,0.7,0.9,0.05,0.01); ";
//String query18 =  "	UNLOCK TABLES; ";
//String query20 =  "	LOCK TABLES `bestellung` WRITE;  ";
//String query22 =  "	UNLOCK TABLES; ";
//String query24 =  "	LOCK TABLES `dispo_drei` WRITE; ";
//String query25 =  "		UNLOCK TABLES; "	;
//String query27 =  "	LOCK TABLES `dispo_drei_ergebniss` WRITE; ";
//String query29 =  "	UNLOCK TABLES; ";
//String query31 =  "	LOCK TABLES `dispo_eins` WRITE; ";
//String query32 =  "		UNLOCK TABLES; ";
//String query34 =  "	LOCK TABLES `dispo_eins_ergebniss` WRITE;  ";
//String query36 =  "	UNLOCK TABLES;  ";
//String query38 =  "	LOCK TABLES `dispo_ergebniss` WRITE;  ";
//String query39 =  "	UNLOCK TABLES;  ";
//		
//String query40 =   "	LOCK TABLES `dispo_zwei` WRITE;  ";


//	+ "	UNLOCK TABLES;   "
//
//	+ "	LOCK TABLES `dispo_zwei_ergebniss` WRITE;   "
//		
//	+ "	UNLOCK TABLES;   "
//
//	+ "	LOCK TABLES `lager_disposition` WRITE;   "
//	 	
//	+ "	INSERT INTO `lager_disposition` VALUES (300,300,50,1.8,9,0.4,2,21),(300,300,50,1.7,9,0.4,2,22),(300,300,50,1.2,6,0.2,1,23),(6100,6100,100,3.2,16,0.3,2,24),(3600,3600,50,0.9,5,0.2,1,25),(1800,1800,75,0.9,5,0.2,1,27),(4500,4500,50,1.7,9,0.4,2,28),(2700,2700,50,2.1,11,0.5,3,32),(900,900,75,1.9,10,0.5,3,33),(22000,22000,50,1.6,8,0.3,2,34),(3600,3600,75,2.2,11,0.4,2,35),(900,900,100,1.2,6,0.1,1,36),(900,900,50,1.5,8,0.3,2,37),(300,300,50,1.7,9,0.4,2,38),(900,1800,75,1.5,8,0.3,2,39),(900,900,50,1.7,9,0.2,1,40),(900,900,50,0.9,5,0.2,1,41),(1800,1800,50,1.2,6,0.3,2,42),(1900,2700,75,2,10,0.5,3,43),(2700,900,50,1,5,0.2,1,44),(900,900,50,1.7,9,0.3,2,45),(900,900,50,0.9,5,0.3,2,46),(900,900,50,1.1,6,0.1,1,47),(1800,1800,75,1,5,0.2,1,48),(600,600,50,1.6,8,0.4,2,52),(22000,22000,50,1.6,8,0.2,1,53),(600,600,50,1.7,9,0.3,2,57),(22000,22000,50,1.6,8,0.5,3,58),(1800,1800,50,0.7,4,0.2,1,59);   "
//		
//	+ "	UNLOCK TABLES;  "
//
//	+ "	LOCK TABLES `mengen_stueckliste` WRITE;  "
//	+ " INSERT INTO `mengen_stueckliste` VALUES (1,4,1),(1,7,1),(1,10,1),(1,13,1),(1,16,1),(1,17,1),(1,18,1),(1,26,1),(1,49,1),(1,50,1),(1,51,1),(1,21,1),(1,24,7),(1,25,4),(1,27,2),(1,28,4),(1,32,3),(1,35,4),(1,36,1),(1,37,1),(1,38,1),(1,39,2),(1,40,1),(1,41,1),(1,42,2),(1,43,1),(1,44,3),(1,45,1),(1,46,1),(1,47,1),(1,48,2),(1,52,2),(1,53,72),(1,59,2),(2,5,1),(2,8,1),(2,11,1),(2,14,1),(2,16,1),(2,17,1),(2,19,1),(2,26,1),(2,54,1),(2,55,1),(2,56,1),(2,22,1),(2,24,7),(2,25,4),(2,27,2),(2,28,5),(2,32,3),(2,35,4),(2,36,1),(2,37,1),(2,38,1),(2,39,2),(2,40,1),(2,41,1),(2,42,2),(2,43,1),(2,44,3),(2,45,1),(2,46,1),(2,47,1),(2,48,2),(2,57,2),(2,58,72),(2,59,2),(3,6,1),(3,9,1),(3,12,1),(3,15,1),(3,16,1),(3,17,1),(3,20,1),(3,26,1),(3,29,1),(3,30,1),(3,31,1),(3,23,1),(3,24,7),(3,25,4),(3,27,2),(3,28,6),(3,32,3),(3,33,2),(3,34,72),(3,35,4),(3,36,1),(3,37,1),(3,38,1),(3,39,2),(3,40,1),(3,41,1),(3,42,2),(3,43,1),(3,44,3),(3,45,1),(3,46,1),(3,47,1),(3,48,2),(3,59,2);   "
//	
//	+ "	UNLOCK TABLES;  "
//
//		
//	+ "	LOCK TABLES `prognose` WRITE;  "
//		
//	+ "	UNLOCK TABLES;  "
//
//	+ "	LOCK TABLES `teil` WRITE;  "
//	+ "	INSERT INTO `teil` VALUES (1,'P','Kinderfahrrad','NIX',156.13),(2,'P','Damenfahrrad','NIX',163.33),(3,'P','Herrenfahrrad','NIX',165.08),(4,'E','Hinterradgruppe','K',40.85),(5,'E','Hinterradgruppe','D',39.85),(6,'E','Hinterradgruppe','H',40.85),(7,'E','Vorderradgruppe','K',35.85),(8,'E','Vorderradgruppe','D',35.85),(9,'E','Vorderradgruppe','H',35.85),(10,'E','Schutzblech h.','K',12.4),(11,'E','Schutzblech h.','D',14.65),(12,'E','Schutzblech h.','H',14.65),(13,'E','Schutzblech v.','K',12.4),(14,'E','Schutzblech v.','D',14.65),(15,'E','Schutzblech v.','H',14.65),(16,'E','Lenker cpl.','KDH',7.02),(17,'E','Sattel cpl.','KDH',7.16),(18,'E','Rahmen','K',13.15),(19,'E','Rahmen','D',14.35),(20,'E','Rahmen','H',15.55),(21,'K','Kette','K',5),(22,'K','Kette','D',6.5),(23,'K','Kette','H',6.5),(24,'K','Mutter 3/8','KDH',0.06),(25,'K','Scheibe 3/8','KDH',0.06),(26,'E','Pedal cpl.','KDH',10.5),(27,'K','Schraube 3/8','KDH',0.1),(28,'K','Rohr 3/4','KDH',1.2),(29,'E','Vorderrad mont.','H',69.29),(30,'E','Rahmen u. Raeder','H',127.53),(31,'E','Fahrrad o. Ped.','H',144.42),(32,'K','Farbe','KDH',0.75),(33,'K','Felde cpl','H',22),(34,'K','Speiche','H',0.1),(35,'K','Nabe','KDH',1),(36,'K','Freilauf','KDH',8),(37,'K','Gabel','KDH',1.5),(38,'K','Welle','KDH',1.5),(39,'K','Blech','KDH',1.5),(40,'K','Lenker','KDH',2.5),(41,'K','Mutter 3/4','KDH',0.06),(42,'K','Griff','KDH',0.1),(43,'K','Sattel','KDH',5),(44,'K','Stange 1/2','KDH',0.5),(45,'K','Mutter 1/4','KDH',0.06),(46,'K','Schraube 1/4','KDH',0.1),(47,'K','Zahnkranz','KDH',3.5),(48,'K','Pedal','KDH',1.5),(49,'E','Vorderrad cpl.','K',64.64),(50,'E','Rahmen u. Raeder','K',120.63),(51,'E','Fahrrad o. Pedal','K',137.47),(52,'K','Felge cpl.','K',22),(53,'K','Speiche','K',0.1),(54,'E','Vorderrad cpl.','D',68.09),(55,'E','Rahmen u. Raeder.','D',125.33),(56,'E','Fahrrad o. Pedal','D',142.67),(57,'K','Felge cpl.','D',22),(58,'K','Speiche','D',0.1),(59,'K','Schweissdraht','KDH',0.15);  "
//	+ "	UNLOCK TABLES;  "
//
//	+ "	LOCK TABLES `teil_ist_in_teil` WRITE;  "
//	+ "	INSERT INTO `teil_ist_in_teil` VALUES (13,39,1),(13,32,1),(18,28,3),(18,59,2),(18,32,1),(7,52,1),(7,53,36),(7,35,2),(7,37,1),(7,38,1),(49,13,1),(49,18,1),(49,7,1),(49,24,2),(49,25,2),(4,52,1),(4,53,36),(4,35,2),(4,36,1),(10,39,1),(10,32,1),(17,43,1),(17,44,1),(17,45,1),(17,46,1),(16,28,1),(16,24,1),(16,40,1),(16,41,1),(16,42,2),(50,49,1),(50,4,1),(50,10,1),(50,24,2),(50,25,2),(51,17,1),(51,16,1),(51,50,1),(51,24,1),(51,27,1),(26,44,2),(26,48,2),(26,47,1),(1,51,1),(1,26,1),(1,21,1),(1,24,1),(1,27,1),(2,22,1),(2,24,1),(2,27,1),(2,26,1),(2,56,1),(56,24,1),(56,27,1),(56,16,1),(56,17,1),(56,55,1),(55,24,2),(55,25,2),(55,5,1),(55,11,1),(55,54,1),(5,35,2),(5,36,1),(5,57,1),(5,58,36),(11,32,1),(11,39,1),(54,24,2),(54,25,2),(54,8,1),(54,14,1),(54,19,1),(8,35,2),(8,37,1),(8,38,1),(8,57,1),(8,58,36),(14,32,1),(14,39,1),(19,28,4),(19,32,1),(19,59,2),(3,23,1),(3,24,1),(3,27,1),(3,26,1),(3,31,1),(31,24,1),(31,27,1),(31,16,1),(31,17,1),(31,30,1),(30,24,2),(30,25,2),(30,6,1),(30,12,1),(30,29,1),(6,33,1),(6,34,36),(6,35,2),(6,36,1),(12,32,1),(12,39,1),(29,24,2),(29,25,2),(29,9,1),(29,15,1),(29,20,1),(9,33,1),(9,34,36),(9,35,2),(9,37,1),(9,38,1),(15,32,1),(15,39,1),(20,28,5),(20,32,1),(20,59,2);  "
//		
//	+ "	UNLOCK TABLES;  ";
	
//int[] count  = createStatement.executeBatch();
//connection.commit();
//
//
//        } catch (final SQLException e) {
//            e.printStackTrace();
//        } finally {
//        	
//        	if(connection != null && !connection.isClosed())
//        		connection.close();
//        }
//		
//	}
}
	}
