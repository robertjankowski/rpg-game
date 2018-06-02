package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Stats;

public class Shop implements Screen {

	public Stage stage;
	private Table table;
	private TextButton hpButton;
	private TextButton manaButton;
	private TextButton skillButton;
	private Label hpLabel;
	private Label manaLabel;
	private Label randomSkillLabel;
	private Label title;

	// do background image
	private SpriteBatch batch;
	private Sprite sprite;

	private AssetManager assetManager;

	public Shop(final MapScreen mapScreen) {
		assetManager = new AssetManager();
		assetManager.load("uiskin.json", Skin.class);
		assetManager.finishLoading();
		hpButton = new TextButton("10 GOLD", assetManager.get("uiskin.json", Skin.class));
		manaButton = new TextButton("10 GOLD", assetManager.get("uiskin.json", Skin.class));
		skillButton = new TextButton("50 GOLD", assetManager.get("uiskin.json", Skin.class));
		hpLabel = new Label("HP : ", assetManager.get("uiskin.json", Skin.class));
		manaLabel = new Label("MANA : ", assetManager.get("uiskin.json", Skin.class));
		randomSkillLabel = new Label("RANDOM SKILL : ", assetManager.get("uiskin.json", Skin.class));
		title = new Label("--SHOP--", assetManager.get("uiskin.json", Skin.class));
		stage = new Stage(new ScreenViewport());
		table = new Table();

		hpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (mapScreen.getPlayer().getGold() >= Constants.HP_COST) {
					mapScreen.getPlayer().setGold(mapScreen.getPlayer().getGold() - Constants.HP_COST);
					if (mapScreen.getPlayer().getHp() < 100) {
						mapScreen.getPlayer()
								.setHp(mapScreen.getPlayer().getHp() + ThreadLocalRandom.current().nextInt(20, 30));
					}
					if (mapScreen.getPlayer().getHp() > 100) {
						mapScreen.getPlayer().setHp(Stats.HP_START);
					}
				} else {
					notEnoughMoney();
				}
			}
		});
		manaButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (mapScreen.getPlayer().getGold() >= Constants.HP_COST) {
					mapScreen.getPlayer().setGold(mapScreen.getPlayer().getGold() - Constants.HP_COST);
					if (mapScreen.getPlayer().getMana() < 100) {
						mapScreen.getPlayer()
								.setMana(mapScreen.getPlayer().getMana() + ThreadLocalRandom.current().nextInt(20, 30));
					}
					if (mapScreen.getPlayer().getMana() > 100) {
						mapScreen.getPlayer().setMana(Stats.HP_START);
					}
				} else {
					notEnoughMoney();
				}
			}
		});
		skillButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// change HP_COST to SKILL_COST
				if (mapScreen.getPlayer().getGold() >= Constants.HP_COST) {
					mapScreen.getPlayer().setGold(mapScreen.getPlayer().getGold() - Constants.HP_COST);
					if (ThreadLocalRandom.current().nextInt(0, 2) == 1) {
						mapScreen.getPlayer().setAttackLevel(mapScreen.getPlayer().getAttackLevel() + 1);
					} else {
						mapScreen.getPlayer().setMagicLevel(mapScreen.getPlayer().getMagicLevel() + 1);
					}
				} else {
					notEnoughMoney();
				}
			}
		});

		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());

		title.setFontScale(1.5f);
		title.setPosition(Gdx.graphics.getWidth() / 3 + 130, Gdx.graphics.getHeight() - 80);
		table.padTop(200);
		table.add(hpLabel).pad(50);
		table.add(hpButton).pad(50);
		table.row();
		table.add(manaLabel).pad(50);
		table.add(manaButton).pad(50);
		table.row();
		table.add(randomSkillLabel).pad(50);
		table.add(skillButton).pad(50);
		stage.addActor(title);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE)));
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void notEnoughMoney() {
		final Dialog dialog = new Dialog("You don't have money !", assetManager.get("uiskin.json", Skin.class));
		dialog.show(stage).setPosition(Gdx.graphics.getWidth() / 3, 50);
		Timer.schedule(new Task() {
			@Override
			public void run() {
				dialog.hide();
			}
		}, 2);
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
