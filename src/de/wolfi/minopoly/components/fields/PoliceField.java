package de.wolfi.minopoly.components.fields;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.PlayerJailedEvent;
import de.wolfi.minopoly.utils.Messages;

public class PoliceField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7221854907860638801L;

	public PoliceField(Location loc, Minopoly game, int size) {
		super("Polizei", FieldColor.SPECIAL, loc, game, size, -1);
	}

	@Override
	public boolean buy(Player player) {
		return false;
	}
	@Override
	public boolean isOwned() {
		return false;
	}

	@Override
	public void playerStand(Player p) {
		Bukkit.getPluginManager().callEvent(new PlayerJailedEvent(p));
		Messages.POLICE_FIELD_ENTER.broadcast(p.getName());
	}

	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.SEA_LANTERN);
	}

}
