package fr.frivec.plugin.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		
		final Player player = event.getPlayer();
		
	}

}
