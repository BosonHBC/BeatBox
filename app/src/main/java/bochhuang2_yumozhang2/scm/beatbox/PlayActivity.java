package bochhuang2_yumozhang2.scm.beatbox;

import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class PlayActivity extends AppCompatActivity {
Play_View  play_view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // full screen
        play_view = new Play_View(this);

        SharedPreferences sharedPreferences = getSharedPreferences("data", this.MODE_PRIVATE);
        int[]cmp = new int[play_view.songCount];

        for(int i = 0; i<play_view.songCount;i++) {
            cmp[i] = sharedPreferences.getInt("comp"+i,0);
        }
        play_view.setCompletions(cmp);
        setContentView(play_view);


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("data", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i = 0; i<play_view.songCount;i++) {
            editor.putInt("comp" + i, play_view.getCompletions()[i]);
        }
        editor.commit();

        play_view.isPlaying = false;
    }
}
