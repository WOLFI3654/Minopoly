package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class PayingField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6720011079595049636L;

	public PayingField(String name,Location l, Minopoly game, int size) {
		super(name, FieldColor.SPECIAL, l, game, size, -1);
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
	public MaterialData getBlock() {
		return new MaterialData(Material.IRON_BLOCK);
	}

}
