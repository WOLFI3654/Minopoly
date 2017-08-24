package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.events.DiceEvent;
import de.wolfi.minopoly.events.MoneyEvent;

public class GameListener implements Listener {

	
	private Minopoly game;
	public GameListener(Minopoly game) {
		this.game = game;
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}
	
	@EventHandler
	public void onDice(DiceEvent e){
		
	}
	
	public void onMoney(MoneyEvent e){
		game.getScoreboardManager().updatePlayer(e.getPlayer());
	}
}
