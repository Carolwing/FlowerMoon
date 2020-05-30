package bupt.FirstGroup.framework.impl;

import android.animation.Animator;

import bupt.FirstGroup.framework.Graphics;
import bupt.FirstGroup.framework.Image;

public class AnimatorImage {
    int x;//坐标
    int y;
    Image [] anim1;//系列的动图图片
    int i;//当前加载到的动图
    int max_i;
    int speed;

    public AnimatorImage(Image[] image, int x, int y, int speed) {
        this.anim1 = image;
        this.x=x;
        this.y=y;
        this.max_i=anim1.length;
        this.i=0;
        this.speed=speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getMax_i() {
        return max_i;
    }

    public Image getAnim1(int i) {
        return anim1[i];
    }

    public int getSpeed() {
        return speed;
    }
}
