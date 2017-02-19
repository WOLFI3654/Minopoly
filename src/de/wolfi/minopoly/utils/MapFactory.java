package de.wolfi.minopoly.utils;

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

	public static MapView getMap(Minopoly game, Player player){
		MapView map = Bukkit.createMap(player.getWorld());
		clearMap(map);
		map.addRenderer(new InternMapRenderer(game));
		return map;
	}
	
	private static void clearMap(MapView m){
		for(MapRenderer r : m.getRenderers()){
			m.removeRenderer(r);
		}
	}
	
	private static class InternMapRenderer extends MapRenderer{

		private final Minopoly game;
		protected InternMapRenderer(Minopoly game) {
			this.game = game;
		}
		
		@Override
		public void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer) {
			de.wolfi.minopoly.components.Player p = game.getByBukkitPlayer(paramPlayer);
			try {
				paramMapCanvas.drawImage(0, 0, ImageIO.read(ClassLoader.getSystemResource("res/monopoly.png")));
				paramMapCanvas.drawText(0, 0, new MinecraftFont(), p.getFigure().getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
