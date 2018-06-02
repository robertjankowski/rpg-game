package pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.circle;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShowRangeActor extends Actor {

	float radius = 100;

	public ShowRangeActor(float x, float y, float radius) {
		setX(x);
		setY(y);
		this.radius = radius;
	}

	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.set(ShapeType.Line);
		shapeRenderer.circle(getX(), getY(), this.radius);
	}
}
