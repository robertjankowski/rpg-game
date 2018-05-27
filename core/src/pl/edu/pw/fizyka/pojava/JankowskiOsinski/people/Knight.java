package pl.edu.pw.fizyka.pojava.JankowskiOsinski.people;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Knight extends PersonTemplate {

	public Knight(OrthographicCamera camera, Vector2 pos, String pathToFile) {
		super(camera, pos, pathToFile);
	}

	@Override
	public int attack(Bot bot) {
		return (getAttackLevel() * getExperience()) / bot.getShielding();
	}

}
