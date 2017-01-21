package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class NormalField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7988254265088447089L;

	public NormalField(String name, FieldColor color, Location l, Minopoly game) {
		super(name, color, l, game);
	}

	@Override
	public void byPass(Player player) {
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawn() {
		getCircle(1, 0, false, new MaterialData(Material.AIR));
		getCircle(1, 0, true, new MaterialData(Material.STAINED_CLAY,getColor().getColor().getData()));
	}

}
