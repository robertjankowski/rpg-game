package pl.edu.pw.fizyka.pojava.JankowskiOsinski;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.ScreenSwitcher;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.Shop;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.screens.StatsScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui.Menu;

public class RPGgame extends Game {

	public MapScreen mapScreen;
	StatsScreen statsScreen;
	Shop shop;
	Menu menu;
	ScreenSwitcher screenSwitcher;

	@Override
	public void create() {
		menu = new Menu(this);
		this.setScreen(menu);
	}

	public void loadGame() {
		mapScreen = new MapScreen(this);
		statsScreen = new StatsScreen(mapScreen);
		shop = new Shop(mapScreen);
		this.setScreen(mapScreen);
		screenSwitcher = new ScreenSwitcher(this, mapScreen, statsScreen, shop);
		InputMultiplexer im = new InputMultiplexer(screenSwitcher, mapScreen.mapPlayerStats.stage, shop.stage);
		Gdx.input.setInputProcessor(im);
	}

	public ScreenSwitcher getScreenSwitcher() {
		return screenSwitcher;
	}

}
