package com.asteroidavoid.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Asteroid {
    public static final int ASTEROID_WIDTH = 32;

    private static final int FLUCTUATION =130;
    private static final int ASTEROID_GAP =100,LOWEST_OPENING=120;
    private Texture Asteroid;
    private Vector2 posAsteroid;
    private Rectangle bounds;
    private Random rand;
    private Ship ship;
    public Asteroid(float x,float y,Ship ship){
        this.ship=ship;
        Asteroid = new Texture("asteroid.png");
        rand = new Random();
        posAsteroid = new Vector2(x,y);
        bounds = new Rectangle(posAsteroid.x, posAsteroid.y, ASTEROID_WIDTH, ASTEROID_WIDTH);

    }

    public void update(){
        bounds.setPosition(posAsteroid.x, posAsteroid.y);
    }

    public Texture getTopAsteroid() {
        return Asteroid;
    }



    public Vector2 getPosAsteroid() {
        return posAsteroid;
    }



    public void reposition(){

        posAsteroid = new Vector2((ship.getPosition().x +200)+rand.nextInt(100),rand.nextInt(350));


        bounds.setPosition(posAsteroid.x, posAsteroid.y);

    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds) || player.overlaps(bounds);
    }

    public void dispose(){
        Asteroid.dispose();

    }
}
