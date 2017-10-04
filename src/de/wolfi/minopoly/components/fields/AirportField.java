package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class AirportField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = -358999371220828508L;

	public AirportField(String name, Location l, Minopoly game, int size, int price) {
		super(name, FieldColor.AIRPORT, l, game, size, price);
	}

	@Override
	public void byPass(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		
	}

}
