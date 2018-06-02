package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.circle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;

public class ShowRangeScreenStage {

	private Stage stage;
	private ShapeRenderer renderer;
	private ShowRangeActor actor;

	public ShowRangeScreenStage() {
		stage = new Stage();
		renderer = new ShapeRenderer();
		actor = new ShowRangeActor(100, 100, Constants.WIZARD_RANGE);
		stage.addActor(actor);
	}

	public void render(MapScreen mapScreen) {
		renderer.setProjectionMatrix(mapScreen.getCamera().combined);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		renderer.setAutoShapeType(true);
		renderer.begin();
		actor.draw(renderer);
		updateActor(mapScreen.getCamera().position.x, mapScreen.getCamera().position.y, actor, mapScreen);
		renderer.end();
	}

	public void updateActor(float f, float g, ShowRangeActor actor, MapScreen mapScreen) {
		actor.setX(f);
		actor.setY(g);
		actor.radius = mapScreen.getPlayer().getClass().getSimpleName().equals("Wizard") ? Constants.WIZARD_RANGE
				: Constants.KNIGHT_RANGE;
	}

	public ShowRangeActor getActor() {
		return actor;
	}
}
