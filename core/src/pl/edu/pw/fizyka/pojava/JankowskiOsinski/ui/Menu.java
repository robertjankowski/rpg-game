package pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.RPGgame;

public class Menu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton startButton;
	private TextButton loadButton;
	private TextButton quitButton;
	private TextArea textArea;

	// to background image
	private SpriteBatch batch;
	private Sprite sprite;

	private AssetManager assetManager;
	private RPGgame game;
	private LogMenu logMenu;

	public Menu(RPGgame game) {
		this.game = game;
		logMenu = new LogMenu(game, this);
	}

	@Override
	public void show() {
		assetManager = new AssetManager();
		assetManager.load(Constants.SKIN_NAME, Skin.class);
		assetManager.finishLoading();
		startButton = new TextButton("New game", assetManager.get(Constants.SKIN_NAME, Skin.class));
		loadButton = new TextButton("Load game", assetManager.get(Constants.SKIN_NAME, Skin.class));
		quitButton = new TextButton("Exit", assetManager.get(Constants.SKIN_NAME, Skin.class));
		startButton.setColor(Color.YELLOW);
		loadButton.setColor(Color.RED);
		quitButton.setColor(Color.BLUE);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.loadGame();
			}
		});
		loadButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(logMenu);
			}
		});
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.exit(0);
			}
		});
		stage = new Stage(new ScreenViewport());
		table = new Table();

		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());
		table.padTop(50);
		table.add(startButton).padBottom(50);
		table.row();
		table.add(loadButton).padBottom(50);
		table.row();
		table.add(quitButton);
		table.row();
		table.add(textArea).size(300, 40);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE)));
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderBackgroundImage();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void renderBackgroundImage() {
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
