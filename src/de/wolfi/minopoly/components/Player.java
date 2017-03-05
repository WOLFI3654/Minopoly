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

public class Player {

	private final Minopoly game;
	private final org.bukkit.entity.Player hook;

	private Field location;

	private Bank money;

	private Entity tmp;

	private final FigureType type;

	public Player(org.bukkit.entity.Player hook, FigureType t, Minopoly game) {
		this.hook = hook;
		this.type = t;
		this.game = game;
	}

	public void addMoney(int amount) {
		this.addMoney(amount, "Grundlos");
	}

	public void addMoney(int amount, String reason) {
		this.money.addMoney(this, amount);
		Messages.MONEY_GAIN.send(this.hook, String.valueOf(amount), reason);
	}

	@Override
	public boolean equals(Object compare) {
		if (!(compare instanceof Player))
			return false;
		return this.getFigure() == ((Player) compare).getFigure();
	}

	public String getDisplay() {
		return this.getName() + "(" + this.getFigure().getName() + ")";
	}

	public FigureType getFigure() {
		return this.type;
	}

	protected org.bukkit.entity.Player getHook() {
		return this.hook;
	}

	public String getName() {
		return this.hook.getName();
	}

	public void move(int amount) {
		Messages.MOVE_STARTED.broadcast(this.getDisplay(),Integer.toString(amount));
		Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
			for (int i = 0; i < amount; i++) {
				final int currentNumber = i;
				Bukkit.getScheduler().runTask(Main.getMain(), () -> {
					Player.this.location = Player.this.game.getFieldManager().getNextField(Player.this.location);
					if(currentNumber < amount - 1) Player.this.location.byPass(Player.this);
					Player.this.spawnFigure();
				});
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
			Messages.MOVE_FINISHED.broadcast(Player.this.getDisplay());
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Player.this.location.playerStand(Player.this);
		});

	}

	public void removeMoney(int amount, String reason) {
		this.money.removeMoney(this, amount);
		Messages.MONEY_PAYD.send(this.hook, String.valueOf(amount), reason);
	}

	public void setInventory() {
		this.hook.getInventory().clear();
		this.hook.sendMap(MapFactory.getMap(this.game, this.hook));

	}

	private void spawnFigure() {
		if (this.tmp != null)
			this.tmp.remove();
		final Entity e = this.location.getWorld().spawnEntity(this.location.getLocation(), this.type.getEntityType());
		e.setCustomName(this.hook.getName());
		e.setCustomNameVisible(true);
		e.setMetadata("PlayerNPC", new FixedMetadataValue(Main.getMain(), this));
		if (e instanceof LivingEntity)
			((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60 * 60, 20));
		this.tmp = e;
	}

	public void teleport(Location to, TeleportCause cause) {
		this.hook.teleport(to);
		if (cause == TeleportCause.MINIGAME_STARTED) {
			DisguiseManager.disguise(this.hook, this.type);
			if (this.tmp != null)
				this.tmp.remove();
		} else if (cause == TeleportCause.MINIGAME_END) {
			DisguiseManager.vanish(this.hook);
			this.spawnFigure();
		} else if (cause == TeleportCause.MINIGAME_ACTION)
			Messages.TELEPORT.send(this.hook);
	}

	public void transferMoneyFrom(Player player, int amount, String reason) {
		this.money.addMoney(this, amount);
		Messages.MONEY_TRANSFER_GAIN.send(this.hook, String.valueOf(amount), player.getDisplay(), reason);
	}

	public void transferMoneyTo(Player player, int amount, String reason) {
		this.money.removeMoney(this, amount);
		Messages.MONEY_TRANSFER_SENT.send(this.hook, String.valueOf(amount), player.getDisplay(), reason);
	}
}
