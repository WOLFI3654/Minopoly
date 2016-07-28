package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Messages {

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
