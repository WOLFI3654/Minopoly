package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

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

}
