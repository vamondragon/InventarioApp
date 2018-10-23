package com.mx.cherry.inventario.utils.biometric;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.mx.cherry.inventario.R;
import com.mx.cherry.inventario.utils.Common;


@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricCallbackPie extends BiometricPrompt.AuthenticationCallback {

    private BiometricCallback biometricCallback;
    private Context context;
    private String codigosError;

    public BiometricCallbackPie(BiometricCallback biometricCallback, Context context) {
        this.biometricCallback = biometricCallback;
        this.context = context;
    }


    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        biometricCallback.onAuthenticationResult(true, null);
    }


    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        codigosError = String.format(Common.getString(context, R.string.ayuda_biometric),
                helpCode, helpString);
        biometricCallback.onAuthenticationResult(false, codigosError);
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        codigosError = String.format(Common.getString(context, R.string.error_biometric),
                errorCode, errString);
        biometricCallback.onAuthenticationResult(false, codigosError);
    }


    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        biometricCallback.onAuthenticationResult(false, context.getString(R.string.biometric_error));
    }
}
