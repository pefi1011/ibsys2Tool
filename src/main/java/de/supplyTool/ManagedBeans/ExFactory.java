package de.supplyTool.ManagedBeans;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExFactory extends ExceptionHandlerFactory {

    private final ExceptionHandlerFactory wrapped;

    public ExFactory(final ExceptionHandlerFactory factory) {
        wrapped = factory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new ExHandler(wrapped.getExceptionHandler());
    }

}