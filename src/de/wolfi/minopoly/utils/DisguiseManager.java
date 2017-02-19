package de.wolfi.minopoly.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.Disguise;

public final class DisguiseManager {

	private static DisguiseAPI disguise;

	private static final HashMap<Player, Disguise> disguises = new HashMap<>();

	public static final Disguise disguise(Player bukkitPlayer, FigureType type) {
		final Disguise d = type.createDisguise();
		DisguiseManager.disguise.disguise(bukkitPlayer, d);
		DisguiseManager.disguises.put(bukkitPlayer, d);
		return d;
	}

	public static final Disguise getPlayersDisguise(Player p) {
		return DisguiseManager.disguises.get(p);
	}

	public static final void hookInto() {
		DisguiseManager.disguise = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();
	}

	public static void vanish(org.bukkit.entity.Player hook) {
		DisguiseManager.disguise.undisguise(hook);
		DisguiseManager.disguises.remove(hook);
		Bukkit.getOnlinePlayers().forEach((p) -> p.hidePlayer(hook));

	}

}
