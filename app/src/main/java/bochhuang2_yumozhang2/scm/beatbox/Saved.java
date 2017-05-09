package bochhuang2_yumozhang2.scm.beatbox;

/**
 * Created by bochhuang2 on 5/5/2017.
 */

public class Saved {

    private int saveId = 0;
    private int speed = 75;
    private int[][] maps = new int[5][16];
    private int[]   types = new int[5];

private boolean[] in_init = new boolean[5];

    private boolean isInit = false;
    public Saved(int id){

        setSaveId(id);

    }

    public void reset(){
        speed = 75;
        isInit = false;
        for(int i = 0;i<5; i++){
            getIn_init()[i] = false;
            types[i] = 0;
            for(int j = 0; j < 16; j++){
                maps[i][j] = 0;
            }
        }
    }

    public int getSaveId() {
        return saveId;
    }

    public void setSaveId(int saveId) {
        this.saveId = saveId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[][] getMaps() {
        return maps;
    }

    public void setMaps(int[][] maps) {
        this.maps = maps;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public boolean[] getIn_init() {
        return in_init;
    }

    public void setIn_init(boolean[] in_init) {
        this.in_init = in_init;
    }
}
