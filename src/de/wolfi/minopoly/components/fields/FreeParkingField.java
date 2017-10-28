package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class FreeParkingField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6732731077591813283L;

	/**
	 * 
	 */
	

	public FreeParkingField(Location l, Minopoly game, int size) {
		super("Freies Parken", FieldColor.SPECIAL, l, game, size, -1);
		
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
		return new MaterialData(Material.GRASS);
	}

}
