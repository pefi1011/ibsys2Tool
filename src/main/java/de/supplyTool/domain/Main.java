package de.supplyTool.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import de.supplyTool.dao.Dao;
import de.supplyTool.dao.Util.JaxbMarshalUnmarshalUtil;
import de.supplyTool.generated.Entry;
import de.supplyTool.generated.PeriodResults;

public class Main {

    public static void main(final String[] args) {

        final ArrayList<APArbeitsplatz> arbeit = new ArrayList<APArbeitsplatz>();
        arbeit.add(new APArbeitsplatz(null, 1, null, null, 60, null, null, 1.1));
        final ArrayList<APTeil> a1 = new ArrayList<APTeil>();
        a1.add(new APTeil(49, null, 6));
        a1.add(new APTeil(54, null, 6));
        a1.add(new APTeil(29, null, 6));
        arbeit.get(0).setTeile(a1);

        arbeit.add(new APArbeitsplatz(null, 2, null, null, 80, null, null, 1.1));
        final ArrayList<APTeil> a2 = new ArrayList<APTeil>();
        a2.add(new APTeil(50, null, 5));
        a2.add(new APTeil(55, null, 5));
        a2.add(new APTeil(30, null, 5));
        arbeit.get(1).setTeile(a2);

        arbeit.add(new APArbeitsplatz(null, 3, null, null, 60, null, null, 1.1));
        final ArrayList<APTeil> a3 = new ArrayList<APTeil>();
        a3.add(new APTeil(51, null, 5));
        a3.add(new APTeil(56, null, 6));
        a3.add(new APTeil(31, null, 6));
        arbeit.get(2).setTeile(a3);

        arbeit.add(new APArbeitsplatz(null, 4, null, null, 80, null, null, 1.1));
        final ArrayList<APTeil> a4 = new ArrayList<APTeil>();
        a4.add(new APTeil(1, null, 6));
        a4.add(new APTeil(2, null, 7));
        a4.add(new APTeil(3, null, 7));
        arbeit.get(3).setTeile(a4);

        arbeit.add(new APArbeitsplatz(null, 6, null, null, 90, null, null, 1.1));
        final ArrayList<APTeil> a5 = new ArrayList<APTeil>();
        a5.add(new APTeil(16, null, 2));
        a5.add(new APTeil(18, null, 3));
        a5.add(new APTeil(19, null, 3));
        a5.add(new APTeil(20, null, 3));
        arbeit.get(4).setTeile(a5);

        arbeit.add(new APArbeitsplatz(null, 7, null, null, 210, null, null, 1.1));
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

        arbeit.add(new APArbeitsplatz(null, 8, null, null, 155, null, null, 1.1));
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

        arbeit.add(new APArbeitsplatz(null, 9, null, null, 140, null, null, 1.1));
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

        arbeit.add(new APArbeitsplatz(null, 10, null, null, 120, null, null, 1.1));
        final ArrayList<APTeil> a9 = new ArrayList<APTeil>();
        a9.add(new APTeil(4, null, 4));
        a9.add(new APTeil(5, null, 4));
        a9.add(new APTeil(6, null, 4));
        a9.add(new APTeil(7, null, 4));
        a9.add(new APTeil(8, null, 4));
        a9.add(new APTeil(9, null, 4));

        arbeit.get(8).setTeile(a9);

        arbeit.add(new APArbeitsplatz(null, 11, null, null, 120, null, null, 1.1));
        final ArrayList<APTeil> a10 = new ArrayList<APTeil>();
        a10.add(new APTeil(4, null, 3));
        a10.add(new APTeil(5, null, 3));
        a10.add(new APTeil(6, null, 3));
        a10.add(new APTeil(7, null, 3));
        a10.add(new APTeil(8, null, 3));
        a10.add(new APTeil(9, null, 3));

        arbeit.get(9).setTeile(a10);

        arbeit.add(new APArbeitsplatz(null, 12, null, null, 0, null, null, 1.1));
        final ArrayList<APTeil> a11 = new ArrayList<APTeil>();
        a11.add(new APTeil(10, null, 3));
        a11.add(new APTeil(11, null, 3));
        a11.add(new APTeil(12, null, 3));
        a11.add(new APTeil(13, null, 3));
        a11.add(new APTeil(14, null, 3));
        a11.add(new APTeil(15, null, 3));

        arbeit.get(10).setTeile(a11);

        arbeit.add(new APArbeitsplatz(null, 13, null, null, 0, null, null, 1.1));
        final ArrayList<APTeil> a12 = new ArrayList<APTeil>();
        a12.add(new APTeil(10, null, 2));
        a12.add(new APTeil(11, null, 2));
        a12.add(new APTeil(12, null, 2));
        a12.add(new APTeil(13, null, 2));
        a12.add(new APTeil(14, null, 2));
        a12.add(new APTeil(15, null, 2));

        arbeit.get(11).setTeile(a12);

        arbeit.add(new APArbeitsplatz(null, 14, null, null, 0, null, null, 1.1));
        final ArrayList<APTeil> a13 = new ArrayList<APTeil>();
        a13.add(new APTeil(16, null, 3));

        arbeit.get(12).setTeile(a13);

        arbeit.add(new APArbeitsplatz(null, 15, null, null, 30, null, null, 1.1));
        final ArrayList<APTeil> a14 = new ArrayList<APTeil>();
        a14.add(new APTeil(17, null, 3));
        a14.add(new APTeil(26, null, 3));

        arbeit.get(13).setTeile(a14);

        for (int n = 0; n < arbeit.size(); ++n) {
            arbeit.get(n).setInBearbeitung(0);
            arbeit.get(n).setUeberstunden(0);
            arbeit.get(n).setWarteschlange(0);
        }

        final Dao dao = new Dao();
        final ArrayList<APTeil> erg = dao.getDispoHelpErgebnissGut();

        // for (int n = 0; n < erg.size(); n++) {
        // System.out.println("erg " + erg.get(n));
        // }

        for (int n = 0; n < arbeit.size(); n++) {
            for (int i = 0; i < arbeit.get(n).getTeile().size(); i++) {
                for (int o = 0; o < erg.size(); o++) {

                    if (erg.get(o).getNummer() == arbeit.get(n).getTeile()
                            .get(i).getNummer()) {
                        arbeit.get(n).getTeile().get(i)
                                .setAnzahl(erg.get(o).getAnzahl());
                        //
                        // System.out.println("yoasdasd "
                        // + arbeit.get(n).getTeile().get(i));

                    }
                }
            }

        }
        PeriodResults e = null;
        try {

            e = JaxbMarshalUnmarshalUtil.unmarshal("C:/dev/ausgabe.xsd",
                    "C:/dev/ausgabe.xml", PeriodResults.class);
        } catch (final JAXBException e1) {
            // TODO Auto-generated catch block
            System.out.println("1");
            e1.printStackTrace();
        } catch (final SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        List<Entry> feld2 = new ArrayList<Entry>();
        feld2 = e.getWorkplaceWaitinglist().getEntry();
        final ArrayList<APHelp> helps = new ArrayList<APHelp>();

        for (int n = 0; n < feld2.size(); n++) {

            final Entry entry = feld2.get(n);
            final APHelp h = new APHelp();
            h.setNummer(feld2.get(n).getWorkplaceNumber());
            h.setWarteschlange(feld2.get(n).getRequiredTime());
            helps.add(h);
            System.out.println("feld 2" + feld2.get(n).getWorkplaceNumber()
                    + " " + feld2.get(n).getRequiredTime());
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

        // for (int i = 0; i < helps2.size(); i++) {
        // System.out.println("sasdasdad " + helps2.get(i));
        // }

        List<Entry> feld3 = new ArrayList<Entry>();
        feld3 = e.getOrdersBeeingProcessed().getEntry();

        for (int n = 0; n < helpsGUT.size(); n++) {
            helpsGUT.get(n).setBearbeitung(BigInteger.valueOf(0));

            if (helpsGUT.get(n).getNummer()
                    .equals(feld3.get(0).getWorkplaceNumber())) {
                helpsGUT.get(n).setBearbeitung(feld3.get(0).getCycleTime());
            }
            System.out.println("nigga " + helpsGUT.get(n));
        }

        for (int i = 0; i < arbeit.size(); i++) {
            for (int n = 0; n < helpsGUT.size(); n++) {
                if (arbeit.get(i).getNummer() == helpsGUT.get(n).getNummer()
                        .intValue()) {
                    arbeit.get(i).setInBearbeitung(
                            helpsGUT.get(n).getBearbeitung().intValue());
                    arbeit.get(i).setWarteschlange(
                            helpsGUT.get(n).getWarteschlange().intValue());
                }
            }
        }

        final Integer uberstunden;
        final Integer schicht;

        for (int i = 0; i < arbeit.size(); i++) {

            arbeit.get(i).setUeberstunden(
                    arbeit.get(i).getInBearbeitung()
                            + arbeit.get(i).getWarteschlange()
                            + arbeit.get(i).getRuestzeit());

            for (int n = 0; n < arbeit.get(i).getTeile().size(); n++) {
                arbeit.get(i).setUeberstunden(
                        arbeit.get(i).getUeberstunden()
                                + arbeit.get(i).getTeile().get(n).getAnzahl()
                                * +arbeit.get(i).getTeile().get(n)
                                        .getZeitProTeil());
            }

            arbeit.get(i).setUeberstunden(
                    (int) (arbeit.get(i).getUeberstunden() * arbeit.get(i)
                            .getAufschlag()));

            arbeit.get(i)
                    .setUeberstunden(
                            (int) (Math.rint((double) arbeit.get(i)
                                    .getUeberstunden() / 10) * 10));

            if (arbeit.get(i).getUeberstunden() > 6666) {
                arbeit.get(i).setSchicht(3);
                if (arbeit.get(i).getUeberstunden() > 7200) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden() - 7200);

                } else {
                    arbeit.get(i).setUeberstundenGut(0);
                }
                continue;
            }

            if (arbeit.get(i).getUeberstunden() > 3720) {
                arbeit.get(i).setSchicht(2);
                if (arbeit.get(i).getUeberstunden() > 4800) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden() - 4800);

                } else {
                    arbeit.get(i).setUeberstundenGut(0);
                }
                continue;
            }

            if (arbeit.get(i).getUeberstunden() > 2400) {
                arbeit.get(i).setSchicht(1);
                if (arbeit.get(i).getUeberstunden() > 2400) {
                    arbeit.get(i).setUeberstundenGut(
                            arbeit.get(i).getUeberstunden() - 2400);

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

        for (int n = 0; n < arbeit.size(); n++) {
            System.out.println("YOYOYO " + arbeit.get(n));
        }

    }
}
