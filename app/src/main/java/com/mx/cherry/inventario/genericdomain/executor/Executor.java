package com.mx.cherry.inventario.genericdomain.executor;


import com.mx.cherry.inventario.genericdomain.interactors.AbstractInteractor;

/**
 * This executor is responsible for running interactors on background threads.
 * <p/>
 */
public interface Executor {

    /**
     * This method should call the x's run method and thus start the x. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The x to run.
     */
    void execute(final AbstractInteractor interactor);
}
