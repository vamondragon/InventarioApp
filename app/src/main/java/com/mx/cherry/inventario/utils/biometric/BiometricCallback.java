package com.mx.cherry.inventario.utils.biometric;

public interface BiometricCallback<Boolean, String> {

    void onAuthenticationResult(Boolean isSuccessful, String message);

    void onAuthenticationCancelled();
}
