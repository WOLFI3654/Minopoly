package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class StartField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = -819511061769975984L;

	public StartField(String name, FieldColor color, Location l, Minopoly game) {
		super(name, color, l, game);
	}

	@Override
	public void byPass(Player player) {
		Messages.START_FIELD_BYPASS.broadcast(player.getName());
		player.addMoney(200, "Los passiert");
		
	}

}
