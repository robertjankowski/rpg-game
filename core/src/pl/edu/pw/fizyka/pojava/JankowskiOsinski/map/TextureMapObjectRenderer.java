package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.utils.UniqueList;

/**
 * @author robjan Class to render map and objects from Tiled
 */
public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

	// not duplicated list of objects to get X,Y position
	UniqueList<TextureMapObject> uniqueMonster = new UniqueList<>();

	public TextureMapObjectRenderer(TiledMap map) {
		super(map);
	}

	public TextureMapObjectRenderer(TiledMap map, Batch batch) {
		super(map, batch);
	}

	public TextureMapObjectRenderer(TiledMap map, float unitScale) {
		super(map, unitScale);
	}

	public TextureMapObjectRenderer(TiledMap map, float unitScale, Batch batch) {
		super(map, unitScale, batch);

	}

	@Override
	public void renderObject(MapObject object) {
		if (object instanceof TextureMapObject) {
			TextureMapObject textureObject = (TextureMapObject) object;
			batch.begin();
			batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY());
			batch.end();
		}
	}

	public void renderMonster(MapObject object) {
		if (object instanceof TextureMapObject) {
			TextureMapObject textureMonster = (TextureMapObject) object;
			uniqueMonster.add(textureMonster);
			batch.begin();
			batch.draw(textureMonster.getTextureRegion(), textureMonster.getX(), textureMonster.getY());
			batch.end();
			// move the monster
			textureMonster.setX(randomMove(-3, 3).x + textureMonster.getX());
			textureMonster.setY(randomMove(-3, 3).y + textureMonster.getY());
			// System.out.println(uniqueMonster.size());
		}
	}

	public Vector2 randomMove(float min, float max) {
		Random random = new Random();
		float randX = min + random.nextFloat() * (max - min);
		float randY = min + random.nextFloat() * (max - min);
		return new Vector2(randX, randY);
	}

	public void setRandomPositionMonsters(UniqueList<TextureMapObject> uniqueMonster) {
		// System.out.println("Size " + uniqueMonster.size());
		uniqueMonster.stream().forEach(e -> {
			e.setX(ThreadLocalRandom.current().nextInt(300, 1700));
			e.setY(ThreadLocalRandom.current().nextInt(300, 1700));
		});
	}

}
