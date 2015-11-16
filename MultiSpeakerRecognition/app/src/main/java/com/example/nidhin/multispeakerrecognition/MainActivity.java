package com.example.nidhin.multispeakerrecognition;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Conference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private SpeechRecognizer speechRecognizer;
    public Intent intent;
    TextView ResultSpoken;
    TextView confidence;
    TextView RMS;
    float RMSVAl =0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResultSpoken = (TextView)findViewById(R.id.ResultSpoken);
        confidence = (TextView)findViewById(R.id.confidence);
        RMS = (TextView)findViewById(R.id.RMS);
        Logger.getLogger().appendLog("Application begining");
        initializeSpeech();




    }




    public void initializeSpeech()
    {
        if(null!=speechRecognizer)
            speechRecognizer.destroy();

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-GB");
        speechRecognizer.startListening(intent);

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

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

            if(rmsdB>6.0)
            {
                RMSVAl = rmsdB;
              //  Logger.getLogger().appendLog("RMSValue is" + RMSVAl);
            }
            RMS.setText("RMS value:" + Float.toString(rmsdB));
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        initializeSpeech();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        System.out.println(data.get(0));
        float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
        ResultSpoken.setText(""+data.get(0));
        confidence.setText("" + Float.toString(scores[0]));
        RMS.setText("" + Float.toString(RMSVAl));


        Logger.getLogger().appendLog("" + data.get(0));
        Logger.getLogger().appendLog("Confidence:" + Float.toString(scores[0]));
        Logger.getLogger().appendLog("RMSValue** is" + RMSVAl);

        Logger.getLogger().appendLog("\n\n\n");

        RMSVAl = 0.0f;




        initializeSpeech();

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }




}
