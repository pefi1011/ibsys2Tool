package de.supplyTool.ManagedBeans;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;

public class ExHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;
    private static boolean         firstTime = false;

    public ExHandler(final ExceptionHandler ex) {
        wrapped = ex;
    }

    @Override
    public void handle() throws FacesException {
        try {
            if (firstTime == false) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("produktionsprogramm.xhtml");
                firstTime = true;
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

}
