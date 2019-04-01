package com.computalimpo.plantorium;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.zxing.Result;

import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scaner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            Log.v("ETIQUETA", "AQUI"); // Prints scan results
            // Programmatically initialize the scanner view
            mScannerView = new ZXingScannerView(this);
            // Set the scanner view as the content view
            setContentView(mScannerView);
        }

        @Override
        public void onResume() {
            super.onResume();
            // Register ourselves as a handler for scan results.
            mScannerView.setResultHandler(this);
            // Start camera on resume
            mScannerView.startCamera();
        }

        @Override
        public void onPause() {
            super.onPause();
            // Stop camera on pause
            mScannerView.stopCamera();
        }

        @Override
        public void handleResult(Result rawResult) {
            // Do something with the result here
            // Prints scan results

            Intent intent = new Intent();
            intent.putExtra("result", rawResult.getText());
            setResult(RESULT_OK, intent);
            finish();
        }
}

