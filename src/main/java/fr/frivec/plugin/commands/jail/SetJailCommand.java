package fr.frivec.plugin.commands.jail;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.frivec.plugin.jail.Jail;

public class SetJailCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			final Player player = (Player) sender;
			
			if(args.length > 0) {
				
				final String name = args[0];
				
				for(Jail jails : Jail.jails) {
					
					if(jails.getId().equalsIgnoreCase(name)) {
						
						player.sendMessage("§cErreur. Cette prison existe déjà. Veuillez choisir un nouveau nom.");
						
						return true;
						
					}
						
				}
				
				final Jail jail = new Jail(player.getLocation(), name);
				
				try {
					
					jail.save();
					
					player.sendMessage("§aLa prison §6" + name + "§a a bien été créée et sauvegardée.");
					
					return true;
				
				} catch (IOException e) {
					e.printStackTrace();
					player.sendMessage("§cUne erreur est survenue lors de la sauvegarde de la prison: §6" + name + "§c.");
					
					return true;
					
				}
				
			}else {
				
				player.sendMessage("§cErreur. Il est nécessaire d'indiquer un nom à la prison.");
				return true;
				
			}
			
		}else
			
			sender.sendMessage("§cErreur. Seul un joueur peut utiliser cette commande.");
		
		return false;
	}

}
