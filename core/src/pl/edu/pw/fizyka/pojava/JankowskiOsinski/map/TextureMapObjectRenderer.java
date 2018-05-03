package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * @author robjan Class to render map and objects from Tiled
 */
public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

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
}
