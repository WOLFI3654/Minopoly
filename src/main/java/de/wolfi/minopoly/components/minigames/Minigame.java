package de.wolfi.minopoly.components.minigames;

import java.util.UUID;

import org.bukkit.Location;

import de.wolfi.minopoly.MinigameRegistry;
import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.GameObject;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.utils.Dangerous;

public class Minigame extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -948671377374764648L;

	private transient MinigameStyleSheet style;
	
	private transient MinigameHook minigameClazz;

	private final UUID uuid;

	private final Minopoly game;

	public Minigame(Minopoly game, MinigameStyleSheet styleSheet) {
		this(game, styleSheet.getUniqIdef());
	}
	
	public Minigame(Minopoly game, UUID styleSheetUUID) {
		this.uuid = styleSheetUUID;
		this.game = game;
	}

	public Location getLocation() {
		return this.style.getLobbyLocation();
	}

	public String getName() {
		return this.style.getName();
	}
	
	@Dangerous(y="Internal use ONLY!")
	public void load() {
		this.style = MinigameRegistry.loadStyleFromUUID(this.uuid);
		try {
			loadHook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadHook() throws Exception{
		minigameClazz = (MinigameHook) this.style.getClazz().getConstructor(Minigame.class).newInstance(this);
	}

	@Dangerous(y="Internal use ONLY!")
	@Override
	public void unload() {
		//XXX
	}
	
	public boolean isLoaded(){
		return minigameClazz != null;
	}
	
	public MinigameHook getMinigameHook() {
		return minigameClazz;
	}

	
	public boolean isEquals(Minigame minigame){
		return this.isEquals(minigame.style);
	}
	public boolean isEquals(MinigameStyleSheet style) {
		return style.getUniqIdef().equals(this.style.getUniqIdef());
	}

	public Minopoly getBoard() {
		return this.game;
	}
	
	public MinigameStyleSheet getStyle() {
		return style;
	}
}
