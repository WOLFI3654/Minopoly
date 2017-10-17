package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class FundsField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 586199880230047625L;

	public FundsField(String name, Location l, Minopoly game, int size) {
		super(name, FieldColor.FUNDS, l, game, size, -1);
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
