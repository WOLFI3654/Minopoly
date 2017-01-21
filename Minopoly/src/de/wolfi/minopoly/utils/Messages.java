package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Messages {
	
	MINIGAME_WIN("Das Minispiel $0 wurde von $1 gewonnen!"),
	MINIGAME_DEATH("$0 ist in $1 gestorben."),
	MINIGAME_GOT_KILLED("$0 wurde in $1 von $2 getötet!"),
	MINIGAME_KILL("Du hast $0 in $1 getötet!"),
	TELEPORT("Du wurdest teleportiert."),
	START_FIELD_BYPASS("$0 ist über Los gezogen."),
	COMMUNITY_FIELD_ENTER("$0 hat ein Gemeinschaffts Feld betreten!"),
	EVENT_FIELD_ENTER("$0 hat ein Ereigniss Feld betreten!"),
	POLICE_FIELD_ENTER("$0 betrat das Feld der Polizei und wurde dafür ins Gefängnis geschickt."),
	JAIL_FIELD_ENTER("$0 stattet dem Gefängnis einen Besuch ab."),
	OTHER_FIELD_ENTERED("$0 ist auf ein Feld von $1 getreten! §l($2)"),
	
	MONEY_GAIN("Du hast $0 Geld bekommen. ($1)"),
	MONEY_PAYD("Du hast $0 Geld bezahlt. ($1)"),
	MONEY_TRANSFER_GAIN("Du hast $0 Geld von $1 bekommen. ($2)"),
	MONEY_TRANSFER_SENT("Du hast $0 Geld an $1 bezahlt. ($2)"),
	;
	
	
	
	private static String Prefix  = "§0[§1Minopoly§0] §a";
	private String txt;
	private Messages(String txt) {
		this.txt = txt;
	}
	
	public void send(Player player,String... target){
		String end = Prefix+txt;
		for(int i = 0; i < target.length; i++) end.replaceAll("$"+i, target[i]); 
		player.sendMessage(end);
	}
	
	public void broadcast(String...target){
		String end = Prefix+txt;
		for(int i = 0; i < target.length; i++) end.replaceAll("$"+i, target[i]); 
		Bukkit.broadcastMessage(end);
	}
}
