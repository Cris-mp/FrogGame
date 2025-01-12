package io.crismp.frogGame;

import static io.crismp.helper.Constants.*;

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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.crismp.helper.TileMapHelper;
import io.crismp.objects.player.Player;

/** First screen of the application. Displayed after the application is created. */
public class PlayScreen implements Screen {

    private FrogGame frogGame;

    private OrthographicCamera camera;
    private Viewport gamePort;

    private TileMapHelper tileMapHelper; // carga y tiene la referancia del mapa
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer; //nuestro mapa en la pantalla

    //creamos un mundo y le ponemos fisicas
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;



    private SpriteBatch batch;
    private Player player;

    public PlayScreen(FrogGame game) {
        this.frogGame = game;
        //camara
        this.camera = new OrthographicCamera();
        this.gamePort=new FitViewport(V_WIDTH, V_HEIGHT,camera); //TODO:creo que esto me hace ir lento el juego en otras pantallas y no adaptarse
        //Fondo y mundo
        this.tileMapHelper = new TileMapHelper(this);
        this.world = new World(new Vector2(0,-25), true);
        this.orthogonalTiledMapRenderer= tileMapHelper.setupMap();//usa world asi que siempre debajo
        this.box2DDebugRenderer = new Box2DDebugRenderer();



        this.batch = new SpriteBatch();
    }

    /**
     * Aqui se realizan las actualizaciones de nuestro mundo
     */
    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        player.update();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        /* con scape sales del juego */
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }
    /**
     * Nos permite que la camara siga al personaje y se vaya actualizando con el movimiento
     */
    private  void cameraUpdate(){
        Vector3 position = camera.position;

        if(player.getBody().getPosition().x>9.5 && player.getBody().getPosition().x<10.5){
            position.x = Math.round(player.getBody().getPosition().x* PPM*10/10f);
        }else{
            if(player.getBody().getPosition().x<10.5){
                position.x = gamePort.getScreenWidth()/4+300;
            }else{
                 position.x = gamePort.getScreenWidth()/4+340;
            }
        }


        position.y =Math.round(player.getBody().getPosition().y* PPM*10/10f+90);





        camera.position.set(new Vector3(position));
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        this.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
        player.render(batch); //esto es para debugear

        batch.begin();

        batch.end();

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
        camera.setToOrtho(false,width/2, height/2);
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
