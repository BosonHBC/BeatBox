package bochhuang2_yumozhang2.scm.beatbox;

/**
 * Created by 黄波铖 on 2017/5/1.
 */

public class Song {
    int songID;
    private int difficulty = 0;
    private int completion = 0;
    private int speed = 0;
    int[][] maps = new int[5][16];
    private int[]   types = new int[5];
    private String name;

    public Song(int id, int spd, int com, int[][] mps, int[] tps, String nme){
        songID = id;
         setSpeed(spd);
        maps = mps;
        setTypes(tps);
        setName(nme);

        for(int i = 0; i < 5; i ++){
            for(int j = 0; j<16; j++){
                if(maps[i][j]==1){
                    setDifficulty(getDifficulty() + 1);
                }
            }
        }

        setCompletion(com);
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }
}
