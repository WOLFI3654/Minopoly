package de.wolfi.minopoly.components;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.components.fields.Field;
import de.wolfi.minopoly.utils.DisguiseManager;
import de.wolfi.minopoly.utils.FigureType;
import de.wolfi.minopoly.utils.MapFactory;
import de.wolfi.minopoly.utils.Messages;
import de.wolfi.minopoly.utils.TeleportCause;

public class Player{

	private final org.bukkit.entity.Player hook;
	private final FigureType type;
	
	private Bank money;
	
	private Field location;
	
	private Entity tmp;
	
	private final Minopoly game;
	
	public Player(org.bukkit.entity.Player hook, FigureType t, Minopoly game){
		this.hook = hook;
		this.type = t;
		this.game = game;
	}

	
	public FigureType getFigure() {
		return this.type;
	}

	@Override
	public boolean equals(Object compare) {
		if(!(compare instanceof Player)) return false;
		return this.getFigure() == ((Player) compare).getFigure();
	}
	
	public void teleport(Location to, TeleportCause cause){
		hook.teleport(to);
		if(cause == TeleportCause.MINIGAME_STARTED){
			DisguiseManager.disguise(hook, type);
			if(tmp != null) tmp.remove();
		}else if(cause == TeleportCause.MINIGAME_END){
			DisguiseManager.vanish(hook);
			spawnFigure();
		}else if(cause == TeleportCause.MINIGAME_ACTION){
			Messages.TELEPORT.send(hook);
		}
	}
	
	
	
	private void spawnFigure() {
		if(tmp != null) tmp.remove();
		Entity e = location.getWorld().spawnEntity(location.getLocation(), type.getEntityType());
		e.setCustomName(hook.getName());
		e.setCustomNameVisible(true);
		e.setMetadata("PlayerNPC", new FixedMetadataValue(Main.getMain(), this));
		if(e instanceof LivingEntity){
			((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*60*60, 20));
		}
		tmp = e;
	}

	private String getDisplay() {
		return this.getName()+"("+this.getFigure().getName()+")";
	}
	
	public String getName(){
		return hook.getName();
	}
	
	protected org.bukkit.entity.Player getHook() {
		return hook;
	}
	
	public void setInventory(){
		hook.getInventory().clear();
		hook.sendMap(MapFactory.getMap(game, hook));
		
	}
	
	public void addMoney(int amount){
		this.addMoney(amount, "Grundlos");
	}
	
	public void addMoney(int amount, String reason){
		this.money.addMoney(this, amount);
		Messages.MONEY_GAIN.send(hook, String.valueOf(amount),reason);
	}
	
	public void removeMoney(int amount, String reason){
		this.money.removeMoney(this, amount);
		Messages.MONEY_PAYD.send(hook, String.valueOf(amount),reason);
	}
	
	public void transferMoneyFrom(Player player, int amount, String reason){
		this.money.addMoney(this, amount);
		Messages.MONEY_TRANSFER_GAIN.send(hook, String.valueOf(amount),player.getDisplay(), reason);
	}
	
	public void transferMoneyTo(Player player,int amount, String reason){
		this.money.removeMoney(this, amount);
		Messages.MONEY_TRANSFER_SENT.send(hook, String.valueOf(amount),player.getDisplay(),reason);
	}
	
	public void move(int amount){
		Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), new Runnable() {
			public void run() {
				for(int i = 0; i < amount; i++){
					Bukkit.getScheduler().runTask(Main.getMain(), new Runnable() {
						public void run() {
							Player.this.location = Player.this.game.getNextField(Player.this.location);
							Player.this.spawnFigure();
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
}
