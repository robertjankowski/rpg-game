package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;

public class StatsScreen implements Screen {
	public static final String TAG = StatsScreen.class.getName();

	private Stage stage;
	private Table table;
	private Label statsLabel;
	private Label expLabel;
	private Label hpLabel;
	private Label manaLabel;
	private Label attackLabel;
	private Label magicLabel;
	private Label goldLabel;
	private AssetManager assetManager;
	MapScreen mapScreen;
	private SpriteBatch batch;
	private Sprite sprite;

	public StatsScreen(MapScreen mapScreen) {
		this.mapScreen = mapScreen;
	}

	@Override
	public void show() {
		assetManager = new AssetManager();
		assetManager.load("uiskin.json", Skin.class);
		assetManager.finishLoading();
		statsLabel = new Label("--Statistics--", assetManager.get(Constants.SKIN_NAME, Skin.class));
		expLabel = new Label("EXPERIENCE: " + mapScreen.getPlayer().getExperience(),
				assetManager.get(Constants.SKIN_NAME, Skin.class));
		hpLabel = new Label("HP: " + mapScreen.getPlayer().getHp(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		goldLabel = new Label("GOLD: " + mapScreen.getPlayer().getGold(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		manaLabel = new Label("MANA: " + mapScreen.getPlayer().getMana(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		attackLabel = new Label("ATTACK LEVEL: " + mapScreen.getPlayer().getAttackLevel(),
				assetManager.get(Constants.SKIN_NAME, Skin.class));
		magicLabel = new Label("MAGIC LEVEL: " + mapScreen.getPlayer().getMagicLevel(),
				assetManager.get(Constants.SKIN_NAME, Skin.class));
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());

		statsLabel.setFontScale(1.75f);
		statsLabel.setPosition(Gdx.graphics.getWidth() / 3 + 70, Gdx.graphics.getHeight() - 80);
		table.padTop(200);
		table.row();
		table.add(expLabel).pad(30);
		table.row();
		table.add(hpLabel).pad(30);
		table.row();
		table.add(goldLabel).pad(30);
		table.row();
		table.add(manaLabel).pad(30);
		table.row();
		table.add(attackLabel).pad(30);
		table.row();
		table.add(magicLabel).pad(30);
		table.row();
		stage.addActor(statsLabel);
		stage.addActor(table);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE)));
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		batch.end();
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
