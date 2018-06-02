package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.Shop;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.StatsScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Wizard;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.utils.UniqueList;

public class ScreenSwitcher extends InputAdapter {

	Game game;
	MapScreen mapScreen;
	StatsScreen statsScreen;
	Shop shop;
	int currentScreen = Constants.MAP_SCREEN;
	Vector2 posCamera;
	Vector2 posPlayer;
	List<Vector2> posOfMonsters = new ArrayList<>();
	public static boolean isClicked = false;

	public ScreenSwitcher(Game game, MapScreen screenMap, StatsScreen statsScreen, Shop shop) {
		this.game = game;
		this.mapScreen = screenMap;
		this.statsScreen = statsScreen;
		this.shop = shop;
	}

	// Use 'I' to change screens
	// Use 'Space' to go to the shop
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.I) {
			if (currentScreen == Constants.MAP_SCREEN) {
				currentScreen = Constants.STATS_SCREEN;
				posCamera = new Vector2(mapScreen.camera.position.x, mapScreen.camera.position.y);
				posPlayer = new Vector2(mapScreen.getPlayer().getPosition().x, mapScreen.getPlayer().getPosition().y);
				game.setScreen(statsScreen);
			} else if (currentScreen == Constants.STATS_SCREEN) {
				currentScreen = Constants.MAP_SCREEN;
				game.setScreen(mapScreen);
				mapScreen.camera.position.set(new Vector3(posCamera.x, posCamera.y, 0));
				mapScreen.getPlayer().getPosition().set(posPlayer);
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
		// show range circle
		if (keycode == Keys.R) {
			if (!Constants.isCircle) {
				Constants.isCircle = true;
			}
			// System.out.println(Constants.isCircle);
		}
		return true;
	}

	// check if player clicked the monster
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		try {
			isClicked = true;

			if (currentScreen == Constants.MAP_SCREEN) {

				addMonstersByPosition(mapScreen, posOfMonsters);
				// mapScreen.mapBots.forEach((k, v) -> System.out.println(k));

				// translate touch coords to map coords
				Vector2 cameraCoords = new Vector2(screenX, Gdx.graphics.getHeight() - 1 - screenY);

				// get player coords
				int playerX = (int) mapScreen.player.getPosition().x;
				int playerY = (int) mapScreen.player.getPosition().y;

				// get map coords
				Vector3 tempCoord = new Vector3(screenX, screenY, 0);
				Vector3 coords = mapScreen.camera.unproject(tempCoord);
				screenX = (int) coords.x;
				screenY = (int) coords.y;

				// change range based on the class
				Object[] results = isMonsterClicked(screenX, screenY, posOfMonsters, playerX, playerY,
						(mapScreen.player instanceof Wizard) ? Constants.WIZARD_RANGE : Constants.KNIGHT_RANGE,
						mapScreen.tiledMapRenderer.getUniqueMonster());

				Constants.monsterCurrentAttack = (int) results[2];

				posOfMonsters.clear();

				if ((boolean) results[0]) {

					int hpBot = mapScreen.mapBots.get((String) results[1]).getHp();

					if (hpBot <= 0) {
						mapScreen.player
								.setGold(mapScreen.player.getGold() + ThreadLocalRandom.current().nextInt(0, 30));
						mapScreen.player.setExperience(mapScreen.player.getExperience()
								+ mapScreen.mapBots.get((String) results[1]).getShielding());
						mapScreen.mapBots.remove((String) results[1]);
						mapScreen.tiledMapRenderer.getUniqueMonster().remove((int) results[2]);
						mapScreen.mapPlayerStats.show(mapScreen.player);
					} else {

						int damage = mapScreen.player.attack(mapScreen.mapBots.get((String) results[1]));

						mapScreen.mapBots.get((String) results[1]).setHp(hpBot - damage);

						// if clicked bot show -hp above head
						if (button == Input.Buttons.LEFT) {
							Constants.isClickedMonster = true;
							mapScreen.damageScreen.showHp(hpBot, cameraCoords);
						}
						// System.out.println(damage);
						// System.out.println((String) results[1]);
					}
					if (mapScreen.mapBots.size() <= 0) {
						restartMonsters(mapScreen);
						mapScreen.mapBots.forEach((k, v) -> v.increaseStatsAfterDead(30, 5, 2));
					}
				}
				System.out
						.println("Screen coord translated to world coordinates: " + "X: " + screenX + " Y: " + screenY);
			}
		} catch (Exception e) {
			System.out.println("There are't any bots on this map");
			// e.printStackTrace();
		}
		return false;
	}

	public void addMonstersByPosition(MapScreen mapScreen, List<Vector2> posOfMonsters) {
		for (int i = 0; i < mapScreen.mapBots.size(); i++) {
			posOfMonsters.add(new Vector2(mapScreen.tiledMapRenderer.getUniqueMonster().get(i).getX(),
					mapScreen.tiledMapRenderer.getUniqueMonster().get(i).getY()));
		}
	}

	/**
	 * I do that because I need an interval to load textures
	 * 
	 * @param mapScreen
	 */
	private void restartMonsters(MapScreen mapScreen) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				mapScreen.mapBots = mapScreen.renderMonster(mapScreen.tiledMapRenderer, Constants.BOTS_NAMES);
			}
		}, 5000);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				mapScreen.tiledMapRenderer.setRandomPositionMonsters(mapScreen.tiledMapRenderer.getUniqueMonster());
			}
		}, 5100);
	}

	/**
	 * 
	 * @param clickX
	 * @param clickY
	 * @param monstersPos
	 * @param playerX
	 * @param playerY
	 * @param radius
	 * @param monster
	 * @return { (boolean) if player clicked monster, name of monster, index of the
	 *         monster }
	 */
	private Object[] isMonsterClicked(int clickX, int clickY, List<Vector2> monstersPos, int playerX, int playerY,
			int radius, UniqueList<TextureMapObject> monster) {
		for (int i = 0; i < monstersPos.size(); i++) {
			if (isEntityClicked(clickX, clickY, (int) monstersPos.get(i).x, (int) monstersPos.get(i).y)) {
				int range = (int) Math.sqrt(
						Math.pow((monstersPos.get(i).x - playerX), 2) + Math.pow((monstersPos.get(i).y - playerY), 2));
				if (range <= radius) {
					// here I get name of the clicked monster
					// System.out.println(monster.get(i).getName());
					System.out.println(i);
					return new Object[] { true, monster.get(i).getName(), i };
				}
			}
		}
		return new Object[] { false, "", -999 };
	}

	/**
	 * @param clickX
	 * @param clickY
	 * @param entityX
	 * @param entityY
	 * @return true when entity is clicked
	 */
	private boolean isEntityClicked(int clickX, int clickY, int entityX, int entityY) {
		if (clickX >= entityX && clickX <= entityX + Constants.TILE_SIZE) {
			if (clickY >= entityY && clickY <= entityY + Constants.TILE_SIZE) {
				return true;
			}
		}
		return false;
	}

	public List<Vector2> getPosOfMonsters() {
		return posOfMonsters;
	}
}
