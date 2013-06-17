package com.github.matt.williams.glass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE_CALIBRATION = 1;

    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        mCameraView = (CameraView)findViewById(R.id.cameraview);
        calibrateFromPreferences();
    }

    public void calibrateFromPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        CalibratedCameraProjection projection = mCameraView.getCalibratedCameraProjection();
        projection.setTranslation(preferences.getFloat(CalibrationActivity.EXTRA_TRANSLATE_X, 0.0f), preferences.getFloat(CalibrationActivity.EXTRA_TRANSLATE_Y, 0.0f));
        projection.setScale(preferences.getFloat(CalibrationActivity.EXTRA_SCALE_X, 1.0f), preferences.getFloat(CalibrationActivity.EXTRA_SCALE_Y, 1.0f));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_TAB) {
            CalibratedCameraProjection projection = mCameraView.getCalibratedCameraProjection();
            Intent intent = new Intent(this, CalibrationActivity.class);
            intent.putExtra(CalibrationActivity.EXTRA_TRANSLATE_X, projection.mTranslateX);
            intent.putExtra(CalibrationActivity.EXTRA_TRANSLATE_Y, projection.mTranslateY);
            intent.putExtra(CalibrationActivity.EXTRA_SCALE_X, projection.mScaleX);
            intent.putExtra(CalibrationActivity.EXTRA_SCALE_Y, projection.mScaleY);
            startActivityForResult(intent, REQUEST_CODE_CALIBRATION);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
        case REQUEST_CODE_CALIBRATION:
            if (resultCode == Activity.RESULT_OK) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putFloat(CalibrationActivity.EXTRA_TRANSLATE_X, intent.getFloatExtra(CalibrationActivity.EXTRA_TRANSLATE_X, 0.0f));
                editor.putFloat(CalibrationActivity.EXTRA_TRANSLATE_Y, intent.getFloatExtra(CalibrationActivity.EXTRA_TRANSLATE_Y, 0.0f));
                editor.putFloat(CalibrationActivity.EXTRA_SCALE_X, intent.getFloatExtra(CalibrationActivity.EXTRA_SCALE_X, 1.0f));
                editor.putFloat(CalibrationActivity.EXTRA_SCALE_Y, intent.getFloatExtra(CalibrationActivity.EXTRA_SCALE_Y, 1.0f));
                editor.commit();
                calibrateFromPreferences();
            }
            break;
        }
    }
}
