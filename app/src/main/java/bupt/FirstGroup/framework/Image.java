package bupt.FirstGroup.framework;

import android.graphics.Bitmap;

public interface Image {
    public int getWidth();
    public int getHeight();
    public Graphics.ImageFormat getFormat();
    public void dispose();
    public Image resetSize(int _width, int _height);
    public boolean getIsrest();
}
