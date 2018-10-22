package com.mx.cherry.inventario.genericdomain.interactors;

public interface Interactor {

    /**
     * This is the main method that starts an x. It will make sure that the x operation is done on a
     * background thread.
     */
    void execute();
}
