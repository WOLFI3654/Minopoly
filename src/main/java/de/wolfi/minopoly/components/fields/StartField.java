package de.wolfi.minopoly.components.fields;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.I18nHelper;

public class StartField extends Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = -819511061769975984L;

	public StartField(Location l, Minopoly game, int size) {
		super("Los", FieldColor.SPECIAL, l, game, size, -1);
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
	public void byPass(Player player) {
		player.sendMessage("minopoly.ferdinand.field.start.passed",true);
		I18nHelper.broadcast("minopoly.gameplay.field.start.passed", false, player.getDisplay());
		player.addMoney(100, "Los passiert");

	}
	@Override
	public void playerStand(Player player) {
		player.sendMessage("minopoly.ferdinand.field.start.passed",true);
		I18nHelper.broadcast("minopoly.gameplay.field.start.passed", false, player.getDisplay());
		player.addMoney(200,"Los besucht");
		super.playerStand(player);
	}

	
	@Override
	public MaterialData getBlock() {
		return new MaterialData(Material.GOLD_BLOCK);
	}

}
