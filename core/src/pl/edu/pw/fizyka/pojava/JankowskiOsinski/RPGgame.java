package pl.edu.pw.fizyka.pojava.JankowskiOsinski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.ScreenSwitcher;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.Shop;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.StatsScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui.Menu;

public class RPGgame extends Game {

	public MapScreen mapScreen;
	StatsScreen statsScreen;
	Shop shop;
	Menu menu;

	@Override
	public void create() {
		menu = new Menu(this);
		this.setScreen(menu);
	}

	public void loadGame() {
		mapScreen = new MapScreen();
		statsScreen = new StatsScreen(mapScreen);
		shop = new Shop(mapScreen);
		this.setScreen(mapScreen);
		InputMultiplexer im = new InputMultiplexer(new ScreenSwitcher(this, mapScreen, statsScreen, shop),
				mapScreen.mapPlayerStats.stage, shop.stage);
		Gdx.input.setInputProcessor(im);
	}
}
