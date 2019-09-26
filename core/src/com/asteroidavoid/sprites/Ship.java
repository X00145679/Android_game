package com.asteroidavoid.sprites;

import com.asteroidavoid.states.DeadState;
import com.asteroidavoid.states.GameStateManager;
import com.asteroidavoid.states.PlayState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ship {
    private static final int GRAVITY = -10;
    private int MOVEMENT =  100,score;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture texture;
    private Animation shipAnimation;
    private Sound blast;
    private GameStateManager gsm;
    private PlayState playState;


    public Ship(int x, int y, GameStateManager gsm, PlayState playState){
        this.gsm = gsm;
        this.playState = playState;
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("ship.png");
        shipAnimation = new Animation(new TextureRegion(texture),3,0.3f);
        bounds= new Rectangle(x,y,texture.getWidth()/3,texture.getHeight());
        blast = Gdx.audio.newSound(Gdx.files.internal("blast.wav"));
    }

    public void update(float dt){
        if(dt>0) {
            shipAnimation.update(dt);
            if (position.y > 0)
                velocity.add(0, GRAVITY, 0);
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(MOVEMENT * dt, velocity.y, 0);
            if (position.y < 0) {
                position.y = 0;
                gsm.set(new DeadState(gsm, playState.getScore()));
            }
            velocity.scl(1 / dt);
            bounds.setPosition(position.x, position.y);
        }


    }

    public int getMOVEMENT() {
        return MOVEMENT;
    }

    public void setMOVEMENT(int MOVEMENT) {
        this.MOVEMENT = MOVEMENT;
    }

    public Vector3 getPosition(){
        return position;
    }

    public TextureRegion getTexture(){

        return shipAnimation.getFrame();
    }
    /*public Texture getTexture(){
        return texture;
    }*/

    public void jump(){
        velocity.y = 300;
        blast.play(0.004f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        blast.dispose();
    }

}
