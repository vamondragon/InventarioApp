package com.mx.cherry.inventario.splashmodule.storage;


public interface IResultListener<A, B, C> {
    void onDataResult(A isSuccessful, B result, C message);
}
