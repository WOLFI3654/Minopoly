package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class PoliceField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7221854907860638801L;

	public PoliceField(Location loc, Minopoly game) {
		super("Polizei", FieldColor.SPECIAL, loc, game);
	}
	
	public void playerStand(Player p){
		Messages.POLICE_FIELD_ENTER.broadcast(p.getName());
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
		getCircle(1, 0, false, new MaterialData(Material.AIR));
		getCircle(1, 0, true, new MaterialData(Material.COAL_BLOCK));
	}

}
