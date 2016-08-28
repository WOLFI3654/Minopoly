package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Player;

public class CommunityField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546033930136925490L;

	public CommunityField(String name, FieldColor color, Location l) {
		super(name, color, l);
		
	}
	
	@Override
	public void playerStand(Player player) {
		
	}

}
