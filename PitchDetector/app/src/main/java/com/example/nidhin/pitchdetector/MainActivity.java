package com.example.nidhin.pitchdetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity {

    public DrawableView tv_;
    Thread pitch_detector_thread_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_ = new DrawableView(this);
        setContentView(tv_);
        //setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        pitch_detector_thread_ = new Thread(new PitchDetector(this, new Handler()));
        pitch_detector_thread_.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        pitch_detector_thread_.interrupt();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void ShowPitchDetectionResult(
            final HashMap<Double, Double> frequencies,
            final double pitch) {
        tv_.setDetectionResults(frequencies, pitch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
