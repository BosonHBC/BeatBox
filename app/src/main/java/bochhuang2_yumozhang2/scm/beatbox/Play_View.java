package bochhuang2_yumozhang2.scm.beatbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 黄波铖 on 2017/5/1.
 */

public class Play_View  extends View{
    final int SELECT_MODE = 0;
    final int GAME_MODE = 1;
    // int
    int songCount = 2;
    int fps = 100;
    int[][][] maps = new int[songCount][5][16];
    int[][] types = new int[songCount][5];
    int[] completions = new int[songCount];
    int[] typeCount = new int[songCount];
    int inMode = SELECT_MODE;
    int inSong = 0;

    int[][] tMaps = new int[5][16];
    int[][] sMaps = new int[5][16];

    int progress = 0;

    int frame = 0;
    int duration = 25;
    int currFrame = 0;
    int[] soundID = new int[13];
    // float

    // boolean
    boolean isGameStart = true;
    boolean isPlaying = false;

    boolean isShake = false;
    Vibrator vibrator;

    // image

    // Rect
        Rect[] indexR = new Rect[songCount];

    Rect playR = new Rect(221,218,483,293);
    Rect backR = new Rect(939,218,1181,301);
    Rect backR_S = new Rect(10,10,280,100);
    // Point
    Point[] bigCircles = new Point[5];
    Point[][][] circles = new Point[5][2][8];
    // Paint
    int[] colors = {Color.rgb(129,194,214),Color.rgb(129,146,214),Color.rgb(217,179,230),Color.rgb(220,247,161),Color.rgb(131,252,216)};
    int[] colors2 = {Color.rgb(129-30,194-30,214-30),Color.rgb(129-30,146-30,214-30),Color.rgb(217-30,179-30,230-30),Color.rgb(220-30,247-30,161-30),Color.rgb(131-30,252-30,216-30)};
    int[] colors3 = {Color.rgb(129-90,194-90,214-90),Color.rgb(129-90,146-90,214-90),Color.rgb(217-90,179-90,230-90),Color.rgb(220-90,247-90,161-90),Color.rgb(131-90,252-90,216-90)};
    Paint keyPaint = new Paint();
    Paint keyPaint2 = new Paint();
    Paint keyPaint3 = new Paint();
    Paint titlePaint = new Paint();
    Paint detailPaint = new Paint();
    Paint blackPaint = new Paint();

    // abstract data

    Song[] songs = new Song[songCount];
    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    Timer t = new Timer();

