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
		super(name, FieldColor.SPECIAL, l, game, size, 0);
	}

	@Override
	public void byPass(Player player) {
		
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
	public void spawn() {
		System.out.println("Spawning paying");
		this.getCircle(0, false, new MaterialData(Material.AIR));
		this.getCircle(0, true, new MaterialData(Material.IRON_BLOCK));
	}

}
