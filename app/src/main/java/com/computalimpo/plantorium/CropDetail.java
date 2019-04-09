package com.computalimpo.plantorium;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CropDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);
        Intent intent = getIntent();
        CategoryPOJO object = (CategoryPOJO) intent.getSerializableExtra("object");

        TextView nameContent = findViewById(R.id.nameContent);
        TextView locationContent = findViewById(R.id.locationContent);
        TextView illContent = findViewById(R.id.illContent);
        TextView waterContent = findViewById(R.id.waterContent);
        TextView pruneContent = findViewById(R.id.pruneContent);
        ImageView qrImagen = findViewById(R.id.qrImagen);
        TextView numberContent = findViewById(R.id.numberContent);

        nameContent.setText(object.getName());
        locationContent.setText(object.getLocation());
        illContent.setText(object.isIll() ? "Yes" : "No");
        waterContent.setText(object.isWater() ? "Yes" : "No");
        pruneContent.setText(object.isPrune() ? "Yes" : "No");
        numberContent.setText(object.getNumber()+ "");

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode("" + object.getId(), BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrImagen.setImageBitmap(bmp);
            //saveToInternalStorage(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }



    private String saveToInternalStorage (Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
