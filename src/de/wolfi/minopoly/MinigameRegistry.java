package de.wolfi.minopoly;

import java.util.ArrayList;

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

	}
}
