package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

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
	public boolean isOwned() {
		return false;
	}
	@Override
	public void spawn() {
		
	}

}
