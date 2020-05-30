package bupt.FirstGroup.game.models;

public class MusicPoint {
    private int time;
    private int type;//1-6

    public MusicPoint(int type, int time){
        this.type=type;
        this.time=time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public int getType() {
        return type;
    }
}
