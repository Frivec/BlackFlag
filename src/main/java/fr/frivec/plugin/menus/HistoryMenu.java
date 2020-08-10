package fr.frivec.plugin.menus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import fr.frivec.BlackFlag;
import fr.frivec.api.items.ItemCreator;
import fr.frivec.api.menus.AbstractMenu;
import fr.frivec.plugin.jail.log.JailLog;
import fr.frivec.plugin.player.BFPlayer;
import fr.frivec.plugin.punishment.ComparableSanction;
import fr.frivec.plugin.punishment.DateComparator;
import fr.frivec.plugin.punishment.Sanctions;
import me.leoko.advancedban.manager.PunishmentManager;
import me.leoko.advancedban.manager.UUIDManager;
import me.leoko.advancedban.utils.Punishment;
import me.leoko.advancedban.utils.PunishmentType;

public class HistoryMenu extends AbstractMenu {
	
	private String targetName;
	
	private PunishmentManager punishmentManager;
	private UUIDManager uuidManager;
	
	private List<Punishment> punishments = new ArrayList<>();
	
	private int page = 0;
	
	private Sanctions sanction = null;

	public HistoryMenu(final String targetName) {
		
		super(BlackFlag.getInstance(), "Historique de " + targetName, 9*5);
		
		this.targetName = targetName;
		this.punishmentManager = PunishmentManager.get();
		this.uuidManager = UUIDManager.get();
		
	}

	@Override
	public void open(Player player) {
		
		final String uuid = this.uuidManager.getUUID(this.targetName);
		
		final ItemStack glassPane = new ItemCreator(Material.GREEN_STAINED_GLASS_PANE, 1).setDisplayName("").setLores(Arrays.asList("")).build();
		
		for(int i = 0; i < this.inventory.getSize(); i++)
			
			if(i < 18 || i > 26)
			
				this.addItem(glassPane, i, "NOTHING");
		
		int slot = 20;
		
		for(Sanctions sanctions : Sanctions.values()) {
			
			final String name = sanctions.getName();
			int numberOfSanction = 0;
			
			if(sanctions.getTypes() == null)
				
				//JailHistory
				numberOfSanction = BlackFlag.getPlayer(player.getName()).getJailLog().size();
				
			else
				
				//Sanctions from AdvancedBan
				for(PunishmentType types : sanctions.getTypes())
					
					numberOfSanction += getPunishments(uuid, types).size();
			
			this.addItem(new ItemCreator(sanctions.getIcon(), 1).setDisplayName("§c" + name).setLores(numberOfSanction == 0 ? Arrays.asList("§cAucun(e) " + name.toLowerCase() + " n'a été trouvé") : Arrays.asList("§a" + numberOfSanction + " " + name.toLowerCase() + (numberOfSanction > 1 ? "s trouvés" : " trouvé") + ".")).build()
					, slot, "OPEN_" + name.toUpperCase());
			
			slot++;
			
		}
		
		player.openInventory(this.inventory);
			
	}

	@Override
	public void close(Player player) {/*Not used*/}

	@Override
	public void onInteract(Player player, ItemStack itemStack, int slot, InventoryAction action) {
		
		if(this.actions.get(slot).contains("OPEN")) {
			
			this.page = 0;
			
			drawSanctions(player, slot, false);
			
			return;
			
		}else if(this.actions.get(slot).equals("NEXT")) {
			
			if(this.page + 1 > getNumberOfPage(this.punishments)) {
				
				player.sendMessage("§cVous êtes à la dernière page disponible.");
				
				return;
				
			}
			
			this.page++;
			
			drawSanctions(player, slot, true);
			
			return;
			
		}else if(this.actions.get(slot).equals("PREVIOUS")) {
			
			if(this.page - 1 < 0) {
				
				player.sendMessage("§cErreur. Vous êtes déjà à la base de l'historique.");
				
				return;
				
			}
			
			this.page--;
			
			drawSanctions(player, slot, true);
			
			return;
			
		}
		
	}
	
