package com.github.matt.williams.glass;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        mCameraView = (CameraView)findViewById(R.id.cameraview);
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
}
