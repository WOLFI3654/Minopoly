package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public enum Messages {

	COMMUNITY_FIELD_ENTER("$0 hat ein Gemeinschaffts Feld betreten!\n\n\nOh was ist denn das? Es sieht aus als würde sich hier ein Minispiel verstecken!"), EVENT_FIELD_ENTER(
			"$0 hat ein Ereigniss Feld betreten!"), JAIL_FIELD_ENTER(
					"$0 stattet dem Gefängnis einen Besuch ab."), MINIGAME_SELECTED("$0 hat das Minispiel $1 gefunden."), MINIGAME_DEATH(
							"$0 ist in $1 gestorben."), MINIGAME_GOT_KILLED(
									"$0 wurde in $1 von $2 getötet!"), MINIGAME_KILL(
											"Du hast $0 in $1 getötet!"), MINIGAME_WIN(
													"Das Minispiel $0 wurde von $1 gewonnen!"), MONEY_GAIN(
															"Du hast $0 Geld bekommen. ($1)"), MONEY_PAYD(
																	"Du hast $0 Geld bezahlt. ($1)"), MONEY_TRANSFER_GAIN(
																			"Du hast $0 Geld von $1 bekommen. ($2)"), MONEY_TRANSFER_SENT(
																					"Du hast $0 Geld an $1 bezahlt. ($2)"),

	OTHER_FIELD_ENTERED("$0 ist auf ein Feld von $1 getreten! §l($2)"), FIELD_BOUGHT(
			"$0 hat das Feld $1 gekauft!"), POLICE_FIELD_ENTER(
					"$0 betrat das Feld der Polizei und wurde dafür ins Gefängnis geschickt."), START_FIELD_BYPASS(
							"$0 ist über Los gezogen."), TELEPORT("Du wurdest teleportiert."), MOVE_STARTED(
									"$0 bewegt sich nun $1 Felder."), MOVE_FINISHED(
											"Der Zug von $0 wurde beendet."), PLAYER_ROLLED_THE_DICE(
													"$0 hat eine $1 und eine $2 gewürfelt!"), COMMAND_WRONG_WORLD(
															"§cDu befindest dich nicht in einer Minopoly Welt!"), COMMAND_NO_ARGUMENTS(
																	"§cEs werden Mindestens(!) $0 Argumente benötigt!"), COMMAND_NO_PLAYER(
																			"§c$0 ist kein valider Spieler!"), MONEY_GLOBAL_PAID(
																					"$0 Geld wurden an $1 überwiesen. ($2)"), MONEY_GLOBAL_GOT(
																							"$0 Geld wurden bei $1 abgebucht. ($2)"), MONEY_GLOBAL_TRANSFER(
																									"$0 Geld wurden von $1 auf das Konto von $2 überwiesen. ($3)"), FIGURE_SELECTED(
																											"$0 hat sich Figur $1 ausgesucht. o/"), FIGURE_ALREADY_TAKEN(
																													"§cDie Figur $0 ist bereits von jemandem Ausgewählt!"), TRIPPLE_JAILED("$0 hat 3 mal nacheinander einen Pasch gewürfelt. \nDas riecht verdächtig nach cheaten. Ab in den Knast!");

	public static String Prefix = "§0[§1Minopoly§0] §a";
	private String txt;

	private Messages(String txt) {
		this.txt = txt;
	}

	private String createMessage(String... target) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(Messages.Prefix);
		boolean rplc = false;
		for (char c : this.txt.toCharArray()) {
			if (c == '$') {
				rplc = true;
				continue;
			}
			if (rplc) {
				tmp.append(target[c - '0']);
				rplc = false;
				continue;
			}
			tmp.append(c);
		}
		return tmp.toString();
	}

	public void broadcast(String... target) {
		Bukkit.broadcastMessage(createMessage(target));
	}

	public void send(CommandSender player, String... target) {
		player.sendMessage(createMessage(target));
	}
}
