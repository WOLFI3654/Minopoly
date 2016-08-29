package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Messages {

	MINIGAME_WIN("Das Minispiel $0 wurde von $1 gewonnen!"),
	MINIGAME_DEATH("$0 ist in $1 gestorben."),
	MINIGAME_GOT_KILLED("$0 wurde in $1 von $2 getötet!"),
	MINIGAME_KILL("Du hast $0 in $1 getötet!"),
	TELEPORT("Du wurdest teleportiert."),
	OTHER_FIELD_ENTERED("$0 ist auf ein Feld von $1 getreten! §l($2)");
	
	
	
	private static String Prefix  = "§0[§1Minopoly§0] §a";
	private String txt;
	private Messages(String txt) {
		this.txt = txt;
	}
	
	public void send(Player player){
		player.sendMessage(Prefix+txt);
	}
	
	public void broadcast(String...target){
		String end = Prefix+txt;
		for(int i = 0; i < target.length; i++) end.replaceAll("$"+i, target[i]); 
		Bukkit.broadcastMessage(end);
	}
}
