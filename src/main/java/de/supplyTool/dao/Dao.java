package de.supplyTool.dao;

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

}
