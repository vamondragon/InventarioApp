package com.mx.cherry.inventario;

import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mx.cherry.inventario.inboxmodule.InboxActivity;
import com.mx.cherry.inventario.utils.biometric.BiometricCallback;
import com.mx.cherry.inventario.utils.biometric.BiometricManager;
import com.mx.cherry.inventario.utils.biometric.BiometricUtils;
import com.mx.cherry.inventario.utils.pinlock.IndicatorDots;
import com.mx.cherry.inventario.utils.pinlock.PinLockListener;
import com.mx.cherry.inventario.utils.pinlock.PinLockView;

import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity {

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;
    private Button entrarbtn;


    public static final String TAG = "PinLockView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.activity_main);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        entrarbtn = (Button) findViewById(R.id.entrar_btn);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.bg_fingerprint));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                BiometricUtils.isPermissionGranted(LoginActivity.this) &&
                BiometricUtils.isHardwareSupported(LoginActivity.this) &&
                BiometricUtils.isFingerprintAvailable(LoginActivity.this)) {

            new BiometricManager.BiometricBuilder(LoginActivity.this)
                    .setTitle(getString(R.string.biometric_dialog_description))
                    .setDescription(getString(R.string.biometric_dialog_description))
                    .setNegativeButtonText(getString(R.string.biometric_dialog_cancel_button))
                    .build()
                    .authenticate(new BiometricCallback() {
                        @Override
                        public void onAuthenticationResult(Object isSuccessful, Object message) {
                            if (Boolean.parseBoolean(isSuccessful.toString())) {

                                Intent executeIntent = new Intent(LoginActivity.this, InboxActivity.class);
                                executeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                executeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(executeIntent);
                                finish();

                            }

                        }

                        @Override
                        public void onAuthenticationCancelled() {
                        }
                    });
        }

    }


    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
        }

        @Override
        public void onEmpty() {
            entrarbtn.setBackgroundColor(getResources().getColor(R.color.dialog_subtitle));
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            if (pinLength > 0) {
                entrarbtn.setBackgroundColor(getResources().getColor(R.color.bg_fingerprint));
            } else {
                entrarbtn.setBackgroundColor(getResources().getColor(R.color.dialog_subtitle));
            }
        }
    };

}
