package pl.edu.pw.fizyka.pojava.JankowskiOsinski;

public final class Constants {

	public static final String mapName = "map.tmx";
	public static final String newMap = "new_map.tmx";

	public static final String BACKGROUND_IMAGE = "background.jpg";

	public static final String SKIN_NAME = "uiskin.json";

	public static final String KNIGHT_IMG = "person2.png";
	public static final String WIZARD_IMG = "person3.png";

	public static final String[] BOTS_NAMES = { "goblin", "goblin1", "dragon", "dragon1", "demon", "dragon2" };

	public static final float ZOOM = 0.45f;
	public static final float ZOOM_RATE = 0.8f;

	public static final int startPositionX = 352;
	public static final int startPositionY = 1679;

	public static final float endPositionX = 1022;
	public static final float endPositionY = 1131;

	// music
	public static final String FORREST_MUSIC = "music.mp3";
	public static final String DESSERT_MUSIC = "caravan.ogg.ogg";
	public static final String WALK_MUSIC = "footstep.ogg";
	public static final float WALK_MUSIC_VOLUME = 0.1f;
	public static final String WIZZARD_ATTACK_MUSIC = "wizard_sound_attack.mp3";
	public static final String KNIGHT_ATTACK_MUSIC = "knight_sound_attack.mp3";

	public static float MAP_HEIGHT;
	public static float MAP_WIDTH;
	public static int TILE_SIZE = 24;

	public static final int MAP_SCREEN = 1;
	public static final int STATS_SCREEN = 2;
	public static final int SHOP_SCREEN = 3;

	public static final int HP_COST = 10;
	public static final int SKILL_COST = 50;

	public static final int KNIGHT_RANGE = 110;
	public static final int WIZARD_RANGE = 200;
	public static final int MONSTER_RANGE = 110;

	public static boolean isClickedMonster = false;
	public static boolean isCircle = false;

	public static int monsterCurrentAttack = -999;
}
