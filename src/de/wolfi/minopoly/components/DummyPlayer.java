package de.wolfi.minopoly.components;

import org.bukkit.Location;

import de.wolfi.minopoly.utils.FigureType;
import de.wolfi.minopoly.utils.TeleportCause;

/**
 * Does absolutely NOTHING
 * 
 * @author WOLFI3654
 * 
 */
public class DummyPlayer extends Player {

	public DummyPlayer(org.bukkit.entity.Player player) {
		this(player, null);
	}
	
	public DummyPlayer(org.bukkit.entity.Player hook, Minopoly game) {
		super(hook, null, game, null);
	}

	public Minopoly getBoard() {
		return this.game;

	}
	
	@Override
	public void addMoney(int amount) {}

	@Override
	public void addMoney(int amount, String reason) {}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object compare) {
		return this == compare;
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public FigureType getFigure() {
		return super.getFigure();
	}

	@Override
	public org.bukkit.entity.Player getHook() {
		return super.getHook();
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public void move(int amount) {
	}

	@Override
	public void removeMoney(int amount, String reason) {
	}

	@Override
	public void setInventory() {
	}

	@Override
	public void teleport(Location to, TeleportCause cause) {
	}

	@Override
	public void transferMoneyTo(Player player, int amount, String reason) {
	}

	@Override
	protected SerializeablePlayer serialize() {
		return null;
	}

}
