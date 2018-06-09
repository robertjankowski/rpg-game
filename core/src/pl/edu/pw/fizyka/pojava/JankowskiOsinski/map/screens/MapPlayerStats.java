package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.PersonTemplate;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui.LogIn;

public class MapPlayerStats {

	public Stage stage;
	Label statsLabel;
	AssetManager assetManager;
	String statsHP;
	String statsMANA;
	String statsGOLD;
	String statsEXP;
	TextButton returnButton;

	public MapPlayerStats(final MapScreen mapScreen) {
		stage = new Stage();
		assetManager = new AssetManager();
		assetManager.load(Constants.SKIN_NAME, Skin.class);
		assetManager.finishLoading();
		statsLabel = new Label("", assetManager.get(Constants.SKIN_NAME, Skin.class));
		statsLabel.setPosition(20, Gdx.graphics.getHeight() - 50);
		returnButton = new TextButton("Exit", assetManager.get(Constants.SKIN_NAME, Skin.class));
		returnButton.setPosition(1100, Gdx.graphics.getHeight() - 50);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Dialog dialog = new Dialog("Warning", assetManager.get(Constants.SKIN_NAME, Skin.class)) {
					@Override
					protected void result(Object object) {
						if (object.equals(true)) {
							try {
								LogIn.savePlayer(mapScreen.getPlayer());
								System.exit(0);
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("because of new game");
							}
							System.out.println("save");
						} else if (object.equals(false)) {
							// not exiting
						}
					}
				};
				dialog.pad(50);
				dialog.text("Do you want to quit the game ?").pad(60);
				dialog.button("Yes", true).pad(40);
				dialog.button("No", false).pad(40);
				dialog.key(Keys.ENTER, true);
				dialog.show(stage);
			}
		});
		stage.addActor(statsLabel);
		stage.addActor(returnButton);
		Gdx.input.setInputProcessor(stage);
	}

	public void show(PersonTemplate person) {
		statsHP = "HP : " + person.getHp();
		statsMANA = "    MANA : " + person.getMana();
		statsGOLD = "    GOLD : " + person.getGold();
		statsEXP = "    EXP : " + person.getExperience();
		statsLabel.setText(statsHP + statsMANA + statsGOLD + statsEXP);
	}

	public void render() {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

}
