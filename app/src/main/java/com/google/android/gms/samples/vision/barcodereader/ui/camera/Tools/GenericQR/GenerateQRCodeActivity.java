package com.google.android.gms.samples.vision.barcodereader.ui.camera.Tools.GenericQR;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.samples.vision.barcodereader.R;
import com.google.android.gms.samples.vision.barcodereader.ui.camera.Tools.Contents;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class GenerateQRCodeActivity extends Activity implements OnClickListener {

    private String qrInputText;
    private Bitmap bitmap;
    private EditText qrInput;

    private String LOG_TAG = "GenerateQRCode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);

        qrInput = (EditText) findViewById(R.id.qrInput);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                qrInputText = qrInput.getText().toString();
                Log.v(LOG_TAG, qrInputText);

                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}