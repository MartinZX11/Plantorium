package com.computalimpo.plantorium;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CropDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);
        Intent intent = getIntent();
        final CategoryPOJO object = (CategoryPOJO) intent.getSerializableExtra("object");

        TextView nameContent = findViewById(R.id.nameContent);
        TextView locationContent = findViewById(R.id.locationContent);
        TextView illContent = findViewById(R.id.illContent);
        TextView waterContent = findViewById(R.id.waterContent);
        TextView pruneContent = findViewById(R.id.pruneContent);
        TextView harvestContent = findViewById(R.id.harvestContent);
        ImageView categoryImage = findViewById(R.id.categoryImage);
        ImageView qrImagen = findViewById(R.id.qrImagen);
        TextView numberContent = findViewById(R.id.numberContent);
        final TextView taskOfCategory = findViewById(R.id.taskOfCategory);

        String str = object.getImage();
        byte data[]= android.util.Base64.decode(str, android.util.Base64.DEFAULT);
        Bitmap categoryBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        categoryImage.setImageBitmap(categoryBitmap);
        nameContent.setText(object.getName());
        locationContent.setText(object.getLocation());
        String si = getResources().getString(R.string.yes);
        illContent.setText(object.isIll() ? si : "No");
        waterContent.setText(object.isWater() ? si : "No");
        pruneContent.setText(object.isPrune() ? si : "No");
        harvestContent.setText(object.isHarvest() ? si : "No");
        numberContent.setText(object.getNumber()+ "");

        //Task asociadas

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<TaskPOJO> tasks =  CropsDatabase.getInstance(getApplicationContext()).taskDao().getTasksCategory(object.getId());
                        if(tasks.isEmpty()){
                            taskOfCategory.setText(getResources().getString(R.string.noTasks));
                        } else {
                            String res = "";
                            for (TaskPOJO t:
                                 tasks) {
                                res = res + t.getDate() + " : " + t.getType() + "\n";

                            }
                            taskOfCategory.setText(res);

                        }




                    }
                }).start();

        QRCodeWriter writer = new QRCodeWriter();
        final Bitmap image;
        final String location = object.getLocation();
        final String name = object.getName();

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
            image = bmp;
            Button qrButton = findViewById(R.id.qrButton);
            qrButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int checkWriteablePermission =
                                    ContextCompat.checkSelfPermission(CropDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            if (PackageManager.PERMISSION_GRANTED == checkWriteablePermission) {
                                MediaStore.Images.Media.insertImage(getContentResolver(), image, location+name , "Description");
                                Toast.makeText(getApplicationContext(), R.string.qrTitle, Toast.LENGTH_SHORT).show();
                            } else {
                                ActivityCompat.requestPermissions(
                                        CropDetail.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }

                        }
                    }

            );

        } catch (WriterException e) {
            e.printStackTrace();
        }





    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath + "/profile.jpeg");

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }



    private String saveToInternalStorage (Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpeg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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
