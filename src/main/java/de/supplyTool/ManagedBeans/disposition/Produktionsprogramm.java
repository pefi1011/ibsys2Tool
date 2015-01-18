package de.supplyTool.ManagedBeans.disposition;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@ApplicationScoped
public class Produktionsprogramm implements Serializable {

    private static final long serialVersionUID = 9179622950739280960L;

    private int               p1_1             = 0;
    private int               p1_2             = 0;
    private int               p1_3             = 0;
    private int               p1_4             = 0;

    private int               p2_1             = 0;
    private int               p2_2             = 0;
    private int               p2_3             = 0;
    private int               p2_4             = 0;

    private int               p3_1             = 0;
    private int               p3_2             = 0;
    private int               p3_3             = 0;
    private int               p3_4             = 0;

    public void weiter() {
        // final Dao dao = new Dao();
        // dao.resetTeileDispo();
        // dao.resetTeileDispoErgebniss();
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        try {
            response.sendRedirect("prodverwaltungP1.xhtml");
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getP1_1() {
        return p1_1;
    }

    public void setP1_1(final int p1_1) {
        this.p1_1 = p1_1;
    }

    public int getP1_2() {
        return p1_2;
    }

    public void setP1_2(final int p1_2) {
        this.p1_2 = p1_2;
    }

    public int getP1_3() {
        return p1_3;
    }

    public void setP1_3(final int p1_3) {
        this.p1_3 = p1_3;
    }

    public int getP1_4() {
        return p1_4;
    }

    public void setP1_4(final int p1_4) {
        this.p1_4 = p1_4;
    }

    public int getP2_1() {
        return p2_1;
    }

    public void setP2_1(final int p2_1) {
        this.p2_1 = p2_1;
    }

    public int getP2_2() {
        return p2_2;
    }

    public void setP2_2(final int p2_2) {
        this.p2_2 = p2_2;
    }

    public int getP2_3() {
        return p2_3;
    }

    public void setP2_3(final int p2_3) {
        this.p2_3 = p2_3;
    }

    public int getP2_4() {
        return p2_4;
    }

    public void setP2_4(final int p2_4) {
        this.p2_4 = p2_4;
    }

    public int getP3_1() {
        return p3_1;
    }

    public void setP3_1(final int p3_1) {
        this.p3_1 = p3_1;
    }

    public int getP3_2() {
        return p3_2;
    }

    public void setP3_2(final int p3_2) {
        this.p3_2 = p3_2;
    }

    public int getP3_3() {
        return p3_3;
    }

    public void setP3_3(final int p3_3) {
        this.p3_3 = p3_3;
    }

    public int getP3_4() {
        return p3_4;
    }

    public void setP3_4(final int p3_4) {
        this.p3_4 = p3_4;
    }

    @Override
    public String toString() {
        return "Produktionsprogramm [p1_1=" + p1_1 + ", p1_2=" + p1_2
                + ", p1_3=" + p1_3 + ", p1_4=" + p1_4 + ", p2_1=" + p2_1
                + ", p2_2=" + p2_2 + ", p2_3=" + p2_3 + ", p2_4=" + p2_4
                + ", p3_1=" + p3_1 + ", p3_2=" + p3_2 + ", p3_3=" + p3_3
                + ", p3_4=" + p3_4 + "]";
    }

}
