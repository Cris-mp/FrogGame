package io.crismp.frogGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import io.crismp.helper.Constants;
import io.crismp.helper.TileMapHelper;
import io.crismp.objects.player.Player;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    private Player player;

    public FirstScreen(OrthographicCamera camera) {
        this.camera=camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,-25), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer= tileMapHelper.setupMap();
    }

    
    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        player.update();
    

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }
    private  void cameraUpdate(){
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x*Constants.PPM*10/10f);
        position.y = Math.round(player.getBody().getPosition().y*Constants.PPM*10/10f);
        camera.position.set(new Vector3(position));
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        this.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        player.render(batch); //esto es para debugear

        batch.begin();

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));

    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player){
        this.player = player;
    }





    @Override
    public void show() {  
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        
    }
}