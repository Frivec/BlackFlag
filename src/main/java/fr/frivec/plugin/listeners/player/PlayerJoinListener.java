package fr.frivec.plugin.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.frivec.BlackFlag;
import fr.frivec.plugin.player.BFPlayer;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		
		final Player player = event.getPlayer();
		final BFPlayer bfPlayer = BlackFlag.getPlayer(player);
		
	}

}
