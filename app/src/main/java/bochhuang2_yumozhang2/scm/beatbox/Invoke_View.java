package bochhuang2_yumozhang2.scm.beatbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by bochhuang2 on 5/5/2017.
 */

public class Invoke_View extends View {

    // store data
    private int[][][] maps = new int[6][5][16];
    private int[][] types = new int[6][5];
    private int[] speeds = new int[6];
    private boolean[] inits = new boolean[6];

    boolean[][] in_init = new boolean[6][5];
    int saveScount = 2;

    Rect[] indexR = new Rect[6];

    Point pDown = new Point(0,0);

    Point addP = new Point(720, 75);
    Point removeP = new Point(1315, 75);

    Saved[] save_Arr = new Saved[6];

    Context _ctx;

    Point[][] bigCircles = new Point[6][16];

    // Paint
    // Paint
    int[] colors = {Color.rgb(247,235,140),Color.rgb(253,178,142),Color.rgb(255,139,189),Color.rgb(198,164,246),Color.rgb(165,200,255)};
    int[] colors2 = {Color.rgb(247-30,235-30,140-30),Color.rgb(253-30,178-30,142-30),Color.rgb(255-30,139-30,189-30),Color.rgb(198-30,164-30,246-30),Color.rgb(165-30,200-30,255-30)};
    int[] colors3 = {Color.rgb(247-90,235-90,140-90),Color.rgb(253-90,178-90,142-90),Color.rgb(255-90,139-90,189-90),Color.rgb(198-90,164-90,246-90),Color.rgb(165-90,200-90,255-90)};
    Paint keyPaint = new Paint();
    Paint keyPaint2 = new Paint();
    Paint keyPaint3 = new Paint();
    Paint titlePaint = new Paint();
    Paint detailPaint = new Paint();
    Paint blackPaint = new Paint();

