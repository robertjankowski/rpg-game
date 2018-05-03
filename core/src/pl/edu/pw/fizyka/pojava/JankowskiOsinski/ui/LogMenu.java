package pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.Constants;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.RPGgame;

public class LogMenu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton registerButton;
	private TextButton loginButton;
	private TextButton returnButton;
	private TextField loginField;
	private TextArea passwordArea;
	private Label loginLabel;
	private Label passwordLabel;
	private AssetManager assetManager;

	private RPGgame game;
	private Menu menu;

	public LogMenu(RPGgame game, Menu menu) {
		this.game = game;
		this.menu = menu;
	}

	@Override
	public void show() {
		assetManager = new AssetManager();
		assetManager.load("uiskin.json", Skin.class);
		assetManager.finishLoading();

		registerButton = new TextButton("Register", assetManager.get(Constants.SKIN_NAME, Skin.class));
		loginButton = new TextButton("Login", assetManager.get(Constants.SKIN_NAME, Skin.class));
		returnButton = new TextButton("Back to menu", assetManager.get(Constants.SKIN_NAME, Skin.class));
		loginLabel = new Label("Login: ", assetManager.get(Constants.SKIN_NAME, Skin.class));
		passwordLabel = new Label("Password: ", assetManager.get(Constants.SKIN_NAME, Skin.class));
		loginField = new TextField("", assetManager.get(Constants.SKIN_NAME, Skin.class));
		passwordArea = new TextArea("", assetManager.get(Constants.SKIN_NAME, Skin.class));
		final Dialog dialog = new Dialog("Wrong login or password", assetManager.get(Constants.SKIN_NAME, Skin.class));
		loginButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (LogIn.isLogin(loginField.getText(), passwordArea.getText())) {
					game.loadGame();
					// ładowanie statystyk z bazy danych
					LogIn.loadStatsFromServer(game.mapScreen);
				} else {
					dialog.show(stage);
					Timer.schedule(new Task() {
						@Override
						public void run() {
							dialog.hide();
						}
					}, 2);
					loginField.setText("");
					passwordArea.setText("");
				}
			}
		});
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(menu);
			}
		});
		registerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// link do strony gdzie mozna sie zalogowac
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				try {
					desktop.browse(new URI("http://www.paawel97.webd.pro"));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Source is not available");
				}
			}
		});

		stage = new Stage(new ScreenViewport());
		table = new Table();

		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());

		table.padTop(50);
		table.add(loginLabel).padBottom(50);
		table.add(loginField).padBottom(50);
		table.row();
		table.add(passwordLabel).padBottom(50);
		table.add(passwordArea).padBottom(50);
		table.row();
		table.add(loginButton);
		table.add(registerButton);

		returnButton.setPosition(10, 20, Align.left);
		stage.addActor(table);
		stage.addActor(returnButton);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menu.renderBackgroundImage();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
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

	@Override
	public void dispose() {
	}

}
