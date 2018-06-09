package pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.RPGgame;

public class HelpMenu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton returnButton;
	private AssetManager assetManager;
	private Label infoLabel;
	private Label[] screenInfo;
	private Label attackInfo;

	RPGgame game;
	Menu menu;

	public HelpMenu(RPGgame rpgGame, Menu menu) {
		this.game = rpgGame;
		this.menu = menu;
	}

	@Override
	public void show() {
		assetManager = new AssetManager();
		assetManager.load(Constants.SKIN_NAME, Skin.class);
		assetManager.finishLoading();
		stage = new Stage(new ScreenViewport());
		table = new Table();
		infoLabel = new Label("Keyboard shortcuts", assetManager.get(Constants.SKIN_NAME, Skin.class));
		attackInfo = new Label("Attack monster by clicking it", assetManager.get(Constants.SKIN_NAME, Skin.class));
		screenInfo = new Label[4];
		screenInfo[0] = new Label("'Space' --- shop", assetManager.get(Constants.SKIN_NAME, Skin.class));
		screenInfo[1] = new Label("  'I'   --- stats", assetManager.get(Constants.SKIN_NAME, Skin.class));
		screenInfo[2] = new Label("  'R'   --- attack range", assetManager.get(Constants.SKIN_NAME, Skin.class));
		screenInfo[3] = new Label("  'Q'   --- spell (only for Wizard)",
				assetManager.get(Constants.SKIN_NAME, Skin.class));

		returnButton = new TextButton("Back to menu", assetManager.get(Constants.SKIN_NAME, Skin.class));
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(menu);
			}
		});

		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());
		table.padTop(200);

		table.add(infoLabel).padBottom(100);
		table.row();
		for (Label l : screenInfo) {
			table.row();
			table.add(l).padBottom(50);
		}
		table.row();
		table.add(attackInfo).padBottom(100);

		returnButton.setPosition(10, 20, Align.left);
		stage.addActor(table);
		stage.addActor(returnButton);
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menu.renderBackgroundImage();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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

	}

}
