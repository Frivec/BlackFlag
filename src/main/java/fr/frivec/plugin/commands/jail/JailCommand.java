package fr.frivec.plugin.commands.jail;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.frivec.BlackFlag;
import fr.frivec.api.packets.Title;
import fr.frivec.plugin.jail.Jail;
import fr.frivec.plugin.jail.JailObjective;
import fr.frivec.plugin.player.BFPlayer;

public class JailCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(args.length >= 3) {
			
			final String targetName = args[0], jailName = args[1], objectiveID = args[2];
			final Player target = Bukkit.getPlayer(targetName);
			final BFPlayer bfPlayer = BlackFlag.getPlayer(targetName);
			final Jail jail = Jail.get(jailName);
			
			int level = -1;
			
			try {
				
				level = Integer.parseInt(objectiveID);
				
			} catch (NumberFormatException e) {

				sender.sendMessage("§cErreur. Le niveau de la peine n'a pas été reconnu. Veuillez insérer un nombre entre 1 et 5.");
				
				return false;

			}
			
			if(jail == null) {
				
				sender.sendMessage("§cErreur. Cette prison n'existe pas.");
				
				return false;
				
			}
			
			if(level != -1) {
			
				final JailObjective objective = JailObjective.get(level);
				
				if(objective != null) {
					
					bfPlayer.setInJail(true);
					bfPlayer.setObjective(objective);
					bfPlayer.setJail(jail);
					
					sender.sendMessage("§aLe joueur §6" + targetName + " §aa bien été placé dans la prison §6" + jailName + "§a.");
					
					if(target != null && target.isOnline()) {
						
						target.teleport(jail.getLocation());
						bfPlayer.setWasInjail(true);
						
						sender.sendMessage("§bLe joueur " + targetName + "§b a été téléporté dans sa prison.");
						
						new Title("§4Incarcération !", "§cVous avez été placé en prison.", 20, 20, 20).send(target);
						
						target.sendMessage("§cVous avez été placé dans la prison n°" + bfPlayer.getJail().getId() + ".");
						target.sendMessage("§3Votre peine est de niveau: §6" + bfPlayer.getObjective().getLevel() + "§3.");
						target.sendMessage("§3Vous devez donc miner §6" + bfPlayer.getObjective().getNumberOfStack() + " stacks d'obsidienne§3, soit §6" + bfPlayer.getObjective().getNumberOfStack() * 64 + " blocs§3.");
						
					}else
						
						sender.sendMessage("§bLe joueur §c" + targetName + "§b n'est pas connecté actuellement. Il sera téléporté lors de sa prochaine connexion.");
					
					bfPlayer.save();
						
					return true;
					
				}else {
					
					sender.sendMessage("§cErreur. Le niveau de la peine n'a pas été reconnu. Veuillez insérer un nombre entre 1 et 5.");
					
					return false;
					
				}
				
			}
			
		}else {
			
			sender.sendMessage("§cErreur. Il manque des arguments.");
			sender.sendMessage("§aUtilisation: /jail <joueur> <prison> <niveau de la peine>");
			
			return false;
			
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
		
		
		
		return null;
	}

}
