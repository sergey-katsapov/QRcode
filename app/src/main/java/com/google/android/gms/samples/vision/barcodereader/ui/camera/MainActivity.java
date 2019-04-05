package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.samples.vision.barcodereader.R;
import com.google.android.gms.samples.vision.barcodereader.ui.camera.Tools.Barcode.BarcodeCaptureActivity;
import com.google.android.gms.vision.barcode.Barcode;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView statusMessage;
    private TextView barcodeValue;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView) findViewById(R.id.status_message);
        barcodeValue = (TextView) findViewById(R.id.barcode_value);
        barcodeValue.setMovementMethod(new ScrollingMovementMethod());

        findViewById(R.id.read_barcode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            onClickMethod();
        }
    }

    public void onClickMethod() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }


    String stringURL = "";
    android.net.Uri URL;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    String barCodeValue = barcode.displayValue;
                    if (URLUtil.isValidUrl(barCodeValue)) {
                        statusMessage.setText(R.string.barcode_success);
                        barcodeValue.setText(barCodeValue);
                        android.net.Uri link = Uri.parse(barCodeValue);
                        String linkString = link.toString();
                        stringURL = linkString;
                        URL = link;

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("LEAVE THE APPLICATION?");
                        builder.setMessage("Sure to visit " + linkString);
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openUrlInBrowser();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "No Button Clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else {
                        statusMessage.setText(R.string.barcode_success);
                        barcodeValue.setText(barCodeValue);
                    }
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                }
                else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            }
            else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void openUrlInBrowser() {
        Intent browserOpen = new Intent(Intent.ACTION_VIEW, URL);
        startActivity(browserOpen);
    }
}
