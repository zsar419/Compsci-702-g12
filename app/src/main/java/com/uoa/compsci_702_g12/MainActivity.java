package com.uoa.compsci_702_g12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    public static final int SELECTED_PICTURE = 20;
    public Button cameraBtn;
    public Button galleryBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }
    public void init() {
        cameraBtn = (Button) findViewById(R.id.cameraBtn);
        galleryBtn = (Button) findViewById(R.id.galleryBtn);


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //This method will call the camera
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCamera,CAMERA_REQUEST); //passing in a unique number; getting the picture back from the camera

            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data){

                if(resultCode ==RESULT_OK) {//user pressed OK
                    if (requestCode == CAMERA_REQUEST) { //if we are hearing back from the camera
                        Bitmap cameraImage= (Bitmap) data.getExtras().get("data"); //storing the image in bitmap

                    }

                }
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                openGallery.setType("image/*"); //display image files only
                startActivityForResult(openGallery, SELECTED_PICTURE);
            }
            protected void onActivityResult(int requestCode, int resultCode, Intent data){

                 //we are hearing back from the image gallery
                if(resultCode==RESULT_OK){
                    if(requestCode== SELECTED_PICTURE){
                        Uri imageUri = data.getData(); //get image file

                        try {
                            InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            Bitmap image = BitmapFactory.decodeStream(imageStream);
                            //need a reference to imageView.......
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            //Toast.makeText(this,"Unable to open image", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            }


        });

    }







}

