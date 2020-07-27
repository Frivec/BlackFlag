package fr.frivec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.frivec.api.json.GsonManager;
import fr.frivec.plugin.commands.DevCommand;
import fr.frivec.plugin.commands.jail.JailCommand;
import fr.frivec.plugin.commands.jail.RemoveJailCommand;
import fr.frivec.plugin.commands.jail.SetJailCommand;
import fr.frivec.plugin.jail.Jail;
import fr.frivec.plugin.listeners.player.JailListener;
import fr.frivec.plugin.listeners.player.PlayerJoinListener;
import fr.frivec.plugin.player.BFPlayer;

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
			
			if(Files.notExists(Paths.get(this.getDataFolder().toPath() + "/Players/")))
				
				Files.createDirectory(Paths.get(this.getDataFolder().toPath() + "/Players/"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loading the jail world
		this.jailWorld = this.getServer().getWorld(this.getConfig().getString("worldName"));
		
		if(this.jailWorld == null)
			
			this.jailWorld = this.getServer().createWorld(new WorldCreator(this.getConfig().getString("worldName")));
		
		//Loading the jails
		Jail.loadJails();
		
		//Commands
		this.getCommand("dev").setExecutor(new DevCommand());
		this.getCommand("setjail").setExecutor(new SetJailCommand());
		this.getCommand("removejail").setExecutor(new RemoveJailCommand());
		this.getCommand("jail").setExecutor(new JailCommand());
		
		//Listeners
		registerListener(new PlayerJoinListener());
		registerListener(new JailListener());
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}
	
	/*
	 * 
	 * Getters
	 * 
	 */
	
	/**
	 * Get the Gson instance
	 * 
	 * @return gson instance with method to serialize and deserialize a Java POJO
	 */
	public GsonManager getJson() {
		return json;
	}
	
	/**
	 * Get the main instance of the plugin
	 * 
	 * @return instance of BlackFlag class
	 */
	public static BlackFlag getInstance() {
		return instance;
	}
	
	/**
	 * Get the jails' world
	 * 
	 * @return World - world instance
	 */
	public World getJailWorld() {
		return jailWorld;
	}
	
	/**
	 * 
	 * Register a listener on the server
	 * 
	 * @param listener - Listener: Bukkit listener you want to register
	 */
	private void registerListener(final Listener listener) {
		
		this.getServer().getPluginManager().registerEvents(listener, this);
		
	}
	
	/**
	 * 
	 * Get the BFPlayer instance of a player
	 * It will get in the RAM if exists, try to load it from the save files or create a new instance if the file does not exists
	 * 
	 * @param player - Player: Bukkit player you want to get the BFPlayer instance
	 * @return BFPlayer instance for the player
	 */
	public static BFPlayer getPlayer(final Player player) {
		
		for(BFPlayer players : BFPlayer.players)
			
			if(players.getName().equals(player.getName()))
				
				return players;
		
		BFPlayer bfPlayer = BFPlayer.loadPlayer(player);
		
		if(bfPlayer != null)
			
			return bfPlayer;
		
		else {
			
			bfPlayer = new BFPlayer(player);
			bfPlayer.save();
			
			return bfPlayer;
			
		}
		
	}
	
	/**
	 * 
	 * Send a message into the console of the server
	 * 
	 * @param message - String: Message you want to send into the console
	 */
	public static void log(final String message) {
		
		BlackFlag.getInstance().getServer().getConsoleSender().sendMessage("§a" + message);
		
	}

}
