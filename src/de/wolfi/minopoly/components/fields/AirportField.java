package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;

public class AirportField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = -358999371220828508L;

	public AirportField(String name, Location l, Minopoly game, int size, int price) {
		super(name, FieldColor.AIRPORT, l, game, size, price);
	}

	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.MOSSY_COBBLESTONE);
	}

}
