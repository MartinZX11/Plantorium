package com.computalimpo.plantorium.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.Scaner;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;

public class QRFragment extends Fragment {

    public QRFragment() {}
    private static final int INTENT_CODE = 2;
    private static final int CAMERA_PERMISSION = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View qrFragment =inflater.inflate(R.layout.fragment_qr, null);
        Button scan = qrFragment.findViewById(R.id.scanear);
        Button gen = qrFragment.findViewById(R.id.generar);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkWriteablePermission =
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (PackageManager.PERMISSION_GRANTED == checkWriteablePermission) {
                    Intent intent = new Intent(getActivity(), Scaner.class);
                    startActivityForResult(intent, INTENT_CODE);
                } else {
                    ActivityCompat.requestPermissions(
                            getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                }

            }
        });

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imagen = qrFragment.findViewById(R.id.imagenQR);
                EditText entrada = qrFragment.findViewById(R.id.entrada);
                QRCodeWriter writer = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(entrada.getText().toString(), BarcodeFormat.QR_CODE, 512, 512);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    imagen.setImageBitmap(bmp);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });


        return qrFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_CODE:
                    if(data != null) {
                        Toast.makeText(getActivity(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                    }
                break;
        }
    }
}
