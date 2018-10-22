package com.mx.cherry.inventario.genericdomain.presenters;


import com.mx.cherry.inventario.genericdomain.executor.Executor;
import com.mx.cherry.inventario.genericdomain.executor.MainThread;

public abstract class AbstractPresenter {
    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
