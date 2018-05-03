package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.MyMusic;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Bot;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Knight;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Person;

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

	private Knight knight;
	public Bot bots;

	boolean isFirstInit = true;
	boolean isZooming = false;

	public MapScreen() {
		mapPlayerStats = new MapPlayerStats(this);
	}

	@Override
	public void show() {
		// aby nie resetowały się statystyki bohatera
		if (isFirstInit) {
			init(Constants.startPositionX, Constants.startPositionY, Constants.mapName, Constants.FORREST_MUSIC);
			initPlayer(Constants.startPositionX, Constants.startPositionY);
			isFirstInit = false;
		}
		// knight.isCollideWithSecondLayer(tiledMapRenderer);
		mapPlayerStats.show(knight);
	}

	private void initPlayer(float posX, float posY) {
		knight.loadStartingStart();
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
		knight = new Knight(camera, new Vector2(posX, posY));
		// tak zrobilem, bo nie ma botow na nowej mapie !
		try {
			bots = new Bot(tiledMapRenderer);
		} catch (Exception ex) {
		}
		// żeby znaleźć rozmiar mapy
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
			bots.update(delta);
			knight.update(delta, this);
			tiledMapRenderer.render(layerTop);
		}
		// tu teleport do innej mapy !
		if (!isFirstMap) {
			// jeszcze jakies boty
			tiledMapRenderer.render(layerBottom);
			knight.update(delta, this);
		}
		// Zoom out effect and reseting map
		if (isEndOfGame(knight, getTiledMapRenderer())) {
			long endTime = TimeUtils.nanoTime();
			isZooming = false;
			while (!isZooming) {
				if (TimeUtils.timeSinceNanos(endTime) > 100000000) {
					isFirstMap = false;
					music.stopPlay();
					// save stats before changing the map !
					int[] stats = { knight.getHp(), knight.getMana(), knight.getGold(), knight.getAttackLevel(),
							knight.getMagicLevel(), knight.getExperience() };
					knight.getWalkMusic().stopPlay();
					init(Constants.endPositionX, Constants.endPositionY, Constants.nextMapName,
							Constants.DESSERT_MUSIC);
					knight.saveStats(stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
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

	// check player position on the map
	public boolean isEndOfGame(Person person, OrthogonalTiledMapRenderer mapRenderer) {
		// get first layer and calculate center of the map
		TiledMapTileLayer layer = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get(0);
		float width = layer.getTileWidth() * layer.getWidth();
		float height = layer.getTileHeight() * layer.getHeight();
		// is in "circle"
		if (person.getPosition().dst(width / 2, height / 2) < 10) {
			return true;
		}

		// pozycja posągu (650,1140) + 32
		if (((Math.pow(person.getPosition().x - 650, 2)) + (Math.pow(person.getPosition().y - 1140, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 620, 2)) + (Math.pow(person.getPosition().y - 1172, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 620, 2)) + (Math.pow(person.getPosition().y - 1140, 2))) < 60
				|| ((Math.pow(person.getPosition().x - 650, 2)) + (Math.pow(person.getPosition().y - 1172, 2))) < 60) {
			camera.zoom += 0.3;
			return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		tiledMap.dispose();
		tiledMapRenderer.dispose();
		knight.dispose();
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

	public Knight getKnight() {
		return knight;
	}

	public void setKnight(Knight knight) {
		this.knight = knight;
	}
}
