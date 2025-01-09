package io.crismp.frogGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public static Main INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Main(){
        INSTANCE=this;
        
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen=Gdx.graphics.getHeight();
        this.orthographicCamera= new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen/2, heightScreen/2);
        setScreen(new FirstScreen(orthographicCamera));
    }
}