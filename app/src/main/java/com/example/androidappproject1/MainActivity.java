package com.example.androidappproject1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> imageCaptureResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageCaptureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //If the activity result code is ok
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle extras = Objects.requireNonNull(result.getData()).getExtras();

                        //creating a new intent to switch to the upload image activity page
                        Intent uploadPhotoIntent =  new Intent(this, UploadImageActivity.class);

                        //putting the image result as a parameter in the intent and then starting it.
                        uploadPhotoIntent.putExtra("IMAGE", extras);
                        startActivity(uploadPhotoIntent);
                    }
                });
    }

    public void takePicture(View view) {
        //intent to switch to the android camera
        Intent clickPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //if the camera intent is successfully resolved, ie the app is able to properly access the camera feature,
        //then start the camera activity. Else show an error message
        if(clickPictureIntent.resolveActivity(getPackageManager()) != null)
            imageCaptureResultLauncher.launch(clickPictureIntent);
        else
            Toast.makeText(MainActivity.this, "Unable to access the camera", Toast.LENGTH_SHORT).show();
    }

}