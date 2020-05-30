package bupt.FirstGroup.framework.impl;


import android.graphics.Bitmap;
import android.graphics.Matrix;

import bupt.FirstGroup.framework.Image;
import bupt.FirstGroup.framework.Graphics.ImageFormat;

public class RTImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    private boolean isrest;

    public RTImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
        isrest=false;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }

    @Override
    public Image resetSize(int _width,int _height){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        //设置想要的大小
        int newWidth=_width;
        int newHeight=_height;

        //计算压缩的比率
        float scaleWidth=((float)newWidth)/width;
        float scaleHeight=((float)newHeight)/height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        isrest=true;
        return this;
    }
    @Override
    public boolean getIsrest(){
        return isrest;
    }
}
