package pl.edu.pw.fizyka.pojava.JankowskiOsinski.people;

import java.util.Random;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.TextureMapObjectRenderer;

public class Bot {

	TextureMapObjectRenderer tiledMapRenderer;
	MapLayer monsterLayer;
	MapObjects monsterObjects;
	MapObject monster;
	private int hp = 10;
	private int shielding = 1;
	private int attack = 2;
	int counter = 0;

	public Bot(TextureMapObjectRenderer tiledMapRenderer, String name) {
		this.tiledMapRenderer = tiledMapRenderer;
		try {
			monsterLayer = tiledMapRenderer.getMap().getLayers().get("monster");
			monsterObjects = monsterLayer.getObjects();
			monster = monsterObjects.get(name);
		} catch (Exception ex) {
		}
	}

	public void update(float delta, MapScreen mapScreen) {
		tiledMapRenderer.renderMonster(monster);
		++counter;
		if (counter == 3) {
			tiledMapRenderer.followPlayer(tiledMapRenderer.getUniqueMonster(), Constants.monsterCurrentAttack,
					mapScreen.getPlayer());
			tiledMapRenderer.getUniqueMonster().stream().forEach(e -> {
				Random random = new Random();
				e.setX(tiledMapRenderer.randomMove(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1).x
						+ e.getX());
				e.setY(tiledMapRenderer.randomMove(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1).y
						+ e.getY());
			});
			counter = 0;
		}
	}

	public void increaseStatsAfterDead(int hp, int attack, int shielding) {
		this.hp += hp;
		this.attack += attack;
		this.shielding += shielding;
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

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

}
