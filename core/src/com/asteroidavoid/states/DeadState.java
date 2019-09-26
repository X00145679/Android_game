package com.asteroidavoid.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DeadState extends State {

        private Texture background;
        private Skin mySkin;
        private Stage stage;
        private Label outputLabel;
        private BitmapFont font;
        private Preferences prefs;
        private Music music;
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        int score,highScore;


        public DeadState(GameStateManager gsm,int score) {
            super(gsm);
            this.score=score;
            cam.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
            font = new BitmapFont(Gdx.files.internal("game.fnt"),Gdx.files.internal("game.png"),false);
            background = new Texture("bg.png");
            mySkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));

            prefs = Gdx.app.getPreferences("MyPreferences");
            create();

            highScore(score);
            System.out.println(prefs.getInteger("highscore"));

        }
        public void highScore(int score){
            highScore= prefs.getInteger("highscore");
            if(score>=highScore){
                prefs.putInteger("highscore",score);
                highScore = score;

            }
            prefs.flush();


        }

        @Override
        public void handlerInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }*/
        }

        public void create () {

            music = Gdx.audio.newMusic(Gdx.files.internal("gameover.wav"));
            music.setLooping(false);
            music.setVolume(0.7f);
            music.play();


            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
            // Text Button
            Button button2 = new TextButton("Play Again?",mySkin,"default");
            button2.setSize(col_width*4,row_height);
            button2.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*10);
            button2.setTransform(true);
            button2.scaleBy(1.0f);
            button2.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    gsm.set(new PlayState(gsm));
                    return true;
                }
            });
            stage.addActor(button2);

        }

        @Override
        public void update(float dt) {
            handlerInput();
        }

        @Override
        public void dispose() {
            background.dispose();
            stage.dispose();
            music.dispose();
            mySkin.dispose();
            System.out.println("menustate disposed");
        }

        @Override
        public void render(SpriteBatch sb) {
            sb.begin();
            sb.setProjectionMatrix(cam.combined);
            sb.draw(background,0,0,Game.WIDTH,Game.HEIGHT);
            font.setUseIntegerPositions(false);

            font.draw(sb,"High Score: "+highScore ,cam.position.x-50,cam.viewportHeight-100);

            font.draw(sb,"Score: "+score ,cam.position.x-50,cam.viewportHeight-200);
            sb.end();
            //sb.draw(background,0,0 );
            stage.act(Gdx.graphics.getDeltaTime());
            stage.getBatch().begin();


            stage.getBatch().end();
            stage.draw();


            //sb.draw(playBtn,cam.position.x-playBtn.getWidth()/2,cam.position.y);


        }
    }


