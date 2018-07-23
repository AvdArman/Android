package com.example.arman.openimages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE = 1;
    private ImageView image;
    private Button btnRes;
    private Button btnGallery;
    private Button btnUrl;
    private EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnRes.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnUrl.setOnClickListener(this);
    }

    private void findViews() {
        image = findViewById(R.id.img);
        btnRes = findViewById(R.id.btn_res);
        btnGallery = findViewById(R.id.btn_gallery);
        btnUrl = findViewById(R.id.btn_url);
        etUrl = findViewById(R.id.et_url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_res:
                image.setImageResource(R.drawable.image);
                break;
            case R.id.btn_gallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), CODE);
                break;
            case R.id.btn_url:
                new DownloadImage(image).execute(etUrl.getText().toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Uri img = data.getData();

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setImageBitmap(bitmap);
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];

            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null) {
                Toast.makeText(MainActivity.this, "Something is wrong image not found", Toast.LENGTH_SHORT).show();
            }
            imageView.setImageBitmap(result);
        }
    }
}
