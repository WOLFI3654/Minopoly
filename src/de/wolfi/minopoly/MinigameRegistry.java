package de.wolfi.minopoly;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;

import de.wolfi.minopoly.components.minigames.MinigameHook;

public final class MinigameRegistry {

	private static final ArrayList<MinigameStyleSheet> MINIGAME_STYLE_SHEETS = new ArrayList<MinigameStyleSheet>();

	public static void registerMinigame(MinigameStyleSheet sheet) {
		MinigameRegistry.MINIGAME_STYLE_SHEETS.add(sheet);
	}

	public static interface MinigameStyleSheet {
		public String getName();

		public Class<MinigameHook> getClazz();

		public void getMinPlayer();

		public void getMaxPlayers();

		public Location getLobbyLocation();

		public UUID getUniqIdef();
	}

	public static MinigameStyleSheet loadStyleFromUUID(UUID uuid) {
		MinigameStyleSheet sheet = null;
		for (MinigameStyleSheet s : MinigameRegistry.MINIGAME_STYLE_SHEETS)
			if (s.getUniqIdef().equals(uuid))
				sheet = s;
		return sheet;
	}

	public static Iterable<MinigameStyleSheet> minigames() {
		return MinigameRegistry.MINIGAME_STYLE_SHEETS;
	}
}