    ArrayList<Saved> savedList = new ArrayList<Saved>();
    public Invoke_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Init(context);
    }
    public Invoke_View(Context context, AttributeSet attrs) {
        super(context, attrs);

        Init(context);
    }

    public Invoke_View(Context context) {
        super(context);

        Init(context);
    }

    public void Init(Context ctx){
        _ctx = ctx;
        // init saved
        save_Arr[0] = new Saved(0);
        save_Arr[0].setInit(true);
        save_Arr[1] = new Saved(1);
        save_Arr[1].setInit(true);

        int[] tys = new int[5];
        tys[2] = 6;tys[1] = 5;tys[0] = 12;
        int[][] mpss = new int[5][16];
        mpss[2][0] = 1;mpss[2][2] = 1;mpss[2][5] = 1;mpss[2][7] = 1;mpss[2][8] = 1;mpss[2][10] = 1;mpss[2][13] = 1;
        mpss[1][4] = 1;mpss[1][12] = 1;
        mpss[0][0] = 1;mpss[0][6] = 1;mpss[0][8] = 1;

        save_Arr[0].setMaps(mpss);save_Arr[0].setTypes(tys);save_Arr[0].setSpeed(75);
        save_Arr[1].setMaps(mpss);save_Arr[1].setTypes(tys);save_Arr[1].setSpeed(75);

        maps[0] = mpss;maps[1] = mpss;
        types[0] = tys;types[1] = tys;

        save_Arr[2] = new Saved(2);
        save_Arr[3] = new Saved(3);
        save_Arr[4] = new Saved(4);
        save_Arr[5] = new Saved(5);

        indexR[0] = new Rect(100,300,1340,700);

        savedList.add(0,save_Arr[0]);
        savedList.add(1,save_Arr[1]);

        // inti paint
        titlePaint.setColor(Color.WHITE);
        titlePaint.setFakeBoldText(true);
        titlePaint.setTextSize(120);

        detailPaint.setColor(Color.WHITE);
        detailPaint.setTextSize(80);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(100);

        for(int i = 0; i < 6 ;i++){
            indexR[i] = new Rect(100,300 + i*500,1340,700 + i*500);
            speeds[i] = 75;
        }

        int bigR = 130;
        // init big circles
        for(int j = 0; j<6; j++) {
            for (int i = 0; i < 16; i++) {
                bigCircles[j][i] = new Point((int) (indexR[j].right - 200 + bigR * Math.cos(i * 22.5 * Math.PI / 180)), (int) (indexR[j].centerY() + bigR * Math.sin(i * 22.5 * Math.PI / 180)));
            }
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(48,48,48));
        Paint pt = new Paint();

        canvas.drawRect(0,0,1440,150,blackPaint);
        canvas.drawText("< BACK",50,105,detailPaint);

        canvas.drawCircle(addP.x, addP.y, 50,titlePaint);
        canvas.drawCircle(removeP.x, removeP.y, 50, titlePaint);

        canvas.drawRect(690, addP.y - 8, 750,addP.y + 8,blackPaint);
        canvas.drawRect(addP.x - 8, addP.y - 30, addP.x + 8,addP.y + 30,blackPaint);
        canvas.drawRect(removeP.x - 30, removeP.y - 8, removeP.x + 30,removeP.y + 8,blackPaint);
        pt.setColor(Color.rgb(66, 66, 66));
        for (int i = 0; i < saveScount; i++) {
            if(savedList.get(i).isInit()) {
                canvas.drawRect(indexR[i], pt);
                // name
                canvas.drawText("Pattern " + savedList.get(i).getSaveId(), indexR[i].left + 50, indexR[i].top + 140, titlePaint);

                // details
                canvas.drawText("Speed: " +savedList.get(i).getSpeed(), indexR[i].left + 60, indexR[i].top + 240, detailPaint);
                //canvas.drawText("Difficulty: " + songs[i].getDifficulty(), indexR[i].left + 60, indexR[i].top + 340, detailPaint);

                // percentage
                canvas.drawCircle(indexR[i].right - 200, indexR[i].centerY(), 160, titlePaint);
                //canvas.drawText(songs[i].getCompletion() + "%", indexR[i].right - 200 - 80, indexR[i].centerY() + 30, blackPaint);


                Paint gp = new Paint();
                gp.setColor(Color.rgb(180,180,180));
                for (int j = 0; j < 16; j++) {
                    canvas.drawCircle(bigCircles[i][j].x, bigCircles[i][j].y, 20, gp);
                }

                for (int t = 0 ;t < 16; t++){
                    //canvas.drawCircle(bigCircles[i].x,bigCircles[i].y,70,grayPaint);
                    for(int j = 0; j<5; j++) {

                        if (maps[i][j][t] == 1) {
                            Paint tp = new Paint();
                            tp.setColor(colors2[j]);

                            for(int k = 0; k < 5; k++) {
                                if (maps[i][j][t] == maps[i][k][t] && j!=k){
                                    tp.setColor((colors2[j] + colors2[k])/2);

                                }
                                for(int l = 0; l < 5; l++){
                                    if (maps[i][j][t] == maps[i][k][t] && maps[i][k][t] == maps[i][l][t] && k!=l && l!= j && j!=k){
                                        tp.setColor((colors2[j] + colors2[k] + colors2[l])/3);

                                    }
                                }

                            }
                            Paint gryP = new Paint();
                            gryP.setColor(Color.rgb(100,100,100));
                            canvas.drawCircle(bigCircles[i][t].x, bigCircles[i][t].y, 25, gryP);
                            canvas.drawCircle(bigCircles[i][t].x, bigCircles[i][t].y, 20, tp);



                        }
                    }
                    //canvas.drawText(i+"",bigCircles[i].x,bigCircles[i].y,textPaint);
                    //canvas.drawLine(720,800,bigCircles[i].x,bigCircles[i].y,textPaint);
                }

            }

            if(isRemoving) {
                canvas.drawRect(720 - 40,495- 10 + i*500,720 + 40,495 + 10+ i*500,blackPaint);
            }
        }

        invalidate();
    }

    boolean isRemoving = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                pDown.x = (int)event.getX();
                pDown.y = (int)event.getY();

                Log.d("down", pDown.x + " " + pDown.y);

                for(int i = 0; i<saveScount;i++){
                    if(indexR[i].contains(pDown.x, pDown.y) && savedList.get(i).isInit()){

                        if(isRemoving){
                            // delete
                            savedList.get(i).reset();
                            savedList.remove(i);
                            saveScount--;
                        }
                        else{
                            // go to mode
                            Intent intent = new Intent();
                            intent.setClass(_ctx,MainActivity.class);

                            intent.putExtra("id",savedList.get(i).getSaveId());
                            intent.putExtra("speed",savedList.get(i).getSpeed());
                            for(int j = 0; j<5; j++){
                                intent.putExtra("types" + j,savedList.get(i).getTypes()[j]);
                                intent.putExtra("in_int" + j,savedList.get(i).getIn_init()[j]);
                                for(int k = 0; k<16; k++){
                                    intent.putExtra("maps" + j + k,savedList.get(i).getMaps()[j][k]);

                                }
                            }

                            _ctx.startActivity(intent);
                            ((SaveActivity) _ctx).finish();
                        }


                    }
                }

                // Back
                Rect rct = new Rect(18,3,300,111);
                    if(rct.contains(pDown.x, pDown.y)){
                        Intent intent = new Intent();
                        intent.setClass(_ctx,main.class);

                        _ctx.startActivity(intent);
                        ((SaveActivity) _ctx).finish();
                    }
                // add record
                if(disVsPoint(pDown,addP) <= 70 && !isRemoving){
                    //Log.d("ss","add");
                    for(int i = 0; i< 6; i++){
                        if(!save_Arr[i].isInit()){
                            save_Arr[i].setInit(true);
                            saveScount++;
                            savedList.add(saveScount-1,save_Arr[i]);

                            break;
                        }
                    }

                }

                // remove record
                if(disVsPoint(pDown,removeP) <= 70){
                    //Log.d("ss","remove");



                    if(saveScount > 0){
                            isRemoving = !isRemoving;
                    }
                    if(saveScount <1 && isRemoving){
                        isRemoving = false;
                    }
                }

                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
        }

        return true;
    }

    public void inputData(int[][][] mps, int[][] tps, boolean[] inis, int[] sps, boolean[][] in_in){
        saveScount = 0;
        maps = mps;
        types = tps;
        inits = inis;
        speeds = sps;
        in_init = in_in;
        for(int i = 0; i < 6; i ++){
            save_Arr[i].setMaps(mps[i]);
            save_Arr[i].setTypes(tps[i]);
            save_Arr[i].setSpeed(sps[i]);
            save_Arr[i].setInit(inis[i]);
            save_Arr[i].setIn_init(in_in[i]);

            if(save_Arr[i].isInit()){
                savedList.add(saveScount,save_Arr[i]);
                saveScount++;

            }

        }




    }

    public int[][][] getMaps(){
        int[][][] mps = new int[6][5][16];

        for(int i = 0; i < 6; i ++){
            mps[i] = save_Arr[i].getMaps();

        }
        return mps;
    }


    public int[][] getTypes() {
        int[][] tys = new int[6][5];

        for(int i = 0; i < 6; i ++){
            tys[i] = save_Arr[i].getTypes();

        }
        return tys;
    }


    public int[] getSpeeds() {
        int[] sps = new int[6];
        for(int i = 0; i < 6; i ++){
            sps[i] = save_Arr[i].getSpeed();

        }
        return sps;
    }


    public boolean[] getInits() {
        boolean [] inis = new boolean[6];
        for(int i = 0; i < 6; i ++){
            inis[i] = save_Arr[i].isInit();
        }
        return inis;
    }

    public boolean[][] getIn_init(){
        boolean [][] in_in = new boolean[6][5];
        for(int i =0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                in_in[i][j] = save_Arr[i].getIn_init()[j];
            }
        }

        return in_in;
    }

    // Distance vs two circle

    public float disVsPoint(Point a, Point b)
    {
        float d;

        d = (int)Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));

        return d;
    }

}
