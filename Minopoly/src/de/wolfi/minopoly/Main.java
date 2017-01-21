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

public class Main extends JavaPlugin{

	private final HashMap<String,Minopoly> games = new HashMap<>();
 	
	
	
	@Override
	public void onEnable() {
		getCommand("setupminopoly").setExecutor(new SetupCommand());
		getCommand("bank").setExecutor(new BankCommand());
		
	}
	
	public static Main getMain(){
		return JavaPlugin.getPlugin(Main.class);
	}
	
	public Minopoly getMinopoly(World w){
		return games.get(w.getName());
	}
	
	public boolean isMinopolyWorld(World w){
		return new File(w.getWorldFolder(),"minopoly.bin").exists();
	}
	
	public Minopoly loadMap(World w){
		if(!games.containsKey(w.getName())){
			File cfg = new File(w.getWorldFolder(),"minopoly.bin");
			if(cfg.exists()){
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(cfg));
					Object o = in.readObject();
					if(o instanceof Minopoly){
						games.put(w.getName(), (Minopoly) o);
						((Minopoly) o).load();
					}else {
						cfg.delete();
					}
					in.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			
			}else games.put(w.getName(), new Minopoly(w.getName()));
			
		}
		return getMinopoly(w);
	}
	
}
