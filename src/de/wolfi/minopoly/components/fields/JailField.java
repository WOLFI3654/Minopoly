package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class JailField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034500067547136860L;

	public JailField(Location l, Minopoly game, int size) {
		super("Gefängniss", FieldColor.SPECIAL, l, game, size);
	}

	@Override
	public void byPass(Player player) {

	}

	@Override
	public boolean isOwned() {
		return false;
	}

	@Override
	public void playerStand(Player player) {
		Messages.JAIL_FIELD_ENTER.broadcast(player.getName());
	}

	@Override
	public void spawn() {
		System.out.println("Spawning jail");
		this.getCircle(0, false, new MaterialData(Material.AIR));
		this.getCircle(0, true, new MaterialData(Material.IRON_FENCE));
	}
}
