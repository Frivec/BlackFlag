package fr.frivec.plugin.player;

import static fr.frivec.BlackFlag.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import fr.frivec.BlackFlag;
import fr.frivec.plugin.jail.Jail;
import fr.frivec.plugin.jail.JailObjective;

public class BFPlayer {
	
	private transient static Path folder = Paths.get(BlackFlag.getInstance().getDataFolder() + "/Players/");
	public transient static Set<BFPlayer> players = new HashSet<>();
	
	private String name;
	private Jail jail;
	private boolean inJail, wasInjail;
	private int blocksBreaked;
	private JailObjective objective;
	
	private transient Path file;
	
	public BFPlayer(final String player) {
		
		this.name = player;
		this.inJail = false;
		this.wasInjail = false;
		
		this.file = Paths.get(folder + "/" + this.name + ".json");
		
	}
	
	public static BFPlayer loadPlayer(final String player) {
		
		try (final DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
			
			for(Path files : stream) {
				
				if(!Files.isDirectory(files)) {
					
					String fileName = files.getFileName().toString();
					
					fileName = fileName.replace(".json", "");
					
					if(fileName.equals(player)) {
						
						final BufferedReader reader = Files.newBufferedReader(files);
						final StringBuilder json = new StringBuilder();
						
						String line = null;
						
						while((line = reader.readLine()) != null)
							
							json.append(line);
						
						reader.close();
						stream.close();
						
						final BFPlayer bfPlayer = (BFPlayer) BlackFlag.getInstance().getJson().deSeralizeJson(json.toString(), BFPlayer.class);
						
						players.add(bfPlayer);
						
						return bfPlayer;
								
					}
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void save() {
		
		this.file = Paths.get(folder + "/" + this.name + ".json");
		
		try {
		
			if(Files.notExists(this.file))
				
				Files.createFile(this.file);
			
			final BufferedWriter writer = Files.newBufferedWriter(this.file);
			
			writer.write(BlackFlag.getInstance().getJson().serializeObject(this));
			writer.flush();
			writer.close();
			
			log("Saved data for player: " + this.name);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
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

	public void setBlocksBreaked(int blocksBreaked) {
		this.blocksBreaked = blocksBreaked;
	}
	
	public int getBlocksBreaked() {
		return blocksBreaked;
	}
	
	public JailObjective getObjective() {
		return objective;
	}
	
	public void setObjective(JailObjective objective) {
		this.objective = objective;
	}
	
	public Jail getJail() {
		return jail;
	}
	
	public void setJail(Jail jail) {
		this.jail = jail;
	}
	
}
