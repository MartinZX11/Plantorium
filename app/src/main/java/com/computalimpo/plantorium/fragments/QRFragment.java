package com.computalimpo.plantorium.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.computalimpo.plantorium.R;
import com.computalimpo.plantorium.Scaner;

public class QRFragment extends Fragment {

    public QRFragment() {}
    private static final int INTENT_CODE = 2;
    private static final int CAMERA_PERMISSION = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result =inflater.inflate(R.layout.fragment_qr, null);
        Button scan = result.findViewById(R.id.scanear);

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
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_CODE:
                Toast.makeText(getActivity(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
