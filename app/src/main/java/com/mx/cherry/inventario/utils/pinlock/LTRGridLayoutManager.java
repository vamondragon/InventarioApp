package com.mx.cherry.inventario.utils.pinlock;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Used to always maintain an LTR layout no matter what is the real device's layout direction
 * to avoid an unwanted reversed direction in RTL devices
 */

public class LTRGridLayoutManager extends GridLayoutManager {

    public LTRGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LTRGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public LTRGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    protected boolean isLayoutRTL(){
        return false;
    }
}
