package bochhuang2_yumozhang2.scm.beatbox;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class main extends AppCompatActivity {

    Button freeMode_btn;
    VideoView videoHolder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // full screen
        freeMode_btn = (Button)findViewById(R.id.free_btn);
        setContentView(R.layout.main);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        videoHolder = (VideoView)findViewById(R.id.open_v);

        videoHolder.setMediaController(new MediaController(this));
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.open);

        videoHolder.setVideoURI(video);
        videoHolder.start();
    }

    public void onFreeMode(View view){
        Log.d("start","free");
        Intent intent = new Intent();
        intent.setClass(this,SaveActivity.class);
        startActivity(intent);

        videoHolder.stopPlayback();

        main.this.finish();
    }

    public void onPlayMode(View view){
        Log.d("start","play");
        Intent intent = new Intent();
        intent.setClass(this,PlayActivity.class);
        startActivity(intent);
        main.this.finish();
    }
}