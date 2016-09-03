package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class JailField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034500067547136860L;

	public JailField(Location l, Minopoly game) {
		super("Gefängniss", FieldColor.SPECIAL, l, game);
	}

	
	@Override
	public void playerStand(Player player) {
		Messages.JAIL_FIELD_ENTER.broadcast(player.getName());
	}

	@Override
	public boolean isOwned() {
		return false;
	}


	@Override
	public void byPass(Player player) {
		
	}

}
