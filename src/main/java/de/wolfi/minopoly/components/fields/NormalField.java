package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import org.bukkit.material.Wool;

public class NormalField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988254265088447089L;
	
	public NormalField(String name, FieldColor color, Location l, Minopoly game, int size, int price) {
		super(name, color, l, game, size, price);
	}
	
	@Override
	public MaterialData getBlock() {
		Wool dye = new Wool(Material.STAINED_CLAY);
		dye.setColor(this.getColor().getColor());
		return dye;
	}
}
