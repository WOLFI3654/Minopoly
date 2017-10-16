package de.wolfi.minopoly.components;

import java.io.File;
import java.io.IOException;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class MainSettings {

	File yaml;
	YamlConfiguration configuration;

	protected MainSettings(World world) {
		yaml = new File(world.getWorldFolder(), "minop.yml");
	}

	protected void load() {
		if (yaml.exists()) {
			configuration = YamlConfiguration.loadConfiguration(yaml);
		}else{
			configuration = new YamlConfiguration();
		}

		configuration.addDefault("Auto", false);
		configuration.addDefault("ForcedPay", false);
		configuration.addDefault("PlayedTimes", 0);
		configuration.addDefault("MaxPlayers", 5);
		configuration.addDefault("Pott", true);
		save();

		
	}
	
	public boolean isAuto(){
		return configuration.getBoolean("Auto");
	}
	
	public boolean isPayForced(){
		return configuration.getBoolean("ForcedPay");
	}
	
	public boolean hasPott(){
		return configuration.getBoolean("Pott");
	}
	

	private void save(){
		try {
			if (!yaml.exists()) {

				yaml.createNewFile();
			}
			configuration.save(yaml);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void unload() {
		save();

	}

}
