package com.github.matt.williams.glass;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.InputDevice.MotionRange;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.github.matt.williams.android.ar.Renderable;

public class CalibratingCameraView extends CalibratedCameraView {
    public interface OnCalibrationCompleteListener {
        public void onCalibrationComplete(float translateX, float translateY, float scaleX, float scaleY);
    }

    private OnCalibrationCompleteListener mListener;
    private float mPreviousX;
    private float mPreviousY;
    private float mPreviousScale;
    private int mPreviousPointerCount;
    private boolean mMoved;

    public CalibratingCameraView(Context context) {
        super(context);
        setFocusable(true);
    }

    public CalibratingCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }

    @Override
    public Renderable createBillboard() {
        return new FragmentShaderBillboard(getResources(), getTexture(), R.string.edgeFragmentShader);
    }

    public void setOnCalibrationCompleteListener(OnCalibrationCompleteListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        MotionRange motionRangeX = event.getDevice().getMotionRange(MotionEvent.AXIS_X);
        MotionRange motionRangeY = event.getDevice().getMotionRange(MotionEvent.AXIS_Y);
        float rangeX = (motionRangeX.getMax() - motionRangeX.getMin());
        float rangeY = (motionRangeY.getMax() - motionRangeY.getMin());
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;
        for (int ptrIdx = 0; ptrIdx < event.getPointerCount(); ptrIdx++) {
            minX = Math.min(minX, event.getX(ptrIdx));
            minY = Math.min(minY, event.getY(ptrIdx));
            maxX = Math.max(maxX, event.getX(ptrIdx));
            maxY = Math.max(maxY, event.getY(ptrIdx));
        }
        float newX = (minX + maxX) / 2;
        float newY = (minY + maxY) / 2;
        float newScale = FloatMath.sqrt((maxX - minX) * (maxX - minX) + (maxY - minY) * (maxY - minY));
        CalibratedCameraProjection projection = getCalibratedCameraProjection();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mMoved = false;
        } else if ((event.getAction() == MotionEvent.ACTION_MOVE) &&
            (event.getPointerCount() == mPreviousPointerCount)) {
            if ((event.getPointerCount() > 1) ||
                ((newX - mPreviousX) * (newX - mPreviousX) + (newY - mPreviousY) * (newY - mPreviousY) > 25.0f)) {
                mMoved = true;
            }
            projection.setTranslation(projection.mTranslateX + 0.00025f * projection.mScaleX * (newX - mPreviousX),
                                      projection.mTranslateY - 0.00025f * projection.mScaleY * (newY - mPreviousY));
            if (event.getPointerCount() > 1) {
                projection.setScale(projection.mScaleX * (newScale / mPreviousScale), projection.mScaleY * (newScale / mPreviousScale));
            }
        } else if ((event.getAction() == MotionEvent.ACTION_UP) &&
                   (!mMoved) &&
                   (event.getEventTime() - event.getDownTime() < 400)) {
            mListener.onCalibrationComplete(projection.mTranslateX, projection.mTranslateY, projection.mScaleX, projection.mScaleY);
        }
        mPreviousX = newX;
        mPreviousY = newY;
        mPreviousScale = newScale;
        mPreviousPointerCount = event.getPointerCount();
        return true;
    }

    @Override
    public boolean onKeyDown(int key, KeyEvent event) {
        return true;
    }
}
