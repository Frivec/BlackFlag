package fr.frivec.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.frivec.BlackFlag;
import fr.frivec.api.packets.ActionBar;
import fr.frivec.plugin.player.BFPlayer;

public class JailListener implements Listener {
	
	@EventHandler
	public void onTeleport(final PlayerTeleportEvent event) {
		
		final Player player = event.getPlayer();
		final Location to = event.getTo();
		
		if(BlackFlag.getPlayer(player).isInJail()) {
		
			if(to.getWorld() != BlackFlag.getInstance().getJailWorld()) {
				
				event.setCancelled(true);
				player.sendMessage("§cVous ne pouvez pas sortir de prison sans avoir terminé vos objectifs.");
				
				return;
				
			}else
				
				return;
			
		}
		
	}
	
	@EventHandler
	public void onBreakBlock(final BlockBreakEvent event) {
		
		final Player player = event.getPlayer();
		final BFPlayer bfPlayer = BlackFlag.getPlayer(player);
		final Block block = event.getBlock();
		final Material type = block.getType();
		
		if(!bfPlayer.isInJail() || bfPlayer.getJail() == null)
			
			return;
		
		if(!type.equals(Material.OBSIDIAN)) {
			
			event.setCancelled(true);
		
			return;
		
		}
			
		event.setDropItems(false);
		
		bfPlayer.setBlocksBreaked(bfPlayer.getBlocksBreaked() + 1);
				
		new ActionBar("§aVous avez miné §b" + bfPlayer.getBlocksBreaked() + "§7/§b" + bfPlayer.getObjective().getNumberOfStack() * 64, 20, 10, 20).send(player);
		
		if(bfPlayer.getBlocksBreaked() >= (bfPlayer.getObjective().getNumberOfStack() * 64)) {
			
			bfPlayer.setInJail(false);
			bfPlayer.setWasInjail(false);
			
			bfPlayer.setBlocksBreaked(0);
			
			bfPlayer.save();
			
			player.sendMessage("§aBonne nouvelle ! Vous avez purgé votre peine !");
			player.sendMessage("§6Vous êtes à présent libre ! Téléportation dans le monde d'origine.");
			player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
			
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(BlackFlag.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				block.setType(type);
				
			}
			
		}, 20 * 30);
		
	}
	
	@EventHandler
	public void onFoodLevelChange(final FoodLevelChangeEvent event) {
		
		if(event.getEntityType().equals(EntityType.PLAYER)) {
			
			final Player player = (Player) event.getEntity();	
			
			if(BlackFlag.getPlayer(player).isInJail())
				
				player.setFoodLevel(20);
			
		}
		
	}

}
