package de.wolfi.minopoly.components.fields;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.world.registry.WorldData;

import de.wolfi.minopoly.components.GameObject;
import de.wolfi.minopoly.components.Minopoly;
import de.wolfi.minopoly.components.Player;
import de.wolfi.minopoly.utils.Dangerous;
import de.wolfi.minopoly.utils.FigureType;
import de.wolfi.minopoly.utils.Messages;

public abstract class Field extends GameObject {

	private static final HashMap<FieldColor, ArrayList<Field>> members = new HashMap<>();

	/**
	 * 
	 */
	private final float r;

	private static final long serialVersionUID = 2119752416278230984L;

	private static final void add(FieldColor color2, Field field) {
		ArrayList<Field> l = Field.members.get(color2);
		if (l == null)
			l = new ArrayList<>();
		if (!l.contains(field))
			l.add(field);
		Field.members.put(color2, l);
	}

	private final FieldColor color;
	protected final Minopoly game;

	private boolean isOwned = false;

	private transient Location location,tp,stored_home;
	private transient ArrayList<ArmorStand> nametag;
	
	private final String name;

	private FigureType owner;

	private final HashMap<String, Object> storedLocation,storedHome;

	private final int price,billing;
	
	public Field(String name, FieldColor color, Location l, Minopoly game, int size, int price) {
		this.color = color;
		this.name = name;
		this.price = price;
		
		this.billing = price/11;
		
		this.storedHome = new HashMap<>();
		this.storedLocation = new HashMap<>(l.serialize());
		this.location = l;
		this.game = game;
		this.r = size + 0.5F;
		Field.add(color, this);

		this.load();
	}

	public abstract void byPass(Player player);

	
	public void setHome(Location loc){
		this.stored_home = loc;
		this.storedHome.putAll(loc.serialize());
	}
	
	@SuppressWarnings("deprecation")
	protected void getCircle(int yAdd, boolean falling, MaterialData m) {
		final World w = this.location.getWorld();
		

		// final double increment = (2 * Math.PI) / amount;
		int radiusCeil = (int) Math.ceil(r);
		for (double x = -radiusCeil; x <= radiusCeil; x++) {

			for (double z = -radiusCeil; z <= radiusCeil; z++) {

				final Location l = new Location(w, this.location.getX() + x, this.location.getY(),
						this.location.getZ() + z);
				if (this.location.distance(l) > this.r)
					continue;
				l.add(0, yAdd, 0);
				l.getBlock().setType(m.getItemType());
				l.getBlock().setData(m.getData(), false);
				//				l.getBlock().setType(m.getItemType());
//				l.getBlock().setData(m.getData());
				if (falling)
						w.spawnEntity(l, EntityType.FALLING_BLOCK);			
			}
		}
	}

	public FieldColor getColor() {
		return this.color;
	}

	public Location getLocation() {
		return this.location;
	}
	
	public Location getTeleportLocation(){
		return this.tp;
	}

	private String getName() {
		return this.name;
	}

	public int getPrice() {
		return this.price;
	}
	
	public int getBilling() {
		return billing;
	}
	
	
	@Override
	public String toString() {
		return this.getColor().getColorChat() + this.getName();
	}

	
	public boolean buy(Player player){
		player.removeMoney(this.price, "Buy "+this.toString());
		this.setOwner(player);
		this.spawnHouse();
		removeName();
		createNametag();
		Messages.FIELD_BOUGHT.broadcast(player.getDisplay(),this.toString());
		return true;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner.getFigure();
		this.isOwned = true;
	}

	public Player getOwner() {
		return this.game.getByFigureType(this.owner);
	}

	public World getWorld() {
		return this.location.getWorld();
	}

	public boolean isOwned() {
		return this.isOwned;
	}

	@Dangerous(y = "Internal use ONLY!")
	@Override
	public void load() {
		this.location = Location.deserialize(this.storedLocation);
		this.tp = this.location.clone().add(0,1,0);
		this.nametag = new ArrayList<>();
		this.createNametag();
		this.spawn();
		try{
			this.stored_home = Location.deserialize(storedHome);
			if(this.isOwned()) this.spawnHouse();
		}catch(Exception e){
			//XXX TODO FIX
		}

	}

