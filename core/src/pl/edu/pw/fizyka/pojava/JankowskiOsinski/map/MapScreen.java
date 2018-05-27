package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import java.util.HashMap;
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
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Bot;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Knight;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Person;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.PersonTemplate;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Wizard;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui.LogIn;

public class MapScreen implements Screen {
	public static final String TAG = MapScreen.class.getName();
	private boolean isFirstMap = true;

	public static float MAP_HEIGHT;
	public static float MAP_WIDTH;
	public static float TILE_SIZE;

	TiledMap tiledMap;
	OrthographicCamera camera;
	TextureMapObjectRenderer tiledMapRenderer;
	MyMusic music;
	public MapPlayerStats mapPlayerStats;

	private int[] layerBottom = { 0 };
	private int[] layerTop = { 3 };

	// I want to choose which character render
	public PersonTemplate player;
	public Map<String, Bot> mapBots;

	boolean isFirstInit = true;
	boolean isZooming = false;

	public MapScreen() {
		mapPlayerStats = new MapPlayerStats(this);
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

		System.out.println(player.getClass().getName());
		// because, there aren't any bots in next map
		try {
			// render monsters
			mapBots = renderMonster(tiledMapRenderer, Constants.BOTS_NAMES);
		} catch (Exception ex) {
		}

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
		if (isFirstMap) {
			tiledMapRenderer.render(layerBottom);
			mapBots.forEach((k, v) -> v.update(Gdx.graphics.getDeltaTime()));
			player.update(delta, this);
			tiledMapRenderer.render(layerTop);
		}

		// teleport
		if (!isFirstMap) {
			// jeszcze jakies boty
			tiledMapRenderer.render(layerBottom);
			player.update(delta, this);
		}
		// Zoom out effect and reseting map
		if (isNextMap(player)) {
			long endTime = TimeUtils.nanoTime();
			isZooming = false;
			while (!isZooming) {
				if (TimeUtils.timeSinceNanos(endTime) > 100000000) {
					isFirstMap = false;
					music.stopPlay();
					// save stats before changing the map !
					int[] stats = { player.getHp(), player.getMana(), player.getGold(), player.getAttackLevel(),
							player.getMagicLevel(), player.getExperience() };
					player.getWalkMusic().stopPlay();
					init(Constants.endPositionX, Constants.endPositionY, Constants.nextMapName,
							Constants.DESSERT_MUSIC);
					player.saveStats(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
					isZooming = true;
					endTime = TimeUtils.nanoTime();
				}
			}
		}
		mapPlayerStats.render();
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

	public Person getPlayer() {
		return player;
	}

}
