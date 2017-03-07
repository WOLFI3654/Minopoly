package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gnu.trove.map.TMap;

public enum Messages {

	COMMUNITY_FIELD_ENTER("$0 hat ein Gemeinschaffts Feld betreten!"), EVENT_FIELD_ENTER(
			"$0 hat ein Ereigniss Feld betreten!"), JAIL_FIELD_ENTER(
					"$0 stattet dem Gefängnis einen Besuch ab."), MINIGAME_DEATH(
							"$0 ist in $1 gestorben."), MINIGAME_GOT_KILLED(
									"$0 wurde in $1 von $2 getötet!"), MINIGAME_KILL(
											"Du hast $0 in $1 getötet!"), MINIGAME_WIN(
													"Das Minispiel $0 wurde von $1 gewonnen!"), MONEY_GAIN(
															"Du hast $0 Geld bekommen. ($1)"), MONEY_PAYD(
																	"Du hast $0 Geld bezahlt. ($1)"), MONEY_TRANSFER_GAIN(
																			"Du hast $0 Geld von $1 bekommen. ($2)"), MONEY_TRANSFER_SENT(
																					"Du hast $0 Geld an $1 bezahlt. ($2)"),

	OTHER_FIELD_ENTERED("$0 ist auf ein Feld von $1 getreten! §l($2)"), POLICE_FIELD_ENTER(
			"$0 betrat das Feld der Polizei und wurde dafür ins Gefängnis geschickt."), START_FIELD_BYPASS(
					"$0 ist über Los gezogen."), TELEPORT(
							"Du wurdest teleportiert."), MOVE_STARTED("$0 bewegt sich nun $1 Felder."),
	MOVE_FINISHED("Der Zug von $0 wurde beendet.");

	private static String Prefix = "§0[§1Minopoly§0] §a";
	private String txt;

	private Messages(String txt) {
		this.txt = txt;
	}

	public void broadcast(String... target) {
		final String end = Messages.Prefix + this.txt;
		StringBuilder tmp = new StringBuilder();
		
		for (int i = 0; i < target.length; i++)
			tmp.append(end.replaceAll("$" + i, target[i]));
		Bukkit.broadcastMessage(tmp.toString());
	}

	public void send(Player player, String... target) {
		final String end = Messages.Prefix + this.txt;
		StringBuilder tmp = new StringBuilder();
		
		for (int i = 0; i < target.length; i++)
			tmp.append(end.replaceAll("$" + i, target[i]));
		player.sendMessage(tmp.toString());
	}
}
