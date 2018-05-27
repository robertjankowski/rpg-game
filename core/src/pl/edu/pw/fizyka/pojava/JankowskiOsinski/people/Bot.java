package pl.edu.pw.fizyka.pojava.JankowskiOsinski.people;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.TextureMapObjectRenderer;

public class Bot {

	TextureMapObjectRenderer tiledMapRenderer;
	MapLayer monsterLayer;
	MapObjects monsterObjects;
	MapObject monster;
	private int hp = 10;
	private int shielding = 1;

	public Bot(TextureMapObjectRenderer tiledMapRenderer, String name) {
		this.tiledMapRenderer = tiledMapRenderer;
		try {
			monsterLayer = tiledMapRenderer.getMap().getLayers().get("monster");
			monsterObjects = monsterLayer.getObjects();
			monster = monsterObjects.get(name);
		} catch (Exception ex) {
		}
	}

	public void update(float delta) {
		tiledMapRenderer.renderMonster(monster);
	}

	// getting position of the monster ??? wrong way
	public Vector2 position() {
		float x = (float) monster.getProperties().get("x");
		float y = (float) monster.getProperties().get("y");
		System.out.println(x + " , " + y);
		return new Vector2();
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getShielding() {
		return shielding;
	}

	public void setShielding(int shielding) {
		this.shielding = shielding;
	}

}
