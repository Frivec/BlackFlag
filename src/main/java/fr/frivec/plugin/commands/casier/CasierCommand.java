package fr.frivec.plugin.commands.casier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.frivec.api.menus.MenuManager;
import fr.frivec.plugin.menus.HistoryMenu;

public class CasierCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			final Player player = (Player) sender;
			
			if(!player.hasPermission("blackflag.casier")) {
				
				player.sendMessage("§cVou n'avez pas la permission d'utiliser cette commande.");
				
				return false;
				
			}else {
				
				if(args.length > 0) {
					
					final String targetName = args[0];
				
					MenuManager.getInstance().onOpenMenu(player, new HistoryMenu(targetName));
				
					return true;
					
				}else {
					
					player.sendMessage("§cErreur. Il manque des arguments.");
					player.sendMessage("§aUtilisation: /casier <joueur>");
					
					return false;
					
				}
				
			}
			
		}else
			
			sender.sendMessage("§cErreur. Seuls les joueurs peuvent utiliser cette commande.");
		
		return false;
	}

}
