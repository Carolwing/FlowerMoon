package bupt.FirstGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class ImageAnimation extends AppCompatActivity{

    private MyView myView;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        /*
        //使用FrameLayout布局管理器，它允许组件组件控制位置
        FrameLayout frame=new FrameLayout(this);
        setContentView(frame);
        myView=new MyView(this);
        //设置myView用于显示 touch 动画
        myView.setBackgroundResource(R.drawable.touch);
        //设置 myView 为默认隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim=(AnimationDrawable)myView.getBackground();
        frame.addView(myView);
        frame.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View source, MotionEvent event){
                //处理按下事件
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    //先停止播放动画
                    anim.stop();
                    float x=event.getX();
                    float y=event.getY();
                    //控制myview的显示位置
                    myView.setLocation((int)y-40,(int)x-40);
                    ////setVisibility已经播放了动画，不需要再写anim.start();
                    myView.setVisibility(View.VISIBLE);
                    anim.start();
                }
                return false;
            }

        });*/

        Button btn1=(Button)findViewById(R.id.btn_test1);
        final ImageView imageView=(ImageView)findViewById(R.id.image_test1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimTouch(1,1);
                //showAnimRotate(imageView);
            }
        });
    }
    //旋转消失
    public void showAnimRotate(ImageView imageView){
        //获取旋转加消失的动画资源
        final Animation rotate= AnimationUtils.loadAnimation(ImageAnimation.this,R.anim.rotate);
        imageView.startAnimation(rotate);
    }

    //触碰效果
    public void showAnimTouch(final int x,final int y){
        //使用FrameLayout布局管理器，它允许组件组件控制位置
        FrameLayout frame=new FrameLayout(this);
        setContentView(frame);
        myView=new MyView(this);
        //设置myView用于显示 touch 动画
        myView.setBackgroundResource(R.drawable.touch);
        //设置 myView 为默认隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim=(AnimationDrawable)myView.getBackground();
        frame.addView(myView);
        //anim.stop();
        myView.setLocation(y-40,x-40);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    //消失效果
    public void showAnimDisappear(int x,int y){
        //使用FrameLayout布局管理器，它允许组件组件控制位置
        FrameLayout frame=new FrameLayout(this);
        setContentView(frame);
        myView=new MyView(this);
        //设置myView用于显示 touch 动画
        myView.setBackgroundResource(R.drawable.disappeatflower);
        //设置 myView 为默认隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim=(AnimationDrawable)myView.getBackground();
        frame.addView(myView);
        //anim.stop();
        myView.setLocation(y-40,x-40);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    //完美击中
    public void showAnimPerfecthit(int x,int y){
        //使用FrameLayout布局管理器，它允许组件组件控制位置
        FrameLayout frame=new FrameLayout(this);
        setContentView(frame);
        myView=new MyView(this);
        //设置myView用于显示 touch 动画
        myView.setBackgroundResource(R.drawable.perfecthit);
        //设置 myView 为默认隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim=(AnimationDrawable)myView.getBackground();
        frame.addView(myView);
        //anim.stop();
        myView.setLocation(y-40,x-40);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    //优秀击中
    public void showAnimGreathit(int x,int y){
        //使用FrameLayout布局管理器，它允许组件组件控制位置
        FrameLayout frame=new FrameLayout(this);
        setContentView(frame);
        myView=new MyView(this);
        //设置myView用于显示 touch 动画
        myView.setBackgroundResource(R.drawable.greathit);
        //设置 myView 为默认隐藏
        myView.setVisibility(View.INVISIBLE);
        //获取动画对象
        anim=(AnimationDrawable)myView.getBackground();
        frame.addView(myView);
        //anim.stop();
        myView.setLocation(y-40,x-40);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    //定义一个自定义View，用于播放动画效果
    class MyView extends androidx.appcompat.widget.AppCompatImageView{

        public MyView(Context context){
            super(context);
        }

        //定义一个方法，用于控制MyView的显示位置
        public void setLocation(int top,int left){
            this.setFrame(left,top,left+40,top+40);
        }

        //重写该方法，控制如果动画播放到最后一帧，隐藏该View
        @Override
        protected void onDraw(Canvas canvas){
            try
            {
                //反射调用AnimationDrawable里的mCurFrame值
                Field field = AnimationDrawable.class.getDeclaredField("mCurFrame");
                field.setAccessible(true);
                //获取anim动画当前帧
                int curFrame = field.getInt(anim);
                //如果已经到了最后一帧
                if(curFrame == anim.getNumberOfFrames() - 1)
                {
                    //让该View隐藏
                    setVisibility(View.INVISIBLE);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            super.onDraw(canvas);
        }
    }

}
