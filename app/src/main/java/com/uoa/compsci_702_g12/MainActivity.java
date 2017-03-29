package com.uoa.compsci_702_g12;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static String OCR_TEXT = "OCR_TEXT";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_GALLERY_IMAGE = 2;

    Context context;
    Button cameraButton;
    Button galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        cameraButton = (Button) findViewById(R.id.camera_btn);
        galleryButton= (Button) findViewById(R.id.gallary_btn);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dispatchTakePictureIntent(); }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dispathLoadPictureIntent(); }
        });
    }

    /**
     * In-built method which allows user to take photos via in-built camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * In-built method which allows user to load images from media-store (gallery)
     */
    private void dispathLoadPictureIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_LOAD_GALLERY_IMAGE);
    }

    /**
     * Callback retrieved after taking a photo to recieve the image which can be used for further processing
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if(requestCode == REQUEST_IMAGE_CAPTURE) {              // Process callback for image captured from camera
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                postBitmap(imageBitmap);
            } else if(requestCode == REQUEST_LOAD_GALLERY_IMAGE) {  // Process callback for image retrieved from gallery
                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    postBitmap(imageBitmap);
                } catch (FileNotFoundException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Send bitmap to cognitive services API and retrieve extracted text via OCR,
     * initialize new activity (Text) with retrieved data to display the text
     * @param bmp - image passed in as bitmap
     */
    private void postBitmap(Bitmap bmp) {
        // Post bitmap to web api
        /**
         * POST IMAGE AND RECIEVE EXTRACTED TEXT
         */

        // Retrieve text and assign to this variable
        String ocrText = "testing text retrieved from web-api ocr";


        Intent intent = new Intent(this, TextActivity.class);
        intent.putExtra(OCR_TEXT, ocrText);
        startActivity(intent);
    }


}
