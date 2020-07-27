package fr.frivec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import fr.frivec.api.json.GsonManager;
import fr.frivec.plugin.commands.DevCommand;
import fr.frivec.plugin.jail.Jail;

public class BlackFlag extends JavaPlugin {
	
	private static BlackFlag instance;
	
	private World jailWorld;
	
	private GsonManager json;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		this.json = new GsonManager();
		
		saveDefaultConfig();
		
		//Create folders
		
		try {
				
			if(Files.notExists(this.getDataFolder().toPath()))
				
				Files.createDirectory(this.getDataFolder().toPath());
			
			if(Files.notExists(Paths.get(this.getDataFolder().toPath() + "/Jails/")))
				
				Files.createDirectory(Paths.get(this.getDataFolder().toPath() + "/Jails/"));
			
			log("Created folders !");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading the jail world
		this.jailWorld = this.getServer().getWorld(this.getConfig().getString("worldName"));
		
		//Loading the jails
		Jail.loadJails();
		
		//Commands
		this.getCommand("dev").setExecutor(new DevCommand());
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}
	
	public GsonManager getJson() {
		return json;
	}
	
	public static BlackFlag getInstance() {
		return instance;
	}
	
	public World getJailWorld() {
		return jailWorld;
	}
	
	public static void log(final String message) {
		
		BlackFlag.getInstance().getServer().getConsoleSender().sendMessage("§a" + message);
		
	}

}
