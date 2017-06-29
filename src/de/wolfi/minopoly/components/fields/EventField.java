package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Messages;

public class EventField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546033930136925490L;

	public EventField(Location l, Minopoly game) {
		super("Ereigniss Feld", FieldColor.SPECIAL, l, game);

	}

	@Override
	public void byPass(Player player) {

	}

	@Override
	public boolean isOwned() {
		return false;
	}

	@Override
	public void playerStand(Player player) {
		Messages.EVENT_FIELD_ENTER.broadcast(player.getName());
	}

	@Override
	public void spawn() {
		System.out.println("Spawning event");
		this.getCircle(1, 0, false, new MaterialData(Material.AIR));
		this.getCircle(1, 0, true, new MaterialData(Material.ICE));
	}
}
