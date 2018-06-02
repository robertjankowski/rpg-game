package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens;

import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;

public class DamageScreen {

	public Stage stage;
	Label statsLabel;
	AssetManager assetManager;

	public DamageScreen() {
		stage = new Stage();
		assetManager = new AssetManager();
		assetManager.load(Constants.SKIN_NAME, Skin.class);
		assetManager.finishLoading();
		statsLabel = new Label("", assetManager.get(Constants.SKIN_NAME, Skin.class));
		statsLabel.setPosition(500, Gdx.graphics.getHeight() - 500);
		stage.addActor(statsLabel);
	}

	public DamageScreen(Vector2 pos, int hp) {
		stage = new Stage();
		assetManager = new AssetManager();
		assetManager.load(Constants.SKIN_NAME, Skin.class);
		assetManager.finishLoading();
		statsLabel = new Label("" + hp, assetManager.get(Constants.SKIN_NAME, Skin.class));
		statsLabel.setPosition(pos.x, Gdx.graphics.getHeight() - pos.y);
		stage.addActor(statsLabel);
	}

	public void render() {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void showHp(int hp, Vector2 position) {
		statsLabel.setPosition(position.x, position.y);
		statsLabel.setText(String.valueOf(hp));
		Timer obRunTime = new Timer();
		obRunTime.schedule(new TimerTask() {
			@Override
			public void run() {
				statsLabel.setText("");
			}
		}, 500);
	}

	public Label getStatsLabel() {
		return statsLabel;
	}

	public void setStatsLabel(Label statsLabel) {
		this.statsLabel = statsLabel;
	}

}
