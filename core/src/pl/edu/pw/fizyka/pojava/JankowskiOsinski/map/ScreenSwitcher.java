package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;

public class ScreenSwitcher extends InputAdapter {

	Game game;
	MapScreen mapScreen;
	StatsScreen statsScreen;
	Shop shop;
	int currentScreen = Constants.MAP_SCREEN;
	Vector2 posCamera;
	Vector2 posPlayer;

	public ScreenSwitcher(Game game, MapScreen screenMap, StatsScreen statsScreen, Shop shop) {
		this.game = game;
		this.mapScreen = screenMap;
		this.statsScreen = statsScreen;
		this.shop = shop;
	}

	// Use 'I' to change screens
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.I) {
			if (currentScreen == Constants.MAP_SCREEN) {
				currentScreen = Constants.STATS_SCREEN;
				posCamera = new Vector2(mapScreen.camera.position.x, mapScreen.camera.position.y);
				posPlayer = new Vector2(mapScreen.getKnight().getPosition().x, mapScreen.getKnight().getPosition().y);
				game.setScreen(statsScreen);
			} else if (currentScreen == Constants.STATS_SCREEN) {
				currentScreen = Constants.MAP_SCREEN;
				game.setScreen(mapScreen);
				mapScreen.camera.position.set(new Vector3(posCamera.x, posCamera.y, 0));
				mapScreen.getKnight().getPosition().set(posPlayer);
			}
		}
		if (keycode == Keys.SPACE) {
			if (currentScreen == Constants.MAP_SCREEN) {
				game.setScreen(shop);
				currentScreen = Constants.SHOP_SCREEN;
			} else if (currentScreen == Constants.SHOP_SCREEN) {
				game.setScreen(mapScreen);
				currentScreen = Constants.MAP_SCREEN;
			}
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// do poprawy, bo na pustyni nie ma botów, dlatego wywala błedy
		if (currentScreen == Constants.MAP_SCREEN) {
			// zle dziala, bo klikając na siebie dodaje punkty do poprawy ! 
			Vector3 worldCoords = new Vector3(screenX, screenY, 0);
			mapScreen.camera.unproject(worldCoords);
			mapScreen.bots.position(0).x = MapScreen.MAP_WIDTH - MapScreen.TILE_SIZE - mapScreen.bots.position(0).x;
			for (int i = 0; i < mapScreen.bots.getMonsterList().size(); i++) {
				if (Math.abs(worldCoords.x - mapScreen.bots.position(i).x) < MapScreen.TILE_SIZE) {
					// zwiększenie attack lvl za kazdym nasicnieciem na dragona
					mapScreen.getKnight().setAttackLevel(mapScreen.getKnight().getAttackLevel() + 1);
				}
			}
			System.out.println(mapScreen.getKnight().getAttackLevel());
		}
		return false;
	}

}
