package pl.edu.pw.fizyka.pojava.JankowskiOsinski.people;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;

public class Wizard extends PersonTemplate {

	public Wizard(OrthographicCamera camera, Vector2 pos, String pathToFile) {
		super(camera, pos, Constants.WIZARD_IMG);
	}

	@Override
	public int attack(Bot bot) {
		return (getMagicLevel() * getExperience()) / bot.getShielding();
	}

}
