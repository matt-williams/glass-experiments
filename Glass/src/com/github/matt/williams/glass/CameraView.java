package com.github.matt.williams.glass;

import android.content.Context;
import android.util.AttributeSet;

import com.github.matt.williams.android.ar.CameraBillboard;

public class CameraView extends com.github.matt.williams.android.ar.CameraView {

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public CameraBillboard createBillboard() {
        return new EdgeBillboard(getResources(), getTexture());
    }
}