package com.mx.cherry.inventario.utils.biometric;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.mx.cherry.inventario.R;
import com.mx.cherry.inventario.utils.Common;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


@TargetApi(Build.VERSION_CODES.M)
public class BiometricManagerMarshmallow {

    private static final String KEY_NAME = UUID.randomUUID().toString();

    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManagerCompat.CryptoObject cryptoObject;


    protected Context context;

    protected String title;
    protected String subtitle;
    protected String description;
    protected String negativeButtonText;
    private BiometricDialogMarshmallow biometricDialogMarshmallow;
    private String codigosError;


    public void displayBiometricPromptMarshmallow(final BiometricCallback biometricCallback) {
        generateKey();

        if (initCipher()) {

            cryptoObject = new FingerprintManagerCompat.CryptoObject(cipher);
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);

            fingerprintManagerCompat.authenticate(cryptoObject, 0, new CancellationSignal(),
                    new FingerprintManagerCompat.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            super.onAuthenticationError(errorCode, errString);
                            updateStatus(String.valueOf(errString));

                            codigosError = String.format(Common.getString(context, R.string.error_biometric),
                                    errorCode, errString);

                            biometricCallback.onAuthenticationResult(false, codigosError);
                        }

                        @Override
                        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                            super.onAuthenticationHelp(helpCode, helpString);
                            updateStatus(String.valueOf(helpString));

                            codigosError = String.format(Common.getString(context, R.string.ayuda_biometric),
                                    helpCode, helpString);

                            biometricCallback.onAuthenticationResult(false, codigosError);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            dismissDialog();
                            biometricCallback.onAuthenticationResult(true, null);
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            updateStatus(context.getString(R.string.biometric_failed));
                            biometricCallback.onAuthenticationResult(false, context.getString(R.string.biometric_error));
                        }
                    }, null);

            displayBiometricDialog(biometricCallback);
        }
    }


    private void displayBiometricDialog(final BiometricCallback biometricCallback) {
        biometricDialogMarshmallow = new BiometricDialogMarshmallow(context, biometricCallback);
        biometricDialogMarshmallow.setTitle(title);
        biometricDialogMarshmallow.setSubtitle(subtitle);
        biometricDialogMarshmallow.setDescription(description);
        biometricDialogMarshmallow.setButtonText(negativeButtonText);
        biometricDialogMarshmallow.show();
        biometricDialogMarshmallow.setCancelable(false);
    }


    private void dismissDialog() {
        if (biometricDialogMarshmallow != null) {
            biometricDialogMarshmallow.dismiss();
        }
    }

    private void updateStatus(String status) {
        if (biometricDialogMarshmallow != null) {
            biometricDialogMarshmallow.updateStatus(status);
        }
    }

    private void generateKey() {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
        }
    }


    private boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;


        } catch (KeyPermanentlyInvalidatedException e) {
            return false;

        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {

            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
