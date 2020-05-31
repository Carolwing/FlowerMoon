package bupt.FirstGroup.game;

import java.io.InputStream;

import bupt.FirstGroup.framework.Image;
import bupt.FirstGroup.framework.Music;
import bupt.FirstGroup.framework.Sound;
//import bupt.FirstGroup.framework.impl.*;
import bupt.FirstGroup.framework.impl.ButtonImage;

class Assets {
    //特效资源图集
    static Image[] anim1;//触摸波纹
    // Graphics
    //背景图片
    static Image background;
    //游戏结束界面（不需要
    static Image gameover;
    //暂停按钮
    static Image pause;
    //复原按钮
    static Image play;
    //普通音符
    static Image ballNormal;
    //多种音符（不需要
    static Image ballMultiplier;
    static Image ballOneUp;
    static Image ballSpeeder;
    static Image ballBomb;
    static Image explosion;
    static Image ballSkull;
    static Image explosionBright;
    static Image sirens;

    //血量框架
    static Image hpframe;
    //血条
    static Image hp;
    //顶框
    static Image toprect;
    //游戏进行中的分数栏
    static Image score;
    //？
    static Image skillscore;
    //暂停键按下
    static Image pauseclicked;
    //占位
    static Image placeholder;
    //技术分文字
    static Image streak;
    //底部击中点
    static Image scale;
    //花朵
    static Image flower_key1;
    static Image flower_key2;
    //连击数文字
    static Image combo;
    //三种程度提示
    static Image perfect;
    static Image great;
    static Image miss;
    //结束界面背景
    static Image score_bg;
    //结束界面结果
    static Image success;
    static Image fail;
    //结束界面按钮
    static Image return_;
    static Image continue_;
    static Image resume_;

    // Audio
    static Sound soundClick;
    static Sound soundExplosion;
    static Sound soundCreepyLaugh;

    //音乐
    static Music musicTrack;
    //音谱
    static InputStream musicScore;

}