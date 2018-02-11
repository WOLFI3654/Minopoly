package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.I18nHelper;

public class JailField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034500067547136860L;

	public JailField(Location l, Minopoly game, int size) {
		super("Gefängniss", FieldColor.SPECIAL, l, game, size, -1);
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
	public void playerStand(Player player) {
		I18nHelper.broadcast("minopoly.gameplay.field.jail.entered", false, player.getDisplay());
	}
	
	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.IRON_BLOCK);
	}
}
