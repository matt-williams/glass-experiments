package com.github.matt.williams.glass;

import android.content.Context;
import android.util.AttributeSet;

public class CalibratedCameraView extends com.github.matt.williams.android.ar.CameraView {
    private final CalibratedCameraProjection mCalibratedCameraProjection = new CalibratedCameraProjection();
    public CalibratedCameraView(Context context) {
        super(context);
        setProjection(mCalibratedCameraProjection);
    }

    public CalibratedCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setProjection(mCalibratedCameraProjection);
    }

    public CalibratedCameraProjection getCalibratedCameraProjection() {
        return mCalibratedCameraProjection;
    }
}
