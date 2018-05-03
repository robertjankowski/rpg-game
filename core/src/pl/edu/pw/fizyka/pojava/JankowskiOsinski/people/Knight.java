package pl.edu.pw.fizyka.pojava.JankowskiOsinski.people;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.MyMusic;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;

public class Knight extends Person {
	// nazwa pliku z rycerzem
	public static final String toFilePath = "person2.png";

	// To animate person in different position
	Animation<TextureRegion> walkAnimation;
	Texture walkSheet;
	float stateTime;
	TextureRegion[][] textureRegion;
	SpriteBatch walkBatch;
	public OrthographicCamera camera;
	private MyMusic walkMusic;

	// Podzial na cztery kierunki chodzenia
	TextureRegion[] upWalk;
	TextureRegion[] downWalk;
	TextureRegion[] leftWalk;
	TextureRegion[] rightWalk;
	Animation<TextureRegion> walkAnimationUp;
	Animation<TextureRegion> walkAnimationDown;
	Animation<TextureRegion> walkAnimationRight;
	Animation<TextureRegion> walkAnimationLeft;
	TextureRegion currentFrame;

	public Knight(OrthographicCamera camera, Vector2 pos) {

		super(toFilePath, pos);
		this.camera = camera;
		walkMusic = new MyMusic(Constants.WALK_MUSIC);
		// animation
		walkSheet = new Texture(Gdx.files.internal(toFilePath));
		walkSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		textureRegion = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight() / 4);

		upWalk = new TextureRegion[4];
		downWalk = new TextureRegion[4];
		leftWalk = new TextureRegion[4];
		rightWalk = new TextureRegion[4];

		for (int i = 0; i < 4; i++) {
			upWalk[i] = textureRegion[3][i];
			rightWalk[i] = textureRegion[2][i];
			leftWalk[i] = textureRegion[1][i];
			downWalk[i] = textureRegion[0][i];
		}

		walkAnimationUp = new Animation<TextureRegion>(0.1f, upWalk);
		walkAnimationDown = new Animation<TextureRegion>(0.1f, downWalk);
		walkAnimationLeft = new Animation<TextureRegion>(0.1f, leftWalk);
		walkAnimationRight = new Animation<TextureRegion>(0.1f, rightWalk);

		walkBatch = new SpriteBatch();

	}

	public void update(float delta, MapScreen mapScreen) {
		walkBatch.setProjectionMatrix(camera.combined);
		camera.update();
		walkBatch.begin();
		stateTime += delta;
		walkMusic.setLevel(Constants.WALK_MUSIC_VOLUME);
		if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
				&& !Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)
				&& !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.W)
				&& !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
			walkMusic.stopPlay();
			walkBatch.draw(downWalk[0], position.x, position.y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!(camera.position.x < 270)) {
				camera.translate(-WALK_SPEED, 0);
				position.x -= WALK_SPEED;
				currentFrame = walkAnimationLeft.getKeyFrame(stateTime, true);
				walkBatch.draw(currentFrame, position.x, position.y);
				walkMusic.startPlay();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!(camera.position.x > 1771)) {
				camera.translate(WALK_SPEED, 0);
				position.x += WALK_SPEED;
				currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);
				walkBatch.draw(currentFrame, position.x, position.y);
				walkMusic.startPlay();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
			if (!(camera.position.y < 220)) {
				camera.translate(0, -WALK_SPEED);
				position.y -= WALK_SPEED;
				currentFrame = walkAnimationDown.getKeyFrame(stateTime, true);
				walkBatch.draw(currentFrame, position.x, position.y);
				walkMusic.startPlay();
			}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
			if (!(camera.position.y > 1799)) {
				camera.translate(0, WALK_SPEED);
				position.y += WALK_SPEED;
				currentFrame = walkAnimationUp.getKeyFrame(stateTime, true);
				walkBatch.draw(currentFrame, position.x, position.y);
				walkMusic.startPlay();
			}
		walkBatch.end();

	}

	@Override
	public void dispose() {
		walkBatch.dispose();
		walkSheet.dispose();
		super.dispose();
	}

	public boolean isCollideWithBots(Bot bots) {
		for (MapObject obj : bots.monsterList) {
			// dokoncz potem !
		}
		return false;
	}

	public MyMusic getWalkMusic() {
		return walkMusic;
	}

	public void setWalkMusic(MyMusic walkMusic) {
		this.walkMusic = walkMusic;
	}
}
