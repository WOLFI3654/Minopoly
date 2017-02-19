package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.Main;

public class GameListener implements Listener{

	
	public GameListener() {
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}
}
