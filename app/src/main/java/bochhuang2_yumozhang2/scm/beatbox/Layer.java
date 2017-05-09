package bochhuang2_yumozhang2.scm.beatbox;

/**
 * Created by 黄波铖 on 2017/4/29.
 */

public class Layer {
    private int color;
    private int spID = 0;
    private int lyID;
    private int[] map;
    public boolean isinit = false;
    public Layer(int co, int id){
        setMap(new int[16]);
        setColor(co);
        setLyID(id);
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public int getSpID() {
        return spID;
    }

    public void setSpID(int spID) {
        this.spID = spID;
    }

    public int getLyID() {
        return lyID;
    }

    public void setLyID(int lyID) {
        this.lyID = lyID;
    }
}
