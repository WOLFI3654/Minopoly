package de.wolfi.minopoly.components.minigames;

import java.util.UUID;

import org.bukkit.Location;

import de.wolfi.minopoly.MinigameRegistry;
import de.wolfi.minopoly.MinigameRegistry.MinigameStyleSheet;
import de.wolfi.minopoly.components.GameObject;
import de.wolfi.minopoly.utils.Dangerous;

public class Minigame extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -948671377374764648L;

	private transient MinigameStyleSheet style;
	
	private transient MinigameHook minigameClazz;

	private final UUID uuid;

	public Minigame(UUID styleSheetUUID) {
		this.uuid = styleSheetUUID;
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

	@Override
	public boolean equals(Object obj) {
		MinigameStyleSheet sheet = null;
		if(obj instanceof MinigameStyleSheet)
			sheet = (MinigameStyleSheet) obj;
		else sheet = ((Minigame)obj).style;
		assert sheet != null;
		return this.style.equals(sheet);
	}
}
