package fr.frivec.plugin.punishment;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import me.leoko.advancedban.utils.PunishmentType;

public enum Sanctions {
	
	BAN("Ban", Arrays.asList(PunishmentType.BAN, PunishmentType.TEMP_BAN, PunishmentType.IP_BAN, PunishmentType.TEMP_IP_BAN), Material.ANVIL),
	MUTE("Mute", Arrays.asList(PunishmentType.MUTE, PunishmentType.TEMP_MUTE), Material.PAPER),
	KICK("Kick", Arrays.asList(PunishmentType.KICK), Material.WOODEN_SWORD),
	WARN("Warn", Arrays.asList(PunishmentType.WARNING, PunishmentType.TEMP_WARNING), Material.REDSTONE_TORCH);
	
	private String name;
	private List<PunishmentType> types;
	private Material icon;
	
	private Sanctions(final String name, final List<PunishmentType> types, final Material icon) {
		
		this.name = name;
		this.types = types;
		this.icon = icon;
		
	}
	
	public String getName() {
		return name;
	}
	
	public List<PunishmentType> getTypes() {
		return types;
	}
	
	public Material getIcon() {
		return icon;
	}
	
}
