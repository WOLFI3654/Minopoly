package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.events.DiceEvent;
import de.wolfi.minopoly.events.MinigameFoundEvent;
import de.wolfi.minopoly.events.MinigameWinEvent;
import de.wolfi.minopoly.events.MoneyEvent;
import de.wolfi.minopoly.events.PlayerJailedEvent;
import de.wolfi.minopoly.utils.CancelConstants;

public class GameListener implements Listener {

	
	private Minopoly game;
	public GameListener(Minopoly game) {
		this.game = game;
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}
	
	@EventHandler
	public void onDice(DiceEvent e){
		if(this.isAuto()){
			e.getPlayer().move(e.getOne()+e.getTwo());
			if(e.getOne() == e.getTwo()) Bukkit.broadcastMessage("Pasch");
		}
	}
	
	@EventHandler
	public void onMoney(MoneyEvent e){
		game.getScoreboardManager().updatePlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onMinigameWin(MinigameWinEvent e){
		if(this.isAuto()){
			Bukkit.dispatchCommand(this.game, "minigame "+e.getPlayer().getName()+ " stop");
		}
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
	
	
	@EventHandler
	public void onDamageNPC(EntityDamageEvent e){
		if(e.getEntity().hasMetadata("PlayerNPC") || e.getEntity().hasMetadata(CancelConstants.CANCEL_DAMAGE)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChangeWorld(EntityChangeBlockEvent e){
		if(e.getEntity().hasMetadata(CancelConstants.CANCEL_BLOCK_CHANGE)){
			e.setCancelled(true);
		}
	}
}
