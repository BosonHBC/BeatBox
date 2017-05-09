package bochhuang2_yumozhang2.scm.beatbox;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 黄波铖 on 2017/3/15.
 */

public class Edit_View extends View {
    static int EDIT_MODE = 1;
    static int VIEW_MODE = 0;
    // int
    int fps = 100;
    int[][] maps = new int[5][16];
    int[] soundID = new int[13];

    String[] spName = {"8BIT","KICK","HIHAT","HIHAT2","COWBELL","CLAP","SLICK","PERC","SNAP","SNARE","SNARE2","SNARE3","TOM"};

    int id = 0;

    int duration = 25;
    int currFrame = 0;

    int speed = 50;
    int deltaSP = 0;
    int deltaSelect = 0;

    int frame = 0;

    int sRW = 400;
    int sRH = 130;
    int sRN = 480;
    int sRM = 180;

    int sRsX = 200;
    int sRsY = 1800;


    int layerID = 0;

    int addButtonX = 1000;
    int addButtonY = 2200;

    int removeButtonX = 1020;
    int removeButtonY = 1465;

    int cirStartY = 1950;

    int dragCenterX = 720;
    int dragCenterY = 1700;

    int inMode = VIEW_MODE;
    //int inMode = EDIT_MODE;
    // float
    float spdRate = 4.65f;
    // boolean
    boolean isGameStart = true;
    boolean isPlaying = false;
    boolean isRemoving = false;

    boolean firstTime = true;
    // image

    // Rect

    Rect drag_bt = new Rect(665,1613,788,1730);
    Rect drag_bt_o = new Rect(665,dragCenterY - 59,788,dragCenterY + 59);
    Rect select_bt = new Rect(100,1800,1320,2200);

    Rect[] selectR = new Rect[13];  // width 200, height 120
    Rect backR = new Rect(200,1400,500,1500);

    // Point
    Point pDown = new Point(0,0);
    Point[] circles = new Point[16];
    Point start_bt = new Point(720,1450);

    Point[] bigCircles = new Point[16];
    // abstract data
    //int[] colors = {Color.rgb(129,194,214),Color.rgb(129,146,214),Color.rgb(217,179,230),Color.rgb(220,247,161),Color.rgb(131,252,216)};
    //int[] colors2 = {Color.rgb(129-30,194-30,214-30),Color.rgb(129-30,146-30,214-30),Color.rgb(217-30,179-30,230-30),Color.rgb(220-30,247-30,161-30),Color.rgb(131-30,252-30,216-30)};
    //int[] colors3 = {Color.rgb(129-90,194-90,214-90),Color.rgb(129-90,146-90,214-90),Color.rgb(217-90,179-90,230-90),Color.rgb(220-90,247-90,161-90),Color.rgb(131-90,252-90,216-90)};
    int[] colors = {Color.rgb(247,235,140),Color.rgb(253,178,142),Color.rgb(255,139,189),Color.rgb(198,164,246),Color.rgb(165,200,255)};
    int[] colors2 = {Color.rgb(247-30,235-30,140-30),Color.rgb(253-30,178-30,142-30),Color.rgb(255-30,139-30,189-30),Color.rgb(198-30,164-30,246-30),Color.rgb(165-30,200-30,255-30)};
    int[] colors3 = {Color.rgb(247-90,235-90,140-90),Color.rgb(253-90,178-90,142-90),Color.rgb(255-90,139-90,189-90),Color.rgb(198-90,164-90,246-90),Color.rgb(165-90,200-90,255-90)};

    Paint keyPaint = new Paint();
    Paint keyPaint2 = new Paint();
    Paint keyPaint3 = new Paint();
    Paint surround = new Paint();
    Paint textPaint = new Paint();
    Paint grayPaint = new Paint();

    Paint blackPaint = new Paint();

    Paint buttonCirclePaint = new Paint();

    ArrayList<Layer> layerList = new ArrayList<Layer>();

    Layer[] layers = new Layer[5];


