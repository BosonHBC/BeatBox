package bochhuang2_yumozhang2.scm.beatbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    Edit_View edit_view;

    int inID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onpause","onCreate");
        edit_view = new Edit_View(this);
        SharedPreferences sharedPreferences = getSharedPreferences("data", this.MODE_PRIVATE);

        Intent intent = getIntent();
        inID = intent.getIntExtra("id",0);

        boolean first = sharedPreferences.getBoolean("firstTime" + inID,true);
        edit_view.setFirst(first);
        if(!first) {
            int[][] maps = new int[5][16];
            boolean[] inits = new boolean[5];
            int[] spids = new int[5];
            for (int i = 0; i < 5; i++) {
                inits[i] = intent.getBooleanExtra("in_int" + i,false);
                spids[i] = intent.getIntExtra("types" + i,1);
                for (int j = 0; j < 16; j++) {
                    maps[i][j] = intent.getIntExtra("maps" + i + j,0);
                }
            }

            edit_view.setInits(inits);
            edit_view.setMaps(maps);
            edit_view.setSpID(spids);
            edit_view.setSpeed(intent.getIntExtra("speed",50));
        }
        setContentView(edit_view);


        getSupportActionBar().hide(); // hide Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // full screen
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("onpause","onPause");
        SharedPreferences sharedPreferences = getSharedPreferences("data", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstTime" + inID,edit_view.getFirst());
        editor.commit();

        SharedPreferences sharedPreferences2 = getSharedPreferences("datas", this.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();

        editor2.putInt("speeds" + inID,edit_view.getSpeed());

        int[][] maps = edit_view.getMaps();
        for(int i = 0; i<5;i++){
            editor2.putInt("types" + inID + i,edit_view.getSpID()[i]);
            editor2.putBoolean("in_init" + inID + i,edit_view.getinit()[i]);
            for(int j = 0;j<16;j++){
                editor2.putInt("maps" + inID +i+j,maps[i][j]);
            }
        }

        edit_view.isPlaying = false;

        editor2.commit();
    }
}
