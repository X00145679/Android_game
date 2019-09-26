package com.asteroidavoid.states;


import com.asteroidavoid.sprites.Asteroid;
import com.asteroidavoid.sprites.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Random;


public class PlayState extends State {

    private static final int ASTEROID_COUNT = 5;
    private int score=0;
    private Ship ship;
    private Texture bg;
    private BitmapFont font;
    private Music music;


    private Array<Asteroid> asteroids;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new BitmapFont(Gdx.files.internal("game.fnt"),Gdx.files.internal("game.png"),false);
        ship = new Ship(50,200,gsm,this);
        cam.setToOrtho(false, Game.WIDTH/2,Game.HEIGHT/2);
        bg = new Texture("bg.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

        asteroids = new Array<Asteroid>();
        for(int i =0;i<ASTEROID_COUNT;i++){
            Random rand = new Random();
            asteroids.add(new Asteroid(100+(i*100),100,ship));
            asteroids.get(i).reposition();
        }
    }

    @Override
    protected void handlerInput() {
        if(ship.getPosition().y<Game.HEIGHT/2-45){

            if(Gdx.input.justTouched()){
                ship.jump();

            }

        }
        //System.out.println(ship.getPosition().x+","+ship.getPosition().y);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean checkCollisions(){
        for(int i=0;i<ASTEROID_COUNT;i++){
            if(asteroids.get(i).collides(ship.getBounds()))
                return true;
        }return false;
    }

    @Override
    public void update(float dt) {
        handlerInput();
        checkScore();
        if(checkCollisions())
            gsm.set(new DeadState(gsm,score));
        for(int i=0;i>= asteroids.size;i++){
            asteroids.get(i).update();
        }
        ship.update(dt);
        asteroidLoop();
        cam.position.x = ship.getPosition().x +80;


        cam.update();
    }

    public void checkScore(){
        if(score>500){
            ship.setMOVEMENT(300);
        }
        else if(score>300){
            ship.setMOVEMENT(250);
        }
        else if(score>200){
            ship.setMOVEMENT(200);
        }
        else if(score > 100){
            ship.setMOVEMENT(175);
        }
        else if(score > 20){
            ship.setMOVEMENT(140);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        ship.dispose();
        font.dispose();
        music.dispose();
        for(Asteroid asteroid : asteroids)
            asteroid.dispose();
        System.out.println("playstate disposed");
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg,ship.getPosition().x-100,0,Game.HEIGHT,Game.WIDTH);
        font.setUseIntegerPositions(false);

        font.draw(sb,"Score: "+score ,cam.position.x-100,cam.viewportHeight);
        sb.draw(ship.getTexture(),ship.getPosition().x,ship.getPosition().y,32,32);

        for(int i = 0; i<ASTEROID_COUNT;i++){
            sb.draw(asteroids.get(i).getTopAsteroid(),asteroids.get(i).getPosAsteroid().x,asteroids.get(i).getPosAsteroid().y,50,50);

        }
        font.draw(sb,"Tap screen to fly!", Game.WIDTH/3,Game.HEIGHT/4);


        sb.end();
    }

    public void asteroidLoop(){
        for(int i = 0;i<ASTEROID_COUNT;i++){
            if(asteroids.get(i).getPosAsteroid().x+80< ship.getPosition().x){
                asteroids.get(i).reposition();
                score++;
                /*System.out.println("updated asteroid");
                System.out.println(asteroids.get(i).getPosAsteroid().x+","+asteroids.get(i).getPosAsteroid().y);
            */}
        }
    }


}
