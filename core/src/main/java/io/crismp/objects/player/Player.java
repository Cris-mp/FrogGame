package io.crismp.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import io.crismp.helper.Constants;

public class Player extends GameEntity {

    private int jumpCounter;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 2f;
        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;

        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        System.out.println("x:"+body.getPosition().x); //esto es para debugear
        System.out.println("y:"+body.getPosition().y);
    }

    private void checkUserInput() {
        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter < 2) {
            float force = body.getMass() * 12;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }
        // reseteamos el contador de salto
        if (body.getLinearVelocity().y == 0) {
            jumpCounter = 0;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 15 ? body.getLinearVelocity().y : 15);
    }

}
