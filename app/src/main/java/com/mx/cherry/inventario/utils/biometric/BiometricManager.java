package com.mx.cherry.inventario.utils.biometric;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;


public class BiometricManager extends BiometricManagerMarshmallow {


    protected BiometricManager(final BiometricBuilder biometricBuilder) {
        this.context = biometricBuilder.context;
        this.title = biometricBuilder.title;
        this.subtitle = biometricBuilder.subtitle;
        this.description = biometricBuilder.description;
        this.negativeButtonText = biometricBuilder.negativeButtonText;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void authenticate(@NonNull final BiometricCallback biometricCallback) {

        if ( subtitle == null || description == null || negativeButtonText == null) {
            throw new IllegalArgumentException("Dialog elements cannot be null");
        }

        if (!BiometricUtils.isSdkVersionSupported()) {
            throw new IllegalArgumentException("SDK version not supported");
        }

        if (!BiometricUtils.isPermissionGranted(context)) {
            throw new IllegalArgumentException("Biometric authentication permission not granted");
        }

        if (!BiometricUtils.isHardwareSupported(context)) {
            throw new IllegalArgumentException("Biometric authentication not supported");
        }

        if (!BiometricUtils.isFingerprintAvailable(context)) {
            throw new IllegalArgumentException("Biometric authentication  not available");
        }

        displayBiometricDialog(biometricCallback);
    }


    private void displayBiometricDialog(BiometricCallback biometricCallback) {
        if (BiometricUtils.isBiometricPromptEnabled()) {
            displayBiometricPrompt(biometricCallback);
        } else {
            displayBiometricPromptMarshmallow(biometricCallback);
        }
    }


    @TargetApi(Build.VERSION_CODES.P)
    private void displayBiometricPrompt(final BiometricCallback biometricCallback) {
        new BiometricPrompt.Builder(context)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setNegativeButton(negativeButtonText, context.getMainExecutor(), (dialogInterface, i) -> biometricCallback.onAuthenticationCancelled())
                .build()
                .authenticate(new CancellationSignal(), context.getMainExecutor(),
                        new BiometricCallbackPie(biometricCallback, context));
    }


    public static class BiometricBuilder {

        private String title;
        private String subtitle;
        private String description;
        private String negativeButtonText;

        private Context context;

        public BiometricBuilder(Context context) {
            this.context = context;
        }

        public BiometricBuilder setTitle(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public BiometricBuilder setSubtitle(@NonNull final String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public BiometricBuilder setDescription(@NonNull final String description) {
            this.description = description;
            return this;
        }


        public BiometricBuilder setNegativeButtonText(@NonNull final String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public BiometricManager build() {
            return new BiometricManager(this);
        }
    }
}
