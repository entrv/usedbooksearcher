package kr.yookn.usedbooksearcher;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureActivityAnyOrientation extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_any_orientation);
    }
}
