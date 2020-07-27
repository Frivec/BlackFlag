package fr.frivec.plugin.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.frivec.plugin.jail.Jail;

public class DevCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			final Player player = (Player) sender;
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("createjail")) {
					
					final Jail jail = new Jail(player.getLocation(), 1);
					
					player.sendMessage("§aCreated new jail: " + jail.getId());
					
					try {
					
						jail.save();

					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}
