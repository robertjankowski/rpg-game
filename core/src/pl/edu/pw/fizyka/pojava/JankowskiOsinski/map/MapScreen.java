package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.MyMusic;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.RPGgame;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.circle.ShowRangeScreenStage;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.DamageScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.MapPlayerStats;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Bot;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Knight;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Person;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.PersonTemplate;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Stats;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Wizard;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui.LogIn;

public class MapScreen implements Screen {

	public static final String TAG = MapScreen.class.getName();
	public static float MAP_HEIGHT;
	public static float MAP_WIDTH;
	public static float TILE_SIZE;

	RPGgame game;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TextureMapObjectRenderer tiledMapRenderer;
	MyMusic music;
	MyMusic attackMusic;
	public MapPlayerStats mapPlayerStats;
	DamageScreen damageScreen;
	ShowRangeScreenStage rangeScreenStage;

	private int[] layerBottom = { 0 };
	private int[] layerTop = { 3 };

	// I want to choose which character render
	public PersonTemplate player;
	public Map<String, Bot> mapBots;
	// only to get attack point
	private Bot bot;
	private List<Vector2> posOfMonsters = new ArrayList<>();

	boolean isFirstInit = true;
	boolean isZooming = false;

	private int counter = 0;
	private int rangeCounter = 0;
	private int musicCounter = 0;

	public MapScreen(RPGgame game) {
		this.game = game;
		mapPlayerStats = new MapPlayerStats(this);
		damageScreen = new DamageScreen();
		rangeScreenStage = new ShowRangeScreenStage();
	}

	@Override
	public void show() {
		// not to restart player stats
		if (isFirstInit) {
			init(Constants.startPositionX, Constants.startPositionY, Constants.mapName, Constants.FORREST_MUSIC);
			initPlayer(Constants.startPositionX, Constants.startPositionY);
			isFirstInit = false;
		}
		mapPlayerStats.show(player);
	}

	private void initPlayer(float posX, float posY) {
		player.loadStartingStart();
	}

	// initialize variable
	private void init(float posX, float posY, String map, String musicName) {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		music = new MyMusic(musicName);
		music.startPlay();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		tiledMap = new TmxMapLoader().load(map);
		tiledMapRenderer = new TextureMapObjectRenderer(tiledMap);

		// here I have to get from database which type to render
		switch (LogIn.role) {
		case "knight":
			player = new Knight(camera, new Vector2(posX, posY), Constants.KNIGHT_IMG);
			break;
		case "sorcerer":
			player = new Wizard(camera, new Vector2(posX, posY), Constants.WIZARD_IMG);
			break;
		default:
			System.out.println("Error, couldn't find vacation");
			break;
		}

		// attack music depends on role
		attackMusic = new MyMusic(player.getClass().getSimpleName().equals("Wizard") ? Constants.WIZZARD_ATTACK_MUSIC
				: Constants.KNIGHT_ATTACK_MUSIC);
		attackMusic.setLevel(0.7f);

		// System.out.println(player.getClass().getSimpleName());
		mapBots = renderMonster(tiledMapRenderer, Constants.BOTS_NAMES);
		bot = new Bot(tiledMapRenderer, Constants.BOTS_NAMES[0]);

		// to get map size
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMapRenderer.getMap().getLayers().get(0);
		MAP_WIDTH = layer.getTileWidth() * layer.getWidth();
		MAP_HEIGHT = layer.getTileHeight() * layer.getHeight();
		TILE_SIZE = layer.getTileHeight();

		camera.zoom = Constants.ZOOM;
		camera.position.set(posX, posY, 0);
		camera.update();

	}

