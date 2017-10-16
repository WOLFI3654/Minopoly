package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

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
		super("Freies Parken", FieldColor.SPECIAL, l, game, size, 0);
		
	}

	@Override
	public boolean isOwned() {
		return false;
	}
	
	@Override
	public void byPass(Player player) {
		
	}

	@Override
	public void spawn() {
		
		
	}

}
