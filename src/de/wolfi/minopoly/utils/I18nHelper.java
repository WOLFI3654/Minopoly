package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class I18nHelper {

	public static final void sendMessage(Player player, String msg, boolean audio, String... args){
		player.sendMessage(msg);
		if(audio) player.playSound(player.getLocation(), msg, 1f, 1f);
	}
	
	
	public static final void broadcast(String msg, boolean audio, String... args){
		Bukkit.getOnlinePlayers().forEach((p)->sendMessage(p, msg, audio, args));
	}
}
