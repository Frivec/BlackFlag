package fr.frivec.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.frivec.BlackFlag;
import fr.frivec.plugin.player.BFPlayer;

public class JailListener implements Listener {
	
	@EventHandler
	public void onTeleport(final PlayerTeleportEvent event) {
		
		final Player player = event.getPlayer();
		final Location to = event.getTo();
		
		if(to.getWorld() != BlackFlag.getInstance().getJailWorld()) {
			
			event.setCancelled(true);
			player.sendMessage("§cVous ne pouvez pas sortir de prison sans avoir terminé vos objectifs.");
			
			return;
			
		}else
			
			return;
		
	}
	
	@EventHandler
	public void onBreakBlock(final BlockBreakEvent event) {
		
		final Player player = event.getPlayer();
		final BFPlayer bfPlayer = BlackFlag.getPlayer(player);
		final Block block = event.getBlock();
		final Material type = block.getType(); 
		
		event.setDropItems(false);
		
		
		
		bfPlayer.setBlocksBreaked(bfPlayer.getBlocksBreaked() + 1);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(BlackFlag.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				block.setType(type);
				
			}
			
		}, 20 * 5);
		
	}

}
