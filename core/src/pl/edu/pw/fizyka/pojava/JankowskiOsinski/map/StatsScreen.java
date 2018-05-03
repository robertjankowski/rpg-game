package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;

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
		expLabel = new Label("EXPERIENCE: " + mapScreen.getKnight().getExperience(),
				assetManager.get(Constants.SKIN_NAME, Skin.class));
		hpLabel = new Label("HP: " + mapScreen.getKnight().getHp(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		goldLabel = new Label("GOLD: " + mapScreen.getKnight().getGold(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		manaLabel = new Label("MANA: " + mapScreen.getKnight().getMana(), assetManager.get(Constants.SKIN_NAME, Skin.class));
		attackLabel = new Label("ATTACK LEVEL: " + mapScreen.getKnight().getAttackLevel(),
				assetManager.get(Constants.SKIN_NAME, Skin.class));
		magicLabel = new Label("MAGIC LEVEL: " + mapScreen.getKnight().getMagicLevel(),
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