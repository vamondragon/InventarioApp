package com.mx.cherry.inventario.utils.biometric;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.cherry.inventario.R;


public class BiometricDialogMarshmallow extends BottomSheetDialog implements View.OnClickListener {

    private Context context;

    private Button btnCancel;
    private TextView itemTitle, itemSubtitle;

    private BiometricCallback biometricCallback;

    public BiometricDialogMarshmallow(@NonNull Context context) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context.getApplicationContext();
        setDialogView();
    }

    public BiometricDialogMarshmallow(@NonNull Context context, BiometricCallback biometricCallback) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context.getApplicationContext();
        this.biometricCallback = biometricCallback;
        setDialogView();
    }

    public BiometricDialogMarshmallow(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BiometricDialogMarshmallow(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setDialogView() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.view_bottom_sheet, null);
        setContentView(bottomSheetView);

        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        itemTitle = findViewById(R.id.item_title);
        itemSubtitle = findViewById(R.id.item_subtitle);

    }

    public void setTitle(String title) {
        itemTitle.setText(title);
    }


    public void setSubtitle(String subtitle) {
        itemSubtitle.setText(subtitle);
    }


    public void setButtonText(String negativeButtonText) {
        btnCancel.setText(negativeButtonText);
    }


    @Override
    public void onClick(View view) {
        dismiss();
        biometricCallback.onAuthenticationCancelled();
    }
}
