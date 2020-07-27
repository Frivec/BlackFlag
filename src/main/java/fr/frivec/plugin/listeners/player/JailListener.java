package fr.frivec.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.frivec.BlackFlag;

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
		final Block block = event.getBlock();
		final Material type = block.getType(); 
		
		player.sendMessage("§atarget event: " + block.getType());
		event.setDropItems(false);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(BlackFlag.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				block.setType(type);
				final FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().clone().add(0, 20, 0), block.getBlockData());
				block.setType(Material.AIR);
				
				fallingBlock.setDropItem(false);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(BlackFlag.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						player.sendMessage("§aVelocity of fBlock: " + fallingBlock.getVelocity().getY());	
						
					}
					
				}, 20l);
				
				player.sendMessage("§aSpawn new block: " + block.getType() + " |");
				
			}
			
		}, 20 * 5);
		
	}

}
