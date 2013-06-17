package com.github.matt.williams.glass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.matt.williams.glass.CalibratingCameraView.OnCalibrationCompleteListener;

public class CalibrationActivity extends Activity implements OnCalibrationCompleteListener {
    public static final String EXTRA_TRANSLATE_X = "translateX";
    public static final String EXTRA_TRANSLATE_Y = "translateY";
    public static final String EXTRA_SCALE_X = "scaleX";
    public static final String EXTRA_SCALE_Y = "scaleY";
    private CalibratingCameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        mCameraView = (CalibratingCameraView)findViewById(R.id.cameraview);
        Intent intent = getIntent();
        if (intent != null) {
            CalibratedCameraProjection projection = mCameraView.getCalibratedCameraProjection();
            projection.setTranslation(intent.getFloatExtra(EXTRA_TRANSLATE_X, 0.0f), intent.getFloatExtra(EXTRA_TRANSLATE_Y, 0.0f));
            projection.setScale(intent.getFloatExtra(EXTRA_SCALE_X, 1.0f), intent.getFloatExtra(EXTRA_SCALE_Y, 1.0f));
        }
        mCameraView.requestFocus();
        mCameraView.setOnCalibrationCompleteListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
    }

    @Override
    protected void onPause() {
        mCameraView.onPause();
        super.onPause();
    }

    @Override
    public void onCalibrationComplete(float translateX, float translateY, float scaleX, float scaleY) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TRANSLATE_X, translateX);
        intent.putExtra(EXTRA_TRANSLATE_Y, translateY);
        intent.putExtra(EXTRA_SCALE_X, scaleX);
        intent.putExtra(EXTRA_SCALE_Y, scaleY);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
