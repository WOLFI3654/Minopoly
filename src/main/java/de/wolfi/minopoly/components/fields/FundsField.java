package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;

public class FundsField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 586199880230047625L;

	public FundsField(String name, Location l, Minopoly game, int size, int prize) {
		super(name, FieldColor.FUNDS, l, game, size,  prize);
	}
	

	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.EMERALD_BLOCK);
	}

}
