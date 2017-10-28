package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class StartField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -819511061769975984L;

	public StartField(Location l, Minopoly game, int size) {
		super("Los", FieldColor.SPECIAL, l, game, size, -1);
	}

	@Override
	public boolean buy(Player player) {
		return false;
	}
	@Override
	public boolean isOwned() {
		return false;
	}
	
	@Override
	public void byPass(Player player) {
		Messages.START_FIELD_BYPASS.broadcast(player.getName());
		player.addMoney(200, "Los passiert");

	}
	@Override
	public void playerStand(Player player) {
		player.addMoney(400,"Los besucht");
		super.playerStand(player);
	}

	
	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.GOLD_BLOCK);
	}

}
