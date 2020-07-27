package fr.frivec.plugin.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.entity.Player;

import fr.frivec.BlackFlag;

import static fr.frivec.BlackFlag.log;

public class BFPlayer {
	
	private transient static Path folder = Paths.get(BlackFlag.getInstance().getDataFolder() + "/Players/");
	
	private String name;
	private boolean inJail, wasInjail;
	
	private transient Path file;
	
	public BFPlayer(final Player player) {
		
		this.name = player.getName();
		this.inJail = false;
		this.wasInjail = false;
		
		this.file = Paths.get(folder + "/" + this.name + ".json");
		
	}
	
	public void save() throws IOException {
		
		if(Files.notExists(this.file))
			
			Files.createFile(this.file);
		
		final BufferedWriter writer = Files.newBufferedWriter(this.file);
		
		writer.write(BlackFlag.getInstance().getJson().serializeObject(this));
		writer.flush();
		writer.close();
		
		log("Saved data for player: " + this.name);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInJail() {
		return inJail;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public boolean wasInjail() {
		return wasInjail;
	}

	public void setWasInjail(boolean wasInjail) {
		this.wasInjail = wasInjail;
	}

}
