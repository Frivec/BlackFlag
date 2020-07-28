package fr.frivec.plugin.commands.jail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.frivec.plugin.jail.Jail;

public class RemoveJailCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player && !((Player) sender).hasPermission("blackflag.removejail")) {
			
			sender.sendMessage("§cVous n'avez pas la permission de faire ceci.");
			
			return false;
			
		}
		
		if(args.length < 0) {
			
			sender.sendMessage("§cErreur. Merci d'indiquer le nom de la prison à supprimer.");
			
			return false;
			
		}else {
			
			final String name = args[0];
			Jail jail = Jail.get(name);
			
			if(jail != null) {
				
				try {
					
					jail.delete();
					
					Jail.jails.remove(jail);
					
					sender.sendMessage("§aLa prison §6" + name + "§a a bien été supprimée.");
					
					return true;
					
				} catch (IOException e) {
				
					e.printStackTrace();
					sender.sendMessage("§cUne erreur est survenue au moment de supprimer la sauvegarde de la prison: §6" + name + "§c.");
					
					return false;
					
				}
				
			}else {
				
				sender.sendMessage("§cErreur. Aucune prison n'a été trouvée avec ce nom. Veuillez réessayer.");
				
				return false;
				
			}
				
		}
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player && !((Player) sender).hasPermission("blackflag.removejail"))
			
			return Arrays.asList("Non.");
		
		if(args.length >= 0) {
			
			final List<String> list = new ArrayList<>();
			
			for(Jail jail : Jail.jails)
				
				list.add(jail.getId());
			
			return list;
				
		}
		
		return null;
	}

}
