package de.wolfi.minopoly;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import de.wolfi.minopoly.commands.BankCommand;
import de.wolfi.minopoly.commands.SetupCommand;
import de.wolfi.minopoly.components.Minopoly;

public class Main extends JavaPlugin {

	public static Main getMain() {
		return JavaPlugin.getPlugin(Main.class);
	}

	private final HashMap<String, Minopoly> games = new HashMap<>();

	public Minopoly getMinopoly(World w) {
		return this.games.get(w.getName());
	}

	public boolean isMinopolyWorld(World w) {
		return new File(w.getWorldFolder(), "minopoly.bin").exists();
	}

	public Minopoly loadMap(World w) {
		if (!this.games.containsKey(w.getName())) {
			final File cfg = new File(w.getWorldFolder(), "minopoly.bin");
			if (cfg.exists())
				try {
					final ObjectInputStream in = new ObjectInputStream(new FileInputStream(cfg));
					final Object o = in.readObject();
					if (o instanceof Minopoly) {
						this.games.put(w.getName(), (Minopoly) o);
						((Minopoly) o).load();
					} else
						cfg.delete();
					in.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			else
				this.games.put(w.getName(), new Minopoly(w.getName()));

		}
		return this.getMinopoly(w);
	}

	@Override
	public void onEnable() {
		this.getCommand("setupminopoly").setExecutor(new SetupCommand());
		this.getCommand("bank").setExecutor(new BankCommand());

	}

}
