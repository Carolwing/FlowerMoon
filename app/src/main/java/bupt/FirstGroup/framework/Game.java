package bupt.FirstGroup.framework;

import android.content.Context;
import android.os.Vibrator;

import bupt.FirstGroup.models.Difficulty;

/**
 * Created by Peter on 23.01.2017.
 */

public interface Game {

    public Context getContext();

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Vibrator getVibrator();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public void goToActivity(Class<?> activity);

    public void goToActivity(Class<?> activity, String TAG, Difficulty difficulty);
}
