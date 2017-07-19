package de.wolfi.minopoly.components.fields;

import org.bukkit.Bukkit;
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
	
	public NormalField(String name, FieldColor color, Location l, Minopoly game, int size, int price) {
		super(name, color, l, game, size, price);
	}

	@Override
	public void byPass(Player player) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawn() {
		Bukkit.broadcastMessage("Spawning Normal");
		this.getCircle(0, false, new MaterialData(Material.AIR));
		this.getCircle(0, true, new MaterialData(Material.STAINED_CLAY, this.getColor().getColor().getWoolData()));
	}

}
