package de.wolfi.minopoly.utils;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import de.wolfi.minopoly.components.Minopoly;

public final class MapFactory {

	private static class InternMapRenderer extends MapRenderer {

		private final Minopoly game;
		private Image image;
		protected InternMapRenderer(Minopoly game) {
			this.game = game;
		}

		@Override
		public void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer) {
		}

	}

	private static void clearMap(MapView m) {
		for (final MapRenderer r : m.getRenderers())
			m.removeRenderer(r);
	}

	public static MapView getMap(Minopoly game, Player player) {
		final MapView map = Bukkit.createMap(player.getWorld());
		MapFactory.clearMap(map);
		map.addRenderer(new InternMapRenderer(game));
		
		return map;
	}
}
