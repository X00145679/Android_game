package com.asteroidavoid.states;





import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuState extends State {

    private Texture background,logo;
    private Skin mySkin;
    private Stage stage;
    private Label outputLabel;
    int row_height = Gdx.graphics.getWidth() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        background = new Texture("bg.png");
        logo = new Texture("logo.png");
        mySkin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        create();

    }
    @Override
    public void handlerInput() {
        /*if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }*/
    }

    public void create () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    // Text Button
    Button button2 = new TextButton("Play",mySkin,"default");
        button2.setSize(col_width*4,row_height);
        button2.setPosition(col_width*2,Gdx.graphics.getHeight()-row_height*15);
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
        logo.dispose();
        System.out.println("menustate disposed");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background,0,0,Game.WIDTH,Game.HEIGHT);
        sb.draw(logo,cam.position.x-(logo.getWidth()/2),cam.position.y,logo.getWidth(),logo.getHeight());
        //sb.draw(background,0,0 );
        sb.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().end();
        stage.draw();


        //sb.draw(playBtn,cam.position.x-playBtn.getWidth()/2,cam.position.y);


    }
}