    Context _ctx;
    public Play_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Init(context);
    }
    public Play_View(Context context, AttributeSet attrs) {
        super(context, attrs);

        Init(context);
    }

    public Play_View(Context context) {
        super(context);

        Init(context);
    }

    private void Init(Context ctx) {

        _ctx = ctx;
        //init sound pool
        // init sound Pool

        soundID[0] = sp.load(ctx,R.raw._8bit,1);
        soundID[1] = sp.load(ctx,R.raw.clap_tape,1);
        soundID[2] = sp.load(ctx,R.raw.cowbell808,1);
        soundID[3] = sp.load(ctx,R.raw.hihat_acoustic02,1);
        soundID[4] = sp.load(ctx,R.raw.hihat_electro,1);
        soundID[5] = sp.load(ctx,R.raw.kick_808,1);
        soundID[6] = sp.load(ctx,R.raw.openhat_slick_1,1);
        soundID[7] = sp.load(ctx,R.raw.perc_808,1);
        soundID[8] = sp.load(ctx,R.raw.snap_close_01,1);
        soundID[9] = sp.load(ctx,R.raw.snare_808,1);
        soundID[10] = sp.load(ctx,R.raw.snare_acoustic01,1);
        soundID[11] = sp.load(ctx,R.raw.snare_electro,1);
        soundID[12] = sp.load(ctx,R.raw.tom_8082,1);
        // init temper map
        for(int i = 0; i<5;i++){
            for (int j= 0; j<16;j++){
                tMaps[i][j] = 0;
                sMaps[i][j] = 0;
            }
        }

        // init circles
        for(int i = 0; i<5;i++){

            //canvas.drawCircle(100,700 + i*355,78,keyPaint);
            bigCircles[i] = new Point(100,700 + i*355);

            for(int j = 0;j<2;j++){
                for(int k = 0; k<8;k++){
                    circles[i][j][k] = new Point(260 + k*156,632 + j*135 + i*355);
                    //canvas.drawCircle(250 + k*156,632 + j*135 + i*355,55,keyPaint);
                }
            }
        }

        // inti Recy
         indexR[0] = new Rect(100,300,1340,700);

        indexR[1] = new Rect(100,800,1340,1200);

        // inti paint
        titlePaint.setColor(Color.WHITE);
        titlePaint.setFakeBoldText(true);
        titlePaint.setTextSize(120);

        detailPaint.setColor(Color.WHITE);
        detailPaint.setTextSize(80);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(100);
        // inti song
        String[][] str =new String[songCount][5];

        // song0
        str[0][0] =  "1000100010001111";
        str[0][1] =  "0001000100100010";
        str[0][2] =  "0100010000000000";
        str[0][3] =  "0000001001000000";
        str[0][4] =  "0000000000000000";
        types[0][0] = 6;types[0][1] = 12;types[0][2] = 10;types[0][3] = 11;types[0][4] = 0;
        completions[0] = 0;
        typeCount[0] = 4;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 16; j ++){
                maps[0][i][j] = Integer.parseInt(str[0][i].substring(j,j+1));
            }
        }
        songs[0] = new Song(0,83,completions[0],maps[0],types[0],"Song1");

        // song1

        str[1][0] =  "0100010001000100";
        str[1][1] =  "0001000100100010";
        str[1][2] =  "0000000000000000";
        str[1][3] =  "0000000000000000";
        str[1][4] =  "0000000000000000";
        types[1][0] = 5;types[1][1] = 0;types[1][2] = 0;types[1][3] = 0;types[1][4] = 0;
        completions[1] = 0;
        typeCount[1] = 2;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 16; j ++){
                maps[1][i][j] = Integer.parseInt(str[1][i].substring(j,j+1));
            }
        }
        songs[1] = new Song(1,50,completions[1],maps[1],types[1],"Song2");

        vibrator =(Vibrator)_ctx.getSystemService(Context.VIBRATOR_SERVICE);
        t_timer();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(48,48,48));
        Paint pt = new Paint();

        canvas.drawRect(0,0,1440,150,blackPaint);
        canvas.drawText("< BACK",50,90,detailPaint);

        if(inMode == SELECT_MODE) {
            isShake = false;
            pt.setColor(Color.rgb(66, 66, 66));
            for (int i = 0; i < songCount; i++) {
                canvas.drawRect(indexR[i], pt);
                // name
                canvas.drawText(songs[i].getName(), indexR[i].left + 50, indexR[i].top + 140, titlePaint);

                // details
                canvas.drawText("Speed: " + songs[i].getSpeed(), indexR[i].left + 60, indexR[i].top + 240, detailPaint);
                canvas.drawText("Difficulty: " + songs[i].getDifficulty(), indexR[i].left + 60, indexR[i].top + 340, detailPaint);

                // percentage
                canvas.drawCircle(indexR[i].right - 200, indexR[i].centerY(), 160, titlePaint);
                canvas.drawText(songs[i].getCompletion() + "%", indexR[i].right - 200 - 80, indexR[i].centerY() + 30, blackPaint);
            }
        }
        if(inMode == GAME_MODE) {


                for(int i = 0; i<songCount;i++) {

                        if (songs[i].getCompletion() == 100) {
                            if(!isShake) {
                                isShake = true;
                                try {
                                    vibrator.vibrate(1000);
                                    Log.d("test","shake");
                                }
                                catch (Exception e){
                                    Log.d("exception",e.getMessage());
                                }

                            }
                        }

                }







            // not song dependent
            Paint bgPt = new Paint();
            bgPt.setColor(Color.rgb(40,40,40));
            canvas.drawPaint(bgPt);

            // text
            canvas.drawText("START",237,300,detailPaint);
            canvas.drawText("BACK",955,300,detailPaint);
            canvas.drawText("Correctness: " + songs[inSong].getCompletion() + "%",430,150,detailPaint);
            // line

            Paint linePaint = new Paint();
            linePaint.setColor(Color.WHITE);
            linePaint.setStrokeWidth(20);
            canvas.drawLine(220,450,1220,450,linePaint);

            // jin du
            linePaint.setAlpha(120);
            linePaint.setStrokeWidth(75);

            if(currFrame == -1)
            progress = 0;
            else
            progress = (int)((currFrame+1)*6.25);

            canvas.drawLine(220,450,220+progress * 10,450,linePaint);

            linePaint.setAlpha(255);
            canvas.drawCircle(220+progress* 10,450,60,linePaint);
            canvas.drawCircle(220+progress* 10,450,40,blackPaint);

            linePaint.setStrokeWidth(7);
            // body
            for(int i = 0; i<typeCount[inSong];i++){
                keyPaint.setColor(colors[i]);
                keyPaint2.setColor(colors3[i]);



                canvas.drawLine(185,590 + i*355,185,810 + i*355,linePaint);
                canvas.drawLine(796,590 + i*355,796,810 + i*355,linePaint);
                canvas.drawCircle(bigCircles[i].x,bigCircles[i].y,78,keyPaint);

                Path sbt = new Path();
                sbt.moveTo(75,660+ i*355);
                sbt.lineTo(149,700+ i*355);
                sbt.lineTo(75,740+ i*355);

                canvas.drawPath(sbt,bgPt);
                int c;
                for(int j = 0;j<2;j++){
                    c = j*8;
                    for(int k = 0; k<8;k++){
                        if(tMaps[i][c+k]==0) {
                            canvas.drawCircle(circles[i][j][k].x, circles[i][j][k].y, 55, keyPaint);
                        }
                        else if(tMaps[i][c+k]==1){
                            canvas.drawCircle(circles[i][j][k].x, circles[i][j][k].y, 55, keyPaint2);
                        }
                    }
                }
            }
        }
    }

    Point pDown = new Point(0,0);
    Point pUp = new Point(0,0);
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                pDown.x = (int)event.getX();
                pDown.y = (int)event.getY();
                if(inMode == SELECT_MODE) {
                    if(backR_S.contains(pDown.x, pDown.y)){
                        // go back to main
                        Intent intent = new Intent();
                        intent.setClass(_ctx,main.class);
                        t.cancel();
                        _ctx.startActivity(intent);
                        ((PlayActivity) _ctx).finish();
                    }
                }

                if(inMode == GAME_MODE) {
                    for (int i = 0; i < typeCount[inSong]; i++) {

                        if (disVsPoint(pDown, bigCircles[i]) < 78) {
                            // play example
                            Log.d("Play", "Play Example");
                            sp.play(soundID[songs[inSong].getTypes()[i]], 1, 1, 10, 0, 1);
                        }

                        int c;
                        for (int j = 0; j < 2; j++) {
                            c = j * 8;
                            for (int k = 0; k < 8; k++) {

                                if (disVsPoint(pDown, circles[i][j][k]) < 55) {
                                    if (tMaps[i][c + k] == 0)
                                        tMaps[i][c + k] = 1;

                                    else if (tMaps[i][c + k] == 1) {
                                        tMaps[i][c + k] = 0;
                                    }


                                    for(int p = 0;p < 5; p ++) {
                                        for (int q = 0; q < 16; q++){
                                            if(tMaps[p][q] == maps[inSong][p][q] && tMaps[p][q]!=0){
                                                sMaps[p][q] = 1;
                                            }
                                            else if(tMaps[p][q] == 1 &&  maps[inSong][p][q] == 0)
                                            {
                                                sMaps[p][q] = 2;
                                            }
                                            else {
                                                sMaps[p][q] = 0;
                                            }
                                        }
                                    }
                                    int matched = 0;

                                    for(int p = 0;p < 5; p ++) {
                                        for (int q = 0; q < 16; q++){
                                            if(sMaps[p][q] == 1){
                                                matched++;
                                            }
                                            else if(sMaps[p][q] == 2){
                                                matched--;
                                            }
                                        }
                                    }

                                    int comp;

                                    comp = (matched*100/songs[inSong].getDifficulty());

                                    songs[inSong].setCompletion(comp);

                                    Log.d("comp","coore" + songs[inSong].getDifficulty());
                                }
                            }
                        }
                    }

                    if(playR.contains(pDown.x, pDown.y)){
                        if (isPlaying) {
                            isPlaying = false;
                            currFrame = 0;

                        } else if (!isPlaying) {
                            isPlaying = true;
                            frame = 0;
                            currFrame = -1;
                        }
                    }
                    if(backR.contains(pDown.x,pDown.y)){
                        // go back
                        inMode = SELECT_MODE;
                        isPlaying = false;
                        frame = 0;
                        currFrame = -1;
                        for(int i = 0; i<5;i++){
                            for (int j= 0; j<16;j++){
                                tMaps[i][j] = 0;
                                sMaps[i][j] = 0;
                            }
                        }
                    }

                }
                Log.d("pos",pDown.x + " " + pDown.y);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                pUp.x = (int)event.getX();
                pUp.y = (int)event.getY();


                if(inMode==SELECT_MODE){
                    for(int i = 0; i< songCount;i++){
                        if(indexR[i].contains(pUp.x,pUp.y)){
                            inMode = GAME_MODE;
                            inSong = songs[i].songID;
                            Log.d("insong", "inSong" + inSong);
                        }
                    }
                }

                break;
            }
        }
        return true;
    }

    private void t_timer(){
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        // test game state
                        if(isGameStart) {
                            // game loop

                            if (isPlaying) {

                                duration = 101 - songs[inSong].getSpeed();
                                if (frame % duration == 0) {

                                    currFrame++;
                                    if (currFrame >= 16) {
                                        currFrame = 0;
                                    }

                                    Log.d("curr", " current: " + currFrame);
                                    for(int i = 0; i<5;i++) {
                                        if (maps[inSong][i][currFrame] == 1) {
                                            sp.play(soundID[songs[inSong].getTypes()[i]], 1, 1, 10, 0, 1);
                                            //Log.d("YTeds", "tesdt");
                                        }
                                    }

                                }

                            }

                        }

                        // Update frame
                        postInvalidate();
                        frame++;
                    }
                },
                0,
                1000/fps
        );
    }
    public void setCompletions(int [] cmp){
        for(int i = 0; i<songCount;i++){
            songs[i].setCompletion(cmp[i]);
            Log.d("s","cmp" + cmp[i]);
        }
    }

    public int[] getCompletions(){
        int[] cmp = new int[songCount];
        for(int i = 0; i<songCount;i++){
            cmp[i] = songs[i].getCompletion();
            Log.d("s","cmp" + cmp[i]);
        }
        return cmp;
    }

// Distance vs two circle

    public float disVsPoint(Point a, Point b)
    {
        float d;

        d = (int)Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));

        return d;
    }
}