	private void drawSanctions(final Player player, final int slot, final boolean useCurrentSanction) {
		
		this.inventory.clear();
		this.punishments.clear();
		
		if(!useCurrentSanction) {
			
			final String sanctionName = this.actions.get(slot).split("[_]")[1];
		
			for(Sanctions sanctions : Sanctions.values()) {
				
				if(sanctions.getName().equalsIgnoreCase(sanctionName)) {
					
					sanction = sanctions;
					
					break;
					
				}
				
			}
			
		}
		
		if(this.sanction != null) {
			
			if(this.sanction.equals(Sanctions.JAILS)) {
				
				//Jail history
				
				for(int i = 0; i < 35; i++) {
					
					final BFPlayer bfPlayer = BlackFlag.getPlayer(player.getName());
					
					final int inventorySlot = i + 27 * this.page;
					final ArrayList<JailLog> logs = bfPlayer.getJailLog();
					final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy à HH:mm");
					
					if(inventorySlot >= logs.size())
						
						break;
					
					final JailLog jailLog = logs.get(inventorySlot);
					
					this.inventory.setItem(inventorySlot, new ItemCreator(Material.IRON_BARS, 1).setDisplayName("§a" + format.format(jailLog.getStart()))
																			.setLores(Arrays.asList("§5Prison: §b" + jailLog.getJailName(),
																									"§5Niveau de peine: §b" + jailLog.getJailObjective().getLevel(),
																									"§5Terminé: " + (jailLog.isFinished() ? "§aOui" : "§cNon"),
																									"§5Progression: " + (jailLog.isFinished() ? "§cAucune" : "§b" + bfPlayer.getBlocksBreaked() + "§7/" + jailLog.getJailObjective().getNumberOfStack() * 64))).build());
					
				}
				
			}else {
				
				//Sanctions from AdvancedBan
				
				final List<ComparableSanction> comparableSanctions = new ArrayList<>();
				
				for(PunishmentType punishmentType : sanction.getTypes()) {
					
					for(Punishment punishment : getPunishments(this.uuidManager.getUUID(this.targetName), punishmentType)) {
						
						this.punishments.add(punishment);		
						comparableSanctions.add(new ComparableSanction(punishment.getName(), punishment.getReason(), punishment.getStart(), punishment.getEnd(), punishmentType));
						
					}
					
				}
				
				comparableSanctions.sort(new DateComparator());
				
				for(int i = 0; i < 35; i++) {
					
					final int inventorySlot = i + 27 * this.page;
					
					if(inventorySlot >= comparableSanctions.size())
						
						break;
					
					final ComparableSanction sanctions = comparableSanctions.get(inventorySlot);
					
					if(sanctions == null)
						
						break;
					
					final Date start = new Date(), end = new Date();
					final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy à HH:mm");
									
					start.setTime(sanctions.getStart());
					end.setTime(sanctions.getEnd());
					
					this.inventory.setItem(inventorySlot, new ItemCreator(sanction.getIcon(), 1).setDisplayName("§a" + format.format(start)).
							setLores(Arrays.asList("§5Raison: §b" + sanctions.getReason(),
													"§5Fin: §b" + (sanctions.getEnd() != -1 ? format.format(end) : "Aucune"),
													"§5Permanent: §b" + (sanctions.getEnd() != -1 ? "Non" : sanctions.getPunishmentType().equals(PunishmentType.KICK) ? "Non" : "Oui"))).build());
					
				}
				
			}
			
			final ItemStack next = new ItemCreator(Material.PLAYER_HEAD, 1).skullByUrl("https://textures.minecraft.net/texture/2a3b8f681daad8bf436cae8da3fe8131f62a162ab81af639c3e0644aa6abac2f").setDisplayName("§aPage suivante").build(),
						previous = new ItemCreator(Material.PLAYER_HEAD, 1).skullByUrl("https://textures.minecraft.net/texture/8652e2b936ca8026bd28651d7c9f2819d2e923697734d18dfdb13550f8fdad5f").setDisplayName("§aPage précédente").build(),
						info = new ItemCreator(Material.TORCH, 1).setDisplayName("§3Page " + (this.page + 1) + "/" + getNumberOfPage(this.punishments)).build();
			
			this.addItem(next, 41, "NEXT");
			this.addItem(previous, 39, "PREVIOUS");
			this.addItem(info, 40, "NOTHING");
			
			player.updateInventory();
			
			return;
			
		}else {
			
			player.sendMessage("§cUne erreur est survenue.");
			
			return;
			
		}
		
	}
	
	private int getNumberOfPage(final List<Punishment> punishments) {
		
		int i = 1;
		
		while(punishments.size() > i * 36)
			
			i++;
		
		return i;
		
	}
	
	private List<Punishment> getPunishments(String uuid, PunishmentType punishmentType) {
		
		return this.punishmentManager.getPunishments(uuid, punishmentType, false);
		
	}

}
