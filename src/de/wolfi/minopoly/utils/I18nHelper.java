package de.wolfi.minopoly.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.wolfi.utils.i18n.I18n;

public final class I18nHelper {

	public static final void sendMessage(Player player, String msg, boolean audio, Object... args) {
		player.sendMessage(I18n.format("minopoly.prefix") + I18n.format(msg, args));
		if (audio)
			player.playSound(player.getLocation(), msg, 1f, 1f);
	}

	public static final void broadcast(String msg, boolean audio, Object... args) {
		Bukkit.getOnlinePlayers().forEach((p) -> sendMessage(p, msg, audio, args));
	}
}
