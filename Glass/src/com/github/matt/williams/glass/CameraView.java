package com.github.matt.williams.glass;

import android.content.Context;
import android.util.AttributeSet;

import com.github.matt.williams.android.ar.Renderable;

public class CameraView extends CalibratedCameraView {
    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Renderable createBillboard() {
        return new FragmentShaderBillboard(getResources(), getTexture(), R.string.skinFragmentShader);
    }
}
