package de.wolfi.minopoly.components.fields;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.EventFoundEvent;
import de.wolfi.minopoly.utils.I18nHelper;

public class EventField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546033930136925490L;

	public EventField(Location l, Minopoly game, int size) {
		super("Ereigniss Feld", FieldColor.SPECIAL, l, game, size, -1);

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
	public void playerStand(Player player) {
		I18nHelper.broadcast("minopoly.gameplay.field.event.entered", false, player.getDisplay());
		I18nHelper.broadcast("minopoly.ferdinand.field.event.entered", true);
//		Messages.EVENT_FIELD_ENTER.broadcast(player.getName());
		Bukkit.getPluginManager().callEvent(new EventFoundEvent(player));

	}
	
	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.PUMPKIN);
	}
}
