package bupt.FirstGroup.game.models;

public class Ball {


    public int x;
    public int y;
    public int type;
    private double speedMultiplier;
    private float time;

    public Ball(int x, int y, int type, float time){
        this.x = x;
        this.y = y;
        this.type = type;
//        this.speedMultiplier = type == BallType.Speeder ? 1.4 : 1;
        this.time=time;
    }

    public void update(int speed) {
        this.y += speed;
    }

    public float getTime() {
        return time;
    }

    public int getType() {
        return type;
    }
}
