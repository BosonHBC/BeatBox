package bochhuang2_yumozhang2.scm.beatbox;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by bochhuang2 on 5/5/2017.
 */

public class SaveActivity extends AppCompatActivity {

    Invoke_View invoke_view;
    boolean isFirst = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide Title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // full screen

        int[][][] maps = new int[6][5][16];
        int[][] types = new int[6][5];
        int[] speeds = new int[6];
        boolean[] inits = new boolean[6];
        boolean[][] in_ini = new boolean[6][5];


        SharedPreferences sharedPreferences = getSharedPreferences("datas", this.MODE_PRIVATE);
        for(int i = 0; i < 6; i ++){
            inits[i] = sharedPreferences.getBoolean("inits" + i, false);
            speeds[i] = sharedPreferences.getInt("speeds" + i, 75);
            for(int j = 0; j < 5; j++){
                types[i][j] =  sharedPreferences.getInt("types" + i + j,0);
                in_ini[i][j] = sharedPreferences.getBoolean("in_init" + i + j,false);
                for(int k = 0; k<16;k++){
                    maps[i][j][k] =  sharedPreferences.getInt("maps" + i + j + k,0);
                }
            }

            Log.d("init",inits[i] + " ");
        }
        invoke_view = new Invoke_View(this);
        isFirst = sharedPreferences.getBoolean("isFirst",true);
        if(!isFirst) {
            invoke_view.inputData(maps,types, inits, speeds, in_ini);
        }
        else {
            isFirst = false;
        }



        setContentView(invoke_view);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("datas", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isFirst",isFirst);

        for(int i = 0; i < 6; i ++){
            editor.putBoolean("inits" + i,invoke_view.getInits()[i]);
            editor.putInt("speeds" + i,invoke_view.getSpeeds()[i]);
            for(int j = 0; j < 5; j++){
                editor.putInt("types" + i + j,invoke_view.getTypes()[i][j]);
                editor.putBoolean("in_init" + i + j,invoke_view.getIn_init()[i][j]);
                for(int k = 0; k<16;k++){
                    editor.putInt("maps" + i + j + k,invoke_view.getMaps()[i][j][k]);
                }
            }
        }

        editor.commit();
    }
}