	@Override
	public void render(float delta) {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(layerBottom);
		mapBots.forEach((k, v) -> v.update(Gdx.graphics.getDeltaTime(), this));
		player.update(delta, this);
		tiledMapRenderer.render(layerTop);

		// Reseting map / new map
		if (isNextMap(player)) {
			long endTime = TimeUtils.nanoTime();
			isZooming = false;
			while (!isZooming) {
				if (TimeUtils.timeSinceNanos(endTime) > 100000000) {
					music.stopPlay();
					// save stats before changing the map !
					int[] stats = { player.getHp(), player.getMana(), player.getGold(), player.getAttackLevel(),
							player.getMagicLevel(), player.getExperience() };
					player.getWalkMusic().stopPlay();
					attackMusic.stopPlay();
					init(Constants.endPositionX, Constants.endPositionY, Constants.newMap, Constants.DESSERT_MUSIC);
					player.saveStats(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
					isZooming = true;
					endTime = TimeUtils.nanoTime();
				}
			}
		}

		mapPlayerStats.render();
		damageScreen.render();
		if (Constants.isCircle) {
			++rangeCounter;
			rangeScreenStage.render(this);
			if (rangeCounter == 60) {
				Constants.isCircle = false;
				rangeCounter = 0;
			}
		}

		// attack player !
		addMonstersToList(tiledMapRenderer, posOfMonsters);
		if (isPlayerNearMonster(posOfMonsters, (int) player.getPosition().x, (int) player.getPosition().y,
				Constants.MONSTER_RANGE)) {
			++counter;
			// decrease value to increase speed
			if (counter == 10) {
				attackPlayer(player, bot);
				counter = 0;
			}
		}
		posOfMonsters.clear();

		// restart player save old stats and decrease exp lvl
		if (player.getHp() <= 0) {
			// after player dead all monster are alive again
			music.stopPlay();
			player.getWalkMusic().stopPlay();
			attackMusic.stopPlay();
			int expAfterDead = (int) Math.floor(player.getExperience() / 2);
			System.out.println(expAfterDead);
			int[] stats = { Stats.HP_START, player.getMana(), player.getGold(), player.getAttackLevel(),
					player.getMagicLevel(), expAfterDead };
			init(Constants.startPositionX, Constants.startPositionY, Constants.mapName, Constants.FORREST_MUSIC);
			initPlayer(Constants.startPositionX, Constants.startPositionY);
			player.saveStats(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
			mapPlayerStats.show(player);
		}

		// loop attack music
		if (Constants.isClickedMonster) {
			++musicCounter;
			attackMusic.startPlay();
			if (musicCounter == 40) {
				Constants.isClickedMonster = false;
				attackMusic.stopPlay();
				musicCounter = 0;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.update();
	}

	private boolean isNextMap(Person person) {
		if (((Math.pow(person.getPosition().x - 654, 2)) + (Math.pow(person.getPosition().y - 1122, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 654, 2)) + (Math.pow(person.getPosition().y - 1172, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 689, 2)) + (Math.pow(person.getPosition().y - 1122, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 689, 2)) + (Math.pow(person.getPosition().y - 1172, 2))) < 60) {
			return true;
		}
		return false;
	}

	public Map<String, Bot> renderMonster(TextureMapObjectRenderer tiledMapRenderer, String[] names) {
		Map<String, Bot> mapBots = new HashMap<>();
		for (int i = 0; i < names.length; i++) {
			mapBots.put(names[i], new Bot(tiledMapRenderer, names[i]));
			System.out.println(names[i]);
		}
		return mapBots;
	}

	private void addMonstersToList(TextureMapObjectRenderer mapObjectRenderer, List<Vector2> posOfMonsters) {
		// once I get this error, but I don't know what happened
		try {
			for (int i = 0; i < mapBots.size(); i++) {
				posOfMonsters.add(new Vector2(mapObjectRenderer.getUniqueMonster().get(i).getX(),
						mapObjectRenderer.getUniqueMonster().get(i).getY()));
			}
		} catch (IndexOutOfBoundsException e) {
			// e.printStackTrace();
		}
	}

	private void attackPlayer(PersonTemplate player, Bot bot) {
		player.setHp(player.getHp() - bot.getAttack());
		mapPlayerStats.show(player);
	}

	private boolean isPlayerNearMonster(List<Vector2> monstersPos, int playerX, int playerY, int radius) {
		for (Vector2 monster : monstersPos) {
			int range = (int) Math.sqrt(Math.pow((monster.x - playerX), 2) + Math.pow((monster.y - playerY), 2));
			if (range <= radius) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void dispose() {
		tiledMap.dispose();
		tiledMapRenderer.dispose();
		player.dispose();
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

	public OrthogonalTiledMapRenderer getTiledMapRenderer() {
		return tiledMapRenderer;
	}

	public void setTiledMapRenderer(TextureMapObjectRenderer tiledMapRenderer) {
		this.tiledMapRenderer = tiledMapRenderer;
	}

	public PersonTemplate getPlayer() {
		return player;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