	private void spawnHouse() {
		WorldEdit worldEdit = WorldEdit.getInstance();
		LocalConfiguration config = worldEdit.getConfiguration();
		com.sk89q.worldedit.world.World world = new BukkitWorld(stored_home.getWorld());
		EditSession esession = worldEdit.getEditSessionFactory().getEditSession(world, 9999);
        File dir = worldEdit.getWorkingDirectoryFile(config.saveDir);
        File f = new File(dir, this.owner.getName()+"_"+this.color.toString()+".schematic");

        if (!f.exists()) {
        	Bukkit.broadcastMessage("ERROR NO SCHEMATIC FOR "+f.getName());
//            player.printError("Schematic " + filename + " does not exist!");
            return;
        }
        ClipboardFormat format = ClipboardFormat.findByAlias("schematic");
        if (format == null) {
        	Bukkit.broadcastMessage("W00T");
//            player.printError("Unknown schematic format: " + formatName);
            return;
        }
        LocalSession session = new LocalSession();
        Closer closer = Closer.create();
        try {
            FileInputStream fis = closer.register(new FileInputStream(f));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = format.getReader(bis);

            WorldData worldData =world.getWorldData();
            Clipboard clipboard = reader.read(worldData);
            session.setClipboard(new ClipboardHolder(clipboard, worldData));
            
            Logger.getGlobal().info(" loaded " + f.getCanonicalPath());
//            Vector to = atOrigin ? clipboard.getOrigin() : session.getPlacementPosition(player);
            Operation operation = session.getClipboard()
                    .createPaste(esession, worldData)
                    .to(new Vector(this.stored_home.getBlockX(), this.stored_home.getY(), this.stored_home.getBlockZ()))
                    .ignoreAirBlocks(true)
                    .build();
            
            Operations.completeLegacy(operation);

           
//            player.print("The clipboard has been pasted at " + to);

            //            player.print(filename + " loaded. Paste it with //paste");
        } catch (IOException e) {
//            player.printError("Schematic could not read or it does not exist: " + e.getMessage());
            Logger.getGlobal().log(Level.WARNING, "Failed to load a saved clipboard", e);
        } catch (MaxChangedBlocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyClipboardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                closer.close();
            } catch (IOException ignored) {
            }
        }

	}
//	worldEdit.getEditSessionFactory().getEditSession(worldEdit., 99999);

	private void createNametag() {
		Location loc = this.location.clone().add(.5, 2.5, .5);
		if(isOwned)
			createStand(loc, this.color.getColorChat() +"Owner: "+ this.owner.getDisplay());
		else 
			createStand(loc, this.color.getColorChat() +"Price: "+this.getPrice());
		createStand(loc.add(0, .5, 0),this.color.getColorChat() +"Billing: "+this.getBilling());
		createStand(loc.add(0, .5, 0),this.color.getColorChat() + this.getName());
	}
	
	private void createStand(Location loc, String name){
		ArmorStand stand = this.game.getWorld().spawn(loc, ArmorStand.class);
		stand.setGravity(false);
		stand.setCustomName(name);
		stand.setCustomNameVisible(true);
		stand.setVisible(false);
		this.nametag.add(stand);
	}

	public void playerStand(Player player) {
		if (this.isOwned())
			if (!this.owner.equals(player.getFigure()))
				Messages.OTHER_FIELD_ENTERED.broadcast(player.getName(), this.owner.getName(),
						this.color.getColorChat() + this.getName());
	}

	public abstract void spawn();

	public boolean isOwnedBy(Player player) {
		return this.isOwned() && this.getOwner().equals(player);
	}

	
	private final void removeName(){
		for(ArmorStand name : nametag) name.remove();
		nametag.clear();
	}
	@Dangerous(y = "Internal use ONLY!")
	@Override
	public void unload() {
		removeName();
	}

}
