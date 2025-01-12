package io.crismp.frogGame;
import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class FrogGame extends Game {

    

    @Override
    public void create() {
       
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}