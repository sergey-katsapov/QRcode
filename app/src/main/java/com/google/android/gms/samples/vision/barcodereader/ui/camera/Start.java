package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.samples.vision.barcodereader.R;
import com.google.android.gms.samples.vision.barcodereader.ui.camera.Tools.GenericQR.GenerateQRCodeActivity;


public class Start extends Activity {
    private Button scan;
    private Button create;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        scan=(Button)findViewById(R.id.scan);
        create=(Button)findViewById(R.id.create);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,MainActivity.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,GenerateQRCodeActivity.class);
                intent.putExtra("str","a");
                startActivity(intent);
            }
        });
    }
}
