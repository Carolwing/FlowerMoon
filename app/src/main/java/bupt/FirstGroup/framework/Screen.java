package bupt.FirstGroup.framework;

import bupt.FirstGroup.framework.impl.RTGame;

/**
 * Created by Peter on 23.01.2017.
 */
public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);//逻辑

    public abstract void paint(float deltaTime);//前端

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();
}
