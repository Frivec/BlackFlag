package fr.frivec.plugin.jail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import fr.frivec.BlackFlag;

import static fr.frivec.BlackFlag.log;

public class Jail {
	
	public transient static Set<Jail> jails = new HashSet<>();
	private transient static Path folder = Paths.get(BlackFlag.getInstance().getDataFolder() + "/Jails/");
	
	private String id;
	private double x, y, z;
	private float yaw, pitch;
	private transient Path file;
	
	public Jail(final Location location, final String id) {
		
		this.id = id;
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
		
		this.file = Paths.get(folder + "/" + this.id + ".json");
		
		jails.add(this);
		
	}
	
	public void save() throws IOException {
		
		this.file = Paths.get(folder + "/" + this.id + ".json");
		
		final String json = BlackFlag.getInstance().getJson().serializeObject(this);
		
		if(Files.notExists(this.file))
			
			Files.createFile(this.file);
		
		final BufferedWriter writer = Files.newBufferedWriter(this.file);
		
		writer.write(json);
		writer.flush();
		writer.close();
		
		log("Succesfully saved jail: " + this.id + "" + this.id + ".json");
		
	}
	
	public void delete() throws IOException {
		
		this.file = Paths.get(folder + "/" + this.id + ".json");
		
		Files.delete(this.file);
		
	}
	
	public static Jail get(final String id) {
		
		for(Jail jail : jails)
			
			if(jail.getId().equalsIgnoreCase(id))
				
				return jail;
		
		return null;
		
	}
	
	public static void loadJails() {
		
		try (final DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
			
			for(Path files : stream) {
				
				if(!Files.isDirectory(files)) {
				
					final BufferedReader reader = Files.newBufferedReader(files);
					final StringBuilder builder = new StringBuilder();
					
					String line = null;
					
					while((line = reader.readLine()) != null)
						
						builder.append(line);
					
					reader.close();
					
					final Jail jail = (Jail) BlackFlag.getInstance().getJson().deSeralizeJson(builder.toString(), Jail.class);
					
					jails.add(jail);
					
					log("Succesfully loaded jail " + jail.getId());
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public Location getLocation() {
		
		return new Location(BlackFlag.getInstance().getJailWorld(), x, y, z, yaw, pitch);
		
	}

}
