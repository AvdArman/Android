package com.example.arman.userlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        setInfo(intent);

        final FloatingActionButton fabCall = findViewById(R.id.fab_call);
        fabCall.setOnClickListener(this);

        final FloatingActionButton fabMail = findViewById(R.id.fab_mail);
        fabMail.setOnClickListener(this);

        final RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rv = findViewById(R.id.rv_images);
        ImageAdapter imageAdapter = new ImageAdapter();
        rv.setAdapter(imageAdapter);
        rv.setLayoutManager(layoutManager);

        setVideo();
    }

    private void setVideo() {
        final VideoView videoView;
        videoView = findViewById(R.id.video);
        videoView.setVideoPath("https://storage.googleapis.com/coverr-main/mp4/The-Hill.mp4");
        videoView.start();
    }

    private void setInfo(final Intent intent) {
        position = intent.getIntExtra(UserAdapter.POSITION, 1);
        User user = Data.userList.get(position);
        int position = intent.getIntExtra(UserAdapter.IMGPOSITION, 0);
        String url = user.getImgUrlList().get(position);
        final ImageView imageView = findViewById(R.id.toolbar_image);
        final TextView tvTitle = findViewById(R.id.tv_title);
        Picasso.get().load(url).into(imageView);
        tvTitle.setText(user.getFullName());
    }

    @Override
    public void onClick(View view) {
        User user = Data.userList.get(position);
        switch (view.getId()) {
            case R.id.fab_call:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + user.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }
                break;
            case R.id.fab_mail:
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("message/rfc822");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getMailAddress()});
                try {
                    startActivity(Intent.createChooser(mailIntent, getString(R.string.send_mail)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, R.string.mail_error_message, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
