package fr.frivec.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.frivec.BlackFlag;
import fr.frivec.api.packets.Title;
import fr.frivec.plugin.player.BFPlayer;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		
		final Player player = event.getPlayer();
		final BFPlayer bfPlayer = BlackFlag.getPlayer(player);
		final boolean inJail = bfPlayer.isInJail(), wasInJail = bfPlayer.wasInjail();
		
		if(inJail && !wasInJail) {
			
			new Title("§4Incarcération !", "§cVous avez été placé en prison.", 20, 20, 20).send(player);
			
			player.sendMessage("§cVous avez été placé dans la prison n°" + bfPlayer.getJail().getId() + ".");
			player.sendMessage("§3Votre peine est de niveau: §6" + bfPlayer.getObjective().getLevel() + "§3.");
			player.sendMessage("§3Vous devez donc miner §6" + bfPlayer.getObjective().getNumberOfStack() + " stacks d'obsidienne§3, soit §6" + bfPlayer.getObjective().getNumberOfStack() * 64 + " blocs§3.");
			
			player.teleport(bfPlayer.getJail().getLocation());
			
			bfPlayer.setWasInjail(true);
			
		}else if(!inJail && wasInJail) {
			
			player.sendMessage("§aBonne nouvelle ! Vous avez été libéré de votre emprisonnement !");
			player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
			
		}
		
		return;
		
	}
	
	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		
		final Player player = event.getPlayer();
		
		BlackFlag.getPlayer(player).save();
		
	}

}
