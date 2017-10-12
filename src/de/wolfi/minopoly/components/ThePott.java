package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.fields.FreeParkingField;
import de.wolfi.minopoly.events.FieldEvent;

public class ThePott extends GameObject implements Listener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5058788402062982273L;

	private int currentCash = 0;

	private Minopoly game;
	
	public ThePott(Minopoly game) {
		this.game = game;

	}
	
	
	@Override
	protected void load() {
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
	}

	@Override
	protected void unload() {}

	@EventHandler
	public void onPottEntered(FieldEvent e){
		if(this.game.getSettings().hasPott() && e.getField() instanceof FreeParkingField){
			if(this.currentCash>0){
				payMoney(e.getPlayer());
			}
		}
	}
	
	public void payMoney(Player p){
		p.addMoney(currentCash, "Pott-Cash");
		this.currentCash = 0;
		this.updateCounter();
	}
	public void addMoney(int a){
		this.currentCash += a;
		this.updateCounter();
	}
	
	private void updateCounter(){
		this.game.getScoreboardManager().setValue("ThePott:", currentCash);
	}
	
	public int getCurrentCash() {
		return currentCash;
	}
}
