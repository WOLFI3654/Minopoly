package de.wolfi.minopoly.components.fields;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.events.MinigameFoundEvent;
import de.wolfi.minopoly.utils.I18nHelper;

public class CommunityField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546033930136925490L;

	public CommunityField(Location l, Minopoly game, int size) {
		super("Gemeinschafts Feld", FieldColor.SPECIAL, l, game, size, -1);

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
		I18nHelper.broadcast("minopoly.gameplay.field.community.entered", false, player.getDisplay());
		I18nHelper.broadcast("minopoly.ferdinand.field.community.entered", true);
//		Messages.COMMUNITY_FIELD_ENTER.broadcast(player.getName());
		Bukkit.getPluginManager().callEvent(new MinigameFoundEvent(player));
	}

	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.MELON_BLOCK);
	}
}
