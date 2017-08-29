package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.events.DiceEvent;
import de.wolfi.minopoly.events.MinigameFoundEvent;
import de.wolfi.minopoly.events.MoneyEvent;
import de.wolfi.minopoly.events.PlayerJailedEvent;

public class GameListener implements Listener {

	
	private Minopoly game;
	public GameListener(Minopoly game) {
		this.game = game;
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}
	
	@EventHandler
	public void onDice(DiceEvent e){
		
	}
	
	@EventHandler
	public void onMoney(MoneyEvent e){
		game.getScoreboardManager().updatePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onMinigameFound(MinigameFoundEvent e){
		if(this.isAuto()){
			Bukkit.dispatchCommand(this.game, "minigame "+e.getPlayer().getName()+ " select");
		}
	}
	
	@EventHandler
	public void onPlayerJailed(PlayerJailedEvent e){
		if(this.isAuto()){
			e.getPlayer().teleport(this.game.getFieldManager().getJailField());
			this.game.jailPlayer(e.getPlayer().getFigure());
		}
	}
	
	
	
	
	private boolean isAuto(){
		return this.game.getSettings().isAuto();
	}
}
