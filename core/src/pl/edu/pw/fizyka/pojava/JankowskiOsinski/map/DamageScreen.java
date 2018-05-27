package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

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

}
