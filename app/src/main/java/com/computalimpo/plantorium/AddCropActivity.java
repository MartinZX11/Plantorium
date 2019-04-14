package com.computalimpo.plantorium;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.computalimpo.plantorium.POJO.CategoryPOJO;
import com.computalimpo.plantorium.POJO.TaskPOJO;
import com.computalimpo.plantorium.database.CropsDatabase;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddCropActivity extends AppCompatActivity {

    private EditText categoryName;
    private EditText categoryLocation;
    private CheckBox water;
    private CheckBox ill;
    private CheckBox prune;
    private CheckBox harvest;
    private EditText categoryNumber;
    private Button captureImage;
    private ImageView imageView;
    private TextView coordinates;
    private LatLng point = new LatLng(0.0,0.0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        categoryName = findViewById(R.id.categoryName);
        water = findViewById(R.id.checkWater);
        ill = findViewById(R.id.checkIll);
        prune = findViewById(R.id.checkPrune);
        harvest = findViewById(R.id.checkHarvest);
        categoryLocation = findViewById(R.id.categoryLocation);
        categoryNumber = findViewById(R.id.categoryNumber);
        captureImage = findViewById(R.id.captureButton);
        imageView = findViewById(R.id.imageCaptured);
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.oak_tree_icon));
        coordinates = findViewById(R.id.textViewCoord);
        coordinates.setText("Coordinates by defect: " + point.toString());
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 0);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 0) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddCropActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == 1) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
        } else if (requestCode == 2) {
            point = (LatLng) data.getExtras().get("coord");
            coordinates = findViewById(R.id.textViewCoord);
            coordinates.setText("Coordinates selected: "+ data.getExtras().get("coord").toString());
        }
    }


    public void addCategory(View view){

        final Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        final Bitmap newBitmap = resize(bitmap, 200,200);
        new Thread(new Runnable() {
            @Override
            public void run() {

                CropsDatabase.getInstance(getApplicationContext()).categoryDao().addCategory(new CategoryPOJO(categoryName.getText().toString(), categoryLocation.getText().toString(),water.isChecked(), prune.isChecked(),ill.isChecked(), harvest.isChecked(), Integer.parseInt(categoryNumber.getText().toString()), getStringImage(newBitmap), point.latitude, point.longitude));

            }
        }).start();
        finish();
    }

    public void addCoordenates(View view){
        Intent intento = new Intent(this, MapActivity.class );
        startActivityForResult(intento, 2);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public void buttonCancel(View v){
        finish();
    }
}
