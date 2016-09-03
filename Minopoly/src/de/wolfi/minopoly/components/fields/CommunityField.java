package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class CommunityField extends Field{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546033930136925490L;

	public CommunityField(Location l, Minopoly game) {
		super("Gemeinschaffts Feld",FieldColor.SPECIAL, l, game);
		
	}
	
	@Override
	public void playerStand(Player player) {
		Messages.COMMUNITY_FIELD_ENTER.broadcast(player.getName());
	}

	
	@Override
	public boolean isOwned() {
		return false;
	}

	@Override
	public void byPass(Player player) {
		
	}
}
