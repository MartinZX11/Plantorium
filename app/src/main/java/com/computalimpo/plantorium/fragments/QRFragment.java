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

import com.computalimpo.plantorium.CropDetail;
import com.computalimpo.plantorium.MainActivity;
import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.Scaner;
import com.computalimpo.plantorium.database.CropsDatabase;
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


        return qrFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_CODE:
                    if(data != null) {
                        final int id = Integer.parseInt(data.getStringExtra("result"));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(), CropDetail.class);
                                int isIn = CropsDatabase.getInstance(getContext()).categoryDao().getNum(id);
                                if (isIn > 0) {
                                    CategoryPOJO object = CropsDatabase.getInstance(getContext()).categoryDao().getCategoryById(id);
                                    intent.putExtra("object", object);
                                    startActivity(intent);

                                } else {


                                    getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getContext(), R.string.scanError, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }


                            }
                        }).start();
                    }
                break;
        }


    }


}
