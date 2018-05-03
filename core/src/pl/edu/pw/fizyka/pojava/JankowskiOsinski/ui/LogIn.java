package pl.edu.pw.fizyka.pojava.JankowskiOsinski.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.edu.pw.fizyka.pojava.JankowskiOsinski.map.MapScreen;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Person;
import pl.edu.pw.fizyka.pojava.JankowskiOsinski.people.Stats;

public class LogIn {

	public static int gold = Stats.GOLD_START;
	public static int attack = Stats.ATTACK_LEVEL_START;
	public static int magic = Stats.MAGIC_LEVEL_START;
	public static int exp = Stats.EXPERIENCE;
	private static int id;

	public static boolean isLogin(String login, String password) {
		boolean isLog = false;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection("jdbc:mysql://mn26.webd.pl/marekb93_rpggame",
					"marekb93_rpggame", "xBGG)2Jn&b?E?kC+");
			PreparedStatement prep = conn
					.prepareStatement("SELECT * FROM players WHERE login = (?) AND password = (?)");
			prep.setString(1, login);
			prep.setString(2, password);
			prep.executeQuery();
			ResultSet rs = prep.getResultSet();
			isLog = rs.next();

			// odczytac statystyki gracza
			id = rs.getInt("id");
			gold = rs.getInt("gold");
			attack = rs.getInt("skill");
			magic = rs.getInt("magic");
			exp = rs.getInt("exp");
			rs.close();
			prep.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isLog;
	}

	public static void savePlayer(Person person) {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection("jdbc:mysql://mn26.webd.pl/marekb93_rpggame",
					"marekb93_rpggame", "xBGG)2Jn&b?E?kC+");
			PreparedStatement prep = conn
					.prepareStatement("UPDATE players SET gold = (?) , skill = (?), magic = (?), exp = (?) WHERE id = (?)");
			prep.setLong(1, person.getGold());
			prep.setLong(2, person.getAttackLevel());
			prep.setLong(3, person.getMagicLevel());
			prep.setLong(4, person.getExperience());
			prep.setLong(5, LogIn.id);
			prep.executeLargeUpdate();
			System.out.println("elo elo 3 2 0");
			prep.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void loadStatsFromServer(MapScreen mapScreen) {
		// pobieranie z bazy danych statystyk gracza
		mapScreen.getKnight().setAttackLevel(LogIn.attack);
		mapScreen.getKnight().setGold(LogIn.gold);
		mapScreen.getKnight().setMagicLevel(LogIn.magic);
		mapScreen.getKnight().setExperience(LogIn.exp);
	}

}
