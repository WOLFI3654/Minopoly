package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;

public class FundsField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 586199880230047625L;

	public FundsField(String name, Location l, Minopoly game, int size, int prize) {
		super(name, FieldColor.FUNDS, l, game, size,  prize);
	}

	@Override
	public void byPass(Player player) {
		
		
	}

	@Override
	public void spawn() {
		// TODO Auto-generated method stub
		
	}

}