    Path sbt = new Path();

    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);

    Timer t = new Timer();

    Context _ctx;

    ImageView[] toastImg = new ImageView[13];
    Toast toast;

    Resources res = getResources();
    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.center);

    public Edit_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Init(context);
    }
    public Edit_View(Context context, AttributeSet attrs) {
        super(context, attrs);

        Init(context);
    }

    public Edit_View(Context context) {
        super(context);

        Init(context);
    }



    private void Init(Context ctx) {
        _ctx = ctx;
        // init paint
        keyPaint.setColor(colors[0]);
        keyPaint2.setColor(colors2[0]);
        keyPaint3.setColor(colors3[0]);
        keyPaint3.setStrokeWidth(5);


        surround.setColor(Color.WHITE);

        textPaint.setTextSize(72);
        textPaint.setColor(Color.WHITE);
        textPaint.setFakeBoldText(true);
        textPaint.setStrokeWidth(3);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(18);

        grayPaint.setColor(Color.rgb(40,40,40));
        // init sound Pool

        // kick
        soundID[0] = sp.load(ctx,R.raw.kick_808,1);
        soundID[1] = sp.load(ctx,R.raw._8bit,1);

        soundID[2] = sp.load(ctx,R.raw.hihat_electro,1);
        soundID[3] = sp.load(ctx,R.raw.hihat_acoustic02,1);

        soundID[4] = sp.load(ctx,R.raw.cowbell808,1);
        soundID[5] = sp.load(ctx,R.raw.clap_tape,1);

        soundID[6] = sp.load(ctx,R.raw.openhat_slick_1,1);

        soundID[7] = sp.load(ctx,R.raw.perc_808,1);
        soundID[8] = sp.load(ctx,R.raw.snap_close_01,1);
        soundID[9] = sp.load(ctx,R.raw.snare_808,1);
        soundID[10] = sp.load(ctx,R.raw.snare_acoustic01,1);
        soundID[11] = sp.load(ctx,R.raw.snare_electro,1);
        soundID[12] = sp.load(ctx,R.raw.tom_8082,1);

        // init select Rect


        int c0;
        for (int i = 0; i<5;i++){
            c0 = i * 5;
            for (int j = 0; j<5; j++) {

                if(c0+j < soundID.length)
                selectR[c0 + j] = new Rect(sRsX + j * sRN, sRsY + sRM * i, sRsX + sRW + j * sRN, sRsY + sRH + sRM * i);
                else
                    break;
            }

        }

        if(firstTime) {
            // init layer
            layers[0] = new Layer(colors2[0], 0);

            layers[1] = new Layer(colors2[1], 1);

            layers[2] = new Layer(colors2[2], 2);

            layers[3] = new Layer(colors2[3], 3);
            layers[4] = new Layer(colors2[4], 4);




            // init map
            for (int i = 0; i < maps.length; i++) {
                for (int j = 0; j < maps[i].length; j++) {
                    maps[i][j] = 0;
                }
            }

            firstTime = false;
        }


        // init circle position
        int c;
        for(int i = 0; i<4;i++){
            c = i * 4;
            for (int j = 0; j<4; j++){
                circles[c+j] = new Point(0,0);
                circles[c+j].x = 230 + j*(60+260);
                circles[c+j].y = 230 + i*(60+260);

            }
        }
        // init start button path
        sbt = new Path();
        sbt.moveTo(703,1420);
        sbt.lineTo(703,1480);
        sbt.lineTo(756,1450);
        sbt.close();

        int bigR = 550;
        // init big circles
        for(int i = 0; i < 16; i++){
            bigCircles[i] = new Point((int)(720 + bigR * Math.cos(i*22.5*Math.PI/180)),(int)(700 + bigR * Math.sin(i*22.5*Math.PI/180)));
        }


for(int i = 0; i<13;i++){
    toastImg[i] = new ImageView(_ctx);
}

        toastImg[0].setImageResource(R.drawable.img_0);

        toastImg[1].setImageResource(R.drawable.img_01);

        toastImg[2].setImageResource(R.drawable.img_02);

        toastImg[3].setImageResource(R.drawable.img_03);

        toastImg[4].setImageResource(R.drawable.img_04);
        toastImg[5].setImageResource(R.drawable.img_05);

        toastImg[6].setImageResource(R.drawable.img_06);

        toastImg[7].setImageResource(R.drawable.img_07);

        toastImg[8].setImageResource(R.drawable.img_08);

        toastImg[9].setImageResource(R.drawable.img_09);
        toastImg[10].setImageResource(R.drawable.img_10);

        toastImg[11].setImageResource(R.drawable.img_11);

        toastImg[12].setImageResource(R.drawable.img_12);
        toast = new Toast(_ctx);


        t_timer();
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
                                if(inMode == VIEW_MODE){

                                }


                                if (frame % duration == 0) {

                                    currFrame++;
                                    if (currFrame >= 16) {
                                        currFrame = 0;
                                    }

                                    //Log.d("curr", " current: " + currFrame);
                                    for(int i = 0; i<5;i++) {
                                        if (maps[i][currFrame] == 1) {
                                            sp.play(soundID[layers[i].getSpID()], 1, 1, 10, 0, 1);
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(inMode == VIEW_MODE) {
            canvas.drawColor(Color.BLACK);

            canvas.save();
            canvas.scale(0.1f,0.1f);
            canvas.translate(10*(730 - bitmap.getWidth()/20),10*(700 - bitmap.getHeight()/20));
            canvas.drawBitmap(bitmap,0,0,null);
            //canvas.scale(5,5);



            canvas.restore();
            if(isPlaying){
                for(int i = 0; i< 16;i++) {
                    for(int j = 0; j<5;j++) {
                        if (currFrame == -1) {
                            Paint tp = new Paint();
                            tp.setColor(Color.WHITE);
                            canvas.drawCircle(bigCircles[0].x, bigCircles[0].y, 75, tp);
                        }
                        else if (i == currFrame) {
                            Paint tp = new Paint();
                            tp.setColor(Color.WHITE);
                            canvas.drawCircle(bigCircles[currFrame].x, bigCircles[currFrame].y, 75, tp);
                        }
                    }
                }

            }

            for (int i = 0 ;i < 16; i++) {
                canvas.drawCircle(bigCircles[i].x, bigCircles[i].y, 70, grayPaint);
            }

            // big circles
            for (int i = 0 ;i < 16; i++){
                //canvas.drawCircle(bigCircles[i].x,bigCircles[i].y,70,grayPaint);
                for(int j = 0; j<5; j++) {
                    if (maps[j][i] == 1) {
                        Paint tp = new Paint();
                        tp.setColor(colors2[j]);

                        for(int k = 0; k < 5; k++) {
                            if (maps[j][i] == maps[k][i] && j!=k){
                                tp.setColor((colors2[j] + colors2[k])/2);

                            }
                            for(int l = 0; l < 5; l++){
                                if (maps[j][i] == maps[k][i] && maps[k][i] == maps[l][i] && k!=l && l!= j && j!=k){
                                    tp.setColor((colors2[j] + colors2[k] + colors2[l])/3);

                                }
                            }

                        }
                        canvas.drawCircle(bigCircles[i].x, bigCircles[i].y, 70, tp);
                    }
                }
                //canvas.drawText(i+"",bigCircles[i].x,bigCircles[i].y,textPaint);
                //canvas.drawLine(720,800,bigCircles[i].x,bigCircles[i].y,textPaint);
            }


            canvas.drawLine(720-400,1600,720+400,1600,textPaint);
            int c1;
            // layer circle
            for(int i = 0; i<2;i++){
                c1 = i*3;
                for(int j = 0; j<3;j++) {
                    if( layerList.size()>c1+j) {
                        if(layerList.get(c1+j).isinit) {

                            if(isPlaying){

                                        if (maps[layerList.get(c1 + j).getLyID()][0] == 1 && currFrame == -1) {
                                            Paint tp = new Paint();
                                            tp.setColor(Color.WHITE);
                                            canvas.drawCircle(420 + j * 300, cirStartY + i * 300, 130, tp);

                                            //Log.d("test", layerList.get(c1 + j).getLyID() + "");
                                        } else if (currFrame != -1 && maps[layerList.get(c1 + j).getLyID()][currFrame] == 1) {
                                            Paint tp = new Paint();
                                            tp.setColor(Color.WHITE);
                                            canvas.drawCircle(420 + j * 300, cirStartY + i * 300, 130, tp);
                                            //Log.d("test", layerList.get(c1 + j).getLyID() + "");
                                        }


                            }

                            buttonCirclePaint.setColor(layerList.get(c1 + j).getColor());
                            canvas.drawCircle(420 + j * 300, cirStartY + i * 300, 120, buttonCirclePaint);



                            if (isRemoving) {
                                canvas.drawLine(420 + j * 300 - 50, cirStartY + i * 300, 420 + j * 300 + 50, cirStartY + i * 300, blackPaint);

                            }
                            canvas.drawText(layerList.get(c1 + j).getLyID() + "", 420 + j * 300, cirStartY + i * 300, textPaint);
                            if (layerList.size() != 3) {
                                addButtonX = 420 + (j + 1) * 300;
                                addButtonY = cirStartY + (i) * 300;
                            } else {
                                addButtonX = 420;
                                addButtonY = cirStartY + 300;
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }

            // start


            Paint pt = new Paint();
            pt.setColor(Color.rgb(117,117,117));

            canvas.drawCircle(start_bt.x, start_bt.y, 50, pt);
            pt.setColor(Color.rgb(40,40,40));
            canvas.drawPath(sbt, pt);

            drag_bt_o.left = (int)(speed * spdRate + 359);
            drag_bt_o.right = (int)(speed * spdRate + 359 + 113);
            //canvas.drawRect(dragCenterX - 367, dragCenterY - 55, dragCenterX + 367, dragCenterY +55, textPaint);
            canvas.drawRect(dragCenterX - 361, dragCenterY - 49, dragCenterX + 361, dragCenterY +49, pt);
            //canvas.drawCircle(dragCenterX - 458, dragCenterY, 54, textPaint);
            canvas.drawCircle(dragCenterX - 458, dragCenterY, 50, pt);
           // canvas.drawCircle(dragCenterX + 458, dragCenterY, 54, textPaint);
            canvas.drawCircle(dragCenterX + 458, dragCenterY, 50, pt);

            pt.setColor(Color.rgb(117,117,117));
            canvas.drawRect(drag_bt_o, pt);
            canvas.drawText(speed + "", dragCenterX-30, dragCenterY+23, textPaint);

            // go back button
            //canvas.drawRect(backR,textPaint);
            canvas.drawText("< BACK",252,1485,textPaint);



            // add button
            canvas.drawCircle(addButtonX,addButtonY,100,textPaint);
            canvas.drawLine(addButtonX,addButtonY-50,addButtonX, addButtonY+50,blackPaint);
            canvas.drawLine(addButtonX-50,addButtonY,addButtonX+50, addButtonY,blackPaint);

            // remove button
            canvas.drawCircle(removeButtonX,removeButtonY,90,textPaint);
            canvas.drawLine(removeButtonX-50,removeButtonY,removeButtonX+50, removeButtonY,blackPaint);
        }


        if(inMode == EDIT_MODE) {
            // bg
            canvas.drawRect(new Rect(0, 0, 1440, 2560), keyPaint);


            // isPlaying
            if (isPlaying) {


                if (currFrame == -1) {
                    canvas.drawCircle(circles[0].x, circles[0].y, 140, textPaint);
                } else
                    canvas.drawCircle(circles[currFrame].x, circles[currFrame].y, 140, textPaint);


            }

            // 16 circle
            for (int i = 0; i < maps[layerID].length; i++) {
                if (maps[layerID][i] == 1) {
                    canvas.drawCircle(circles[i].x, circles[i].y, 130, keyPaint3);
                } else
                    canvas.drawCircle(circles[i].x, circles[i].y, 130, keyPaint2);
            }
            // UI
            canvas.drawCircle(start_bt.x, start_bt.y, 50, keyPaint3);
            canvas.drawPath(sbt, keyPaint);
            canvas.drawLine(0, 1580, 1440, 1580, keyPaint3);
//665,1623,788,1720

            //speed = (int) ((drag_bt.left - 364) / spdRatef);
            drag_bt.left = (int)(speed*spdRate + 364);
            drag_bt.right = (int)(speed*spdRate + 364 + 113);
            // speed
            canvas.drawRect(365, 1623, 1088, 1720, keyPaint2);
            canvas.drawCircle(1178, 1672, 50, keyPaint2);
            canvas.drawCircle(275, 1672, 50, keyPaint2);
            canvas.drawRect(drag_bt, keyPaint3);
            canvas.drawText(speed + "", 685, 1700, textPaint);
            // go back button
            canvas.drawRect(backR,keyPaint3);
            canvas.drawText("< BACK",242,1475,textPaint);



            for (int i = 0; i < soundID.length; i++) {
                canvas.drawRect(selectR[i], keyPaint2);
                canvas.drawRect(selectR[layers[layerID].getSpID()], keyPaint3);

            }

            for(int i = 0; i<spName.length;i++){
                canvas.drawText(spName[i], selectR[i].centerX() - spName[i].length()*72/4, selectR[i].centerY() + 18, textPaint);
            }

        }
    }

    boolean[] insided = new boolean[16];

    boolean isDown = false;
    boolean isControlSP = false;
    boolean isSelect = false;
    int dX = 0;
    int preX = 0;
    int dX2 = 0;
    int preX2 = 0;

    int thisI = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                isDown = true;
                pDown.x = (int)event.getX();
                pDown.y = (int) event.getY();

                Log.d("mess",pDown.x + " " +pDown.y );
                //Log.d("mess2",layerList.size()+" size");
                if(inMode == EDIT_MODE) {
                    for (int i = 0; i < insided.length; i++) {
                        insided[i] = false;
                    }

                    // tap on and off
                    for (int i = 0; i < maps[layerID].length; i++) {
                        if (disVsPoint(pDown, circles[i]) < 130 && !insided[i]) {
                            insided[i] = true;
                            firstTime = false;
                            if (maps[layerID][i] == 0) {
                                maps[layerID][i] = 1;
                            } else if (maps[layerID][i] == 1) {
                                maps[layerID][i] = 0;
                            }
                        }
                    }

                    // start
                    if (disVsPoint(pDown, start_bt) < 50) {
                        if (isPlaying) {
                            isPlaying = false;
                            currFrame = 0;

                            sbt = new Path();
                            sbt.moveTo(703,1420);
                            sbt.lineTo(703,1480);
                            sbt.lineTo(756,1450);
                            sbt.close();

                        } else if (!isPlaying) {
                            isPlaying = true;
                            frame = 0;
                            currFrame = -1;

                            sbt = new Path();
                            sbt.moveTo(start_bt.x - 20,start_bt.y - 20);
                            sbt.lineTo(start_bt.x + 20,start_bt.y - 20);
                            sbt.lineTo(start_bt.x + 20,start_bt.y + 20);
                            sbt.lineTo(start_bt.x - 20,start_bt.y + 20);
                            sbt.close();

                        }

                    }

                    // select
                    if (select_bt.contains(pDown.x, pDown.y) && !isSelect) {
                        isSelect = true;
                        dX2 = pDown.x;
                        preX2 = selectR[0].left;


                    }

                    if (drag_bt.contains(pDown.x, pDown.y) && !isControlSP) {
                        isControlSP = true;
                        dX = pDown.x;
                        preX = drag_bt.left;
                    }

                    for (int i = 0; i < soundID.length; i++) {
                        if (selectR[i].contains(pDown.x, pDown.y)) {
                            thisI = i;
                        }

                    }
                }
                if(inMode == VIEW_MODE){
                    // start
                    if (disVsPoint(pDown, start_bt) < 50) {
                        if (isPlaying) {
                            isPlaying = false;
                            currFrame = 0;
                            sbt = new Path();
                            sbt.moveTo(703,1420);
                            sbt.lineTo(703,1480);
                            sbt.lineTo(756,1450);
                            sbt.close();

                        } else if (!isPlaying) {
                            isPlaying = true;
                            frame = 0;
                            currFrame = -1;
                            sbt = new Path();
                            sbt.moveTo(start_bt.x - 25,start_bt.y - 25);
                            sbt.lineTo(start_bt.x + 25,start_bt.y - 25);
                            sbt.lineTo(start_bt.x + 25,start_bt.y + 25);
                            sbt.lineTo(start_bt.x - 25,start_bt.y + 25);
                            sbt.close();
                        }

                    }
                    // add
                    if(disVsPoint(pDown,new Point(addButtonX,addButtonY)) < 80 && !isRemoving){
                        Random rd = new Random();
                        if(layerList.size()<5){
                            for(int i = 0;i<10000;i++){
                                int t;
                                t = rd.nextInt(5);
                                //Log.d("mess2",t+" t");
                                if(!layers[t].isinit){
                                    layerList.add(layerList.size(),layers[t]);
                                    layers[t].isinit = true;

                                    break;
                                }
                            }
                        }
                    }
                    // remove
                    if(disVsPoint(pDown,new Point(removeButtonX,removeButtonY))<80){
                        isRemoving = !isRemoving;
                    }

                    if (drag_bt_o.contains(pDown.x, pDown.y) && !isControlSP) {
                        isControlSP = true;
                        dX = pDown.x;
                        preX = drag_bt_o.left;
                    }

                    if(isRemoving) {
                        int c1;
                        // layer circle
                        for(int i = 0; i<2;i++){
                            c1 = i*3;
                            for(int j = 0; j<3;j++) {
                                if(layerList.size()>c1+j &&  layerList.get(c1+j).isinit) {
                                    //canvas.drawCircle(420  + j*300, 1800+ i*300, 120, buttonCirclePaint);
                                    if(disVsPoint(pDown,new Point(420  + j*300,cirStartY+ i*300))<120){  // correct remove
                                        if(layerList.size()>1) {
                                            layerList.get(c1+j).isinit = false;
                                            layerList.get(c1+j).setSpID(0);
                                            for(int k = 0; k<16;k++) {
                                                maps[layerList.get(c1 + j).getLyID()][k] = 0;
                                            }
                                            layerList.remove(c1 + j);


                                            //Log.d("test",layers[0].isinit + " " +layers[1].isinit + " " +layers[2].isinit + " " +layers[3].isinit + " " +layers[4].isinit + " ");
                                        }
                                    }

                                }
                                else{
                                    break;
                                }
                            }
                        }
                    }

                    // enter
                    if(!isRemoving){
                        int c1;
                        // layer circle
                        for(int i = 0; i<2;i++){
                            c1 = i*3;
                            for(int j = 0; j<3;j++) {
                                if(layerList.size()>c1+j &&  layerList.get(c1+j).isinit) {

                                    if(disVsPoint(pDown,new Point(420  + j*300,cirStartY+ i*300))<120){
                                        // correct go in to edit view
                                        layerID = layerList.get(c1+j).getLyID();
                                        keyPaint.setColor(colors[layerID]);
                                        keyPaint2.setColor(colors2[layerID]);
                                        keyPaint3.setColor(colors3[layerID]);
                                        inMode = EDIT_MODE;
                                        break;
                                    }

                                }
                                else{
                                    break;
                                }
                            }
                        }
                    }

                    if(backR.contains(pDown.x,pDown.y)){

                        // go back to main

                        Intent intent = new Intent();
                        intent.setClass(_ctx,SaveActivity.class);
                        t.cancel();
                        _ctx.startActivity(intent);
                        ((MainActivity) _ctx).finish();

                    }

                }


                break;
            }
            case MotionEvent.ACTION_MOVE:{
                pDown.x = (int)event.getX();
                pDown.y = (int) event.getY();
                if(inMode == EDIT_MODE) {
                    for (int i = 0; i < maps[layerID].length; i++) {
                        if (disVsPoint(pDown, circles[i]) < 130 && !insided[i]) {
                            insided[i] = true;
                            if (maps[layerID][i] == 0) {
                                maps[layerID][i] = 1;
                            } else if (maps[layerID][i] == 1) {
                                maps[layerID][i] = 0;
                            }
                        }
                    }

                    if (isControlSP) {
                        deltaSP = (pDown.x - dX);


                        drag_bt = new Rect(preX + deltaSP, 1613, preX + 113 + deltaSP, 1730);

                        if (drag_bt.left <= 365) {
                            drag_bt = new Rect(365, 1613, 365 + 113, 1730);
                        }
                        if (drag_bt.right >= 1088) {
                            drag_bt = new Rect(1088 - 113, 1613, 1088, 1730);
                        }

                        speed = (int) ((drag_bt.left - 364) / spdRate);
                        if (speed <= 0) {
                            speed = 1;
                        }

                        duration = (int)(1000/speed);
                    }

                    if (isSelect) {
                        deltaSelect = (pDown.x - dX2);
                        int c0;
                        for (int i = 0; i < 5; i++) {
                            c0 = i * 5;
                            for (int j = 0; j < 5; j++) {

                                if (c0 + j < soundID.length) {
                                    selectR[c0 + j] = new Rect(preX2 + j * sRN + deltaSelect, sRsY + sRM * i, preX2 + sRW + j * sRN + deltaSelect, sRsY + sRH + sRM * i);


                                    if(selectR[0].left <= -1000){
                                        selectR[c0 + j] = new Rect(-1000 + j * sRN, sRsY + sRM * i, -1000 + sRW + j * sRN, sRsY + sRH + sRM * i);
                                    }
                                    Log.d("test",selectR[4].right + "");
                                    if(selectR[4].right >= 2520){
                                        selectR[c0 + j] = new Rect(200 + j * sRN, sRsY + sRM * i, 200 + sRW + j * sRN, sRsY + sRH + sRM * i);
                                    }
                                }
                                    else
                                    break;
                            }

                        }


                    }
                }

                if(inMode == VIEW_MODE){

                    if (isControlSP) {
                        deltaSP = (pDown.x - dX);


                        drag_bt_o = new Rect(preX + deltaSP, dragCenterY-59, preX + 113 + deltaSP, dragCenterY+59);

                        if (drag_bt_o.left <= dragCenterX - 361) {
                            drag_bt_o = new Rect(dragCenterX - 361, dragCenterY-59, dragCenterX - 361 + 113, dragCenterY+59);
                        }
                        if (drag_bt_o.right >= dragCenterX + 361) {
                            drag_bt_o = new Rect(dragCenterX + 361 - 113, dragCenterY-59, dragCenterX + 361, dragCenterY+59);
                        }

                        speed = (int) ((drag_bt_o.left - 359) / spdRate);
                        if (speed <= 0) {
                            speed = 1;
                        }

                        duration = (int)(1000/speed);
                    }


                }

                break;
            }

            case MotionEvent.ACTION_UP:{
                isDown = true;
                isControlSP = false;
                isSelect = false;
                if(inMode == EDIT_MODE) {
                    int upX = (int) event.getX();
                    int upY = (int) event.getY();

                    for (int i = 0; i < soundID.length; i++) {
                        if (selectR[i].contains(upX, upY) && thisI == i) {

                            layers[layerID].setSpID(i);

                            id = i;

                            toast.setView(toastImg[i]);
                            toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }

                    }

                    if(backR.contains(upX,upY)){
                        inMode = VIEW_MODE;
                    }
                }
                break;
            }
        }

        return true;
    }


    public void setInits(boolean[] ints){
        for(int i = 0; i<5; i++){
            layers[i].isinit = ints[i];

            if(layers[i].isinit && !firstTime){

                layerList.add(layerList.size(),layers[i]);
            }
        }


    }

    public void setSpeed(int spd){
        speed = spd;
    }
    public int getSpeed(){
        return  speed;
    }

    public void setMaps(int[][] mps){
        maps = mps;
    }

    public void setSpID(int[] ids){
        for(int i = 0; i<5;i++){
            layers[i].setSpID(ids[i]);
        }
    }

    public int[][] getMaps(){
        return maps;
    }
    public int[] getSpID(){
            int[] ids = new int[5];
            for(int i = 0; i< 5; i++){
                ids[i] = layers[i].getSpID();
            }

        return ids;
    }

    public boolean[] getinit(){
        boolean[] inits = new boolean[5];
        for (int i = 0; i<5; i++){
            inits[i] = layers[i].isinit;
        }
        return inits;
    }

    public boolean getFirst(){
        return firstTime;
    }
    public void setFirst(boolean ft){
        firstTime = ft;
        if(firstTime){
            layers[0].isinit = true;
            layers[1].isinit = true;
            layers[2].isinit = true;
            layerList.add(0, layers[0]);
            layerList.add(1, layers[1]);
            layerList.add(2, layers[2]);

            speed = 75;
            duration = (int)(1000/speed);


            layers[2].setSpID(6);
            maps[2][0] = 1;maps[2][2] = 1;maps[2][5] = 1;maps[2][7] = 1;maps[2][8] = 1;maps[2][10] = 1;maps[2][13] = 1;

            layers[1].setSpID(5);
            maps[1][4] = 1;maps[1][12] = 1;

            layers[0].setSpID(12);
            maps[0][0] = 1;maps[0][6] = 1;maps[0][8] = 1;
        }
    }

    // Distance vs two circle

    public float disVsPoint(Point a, Point b)
    {
        float d;

        d = (int)Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));

        return d;
    }

}
