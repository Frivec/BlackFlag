package fr.frivec.plugin.punishment;

import me.leoko.advancedban.utils.PunishmentType;

public class ComparableSanction {
	
	private String name, reason;
	private long start, end;
	private PunishmentType punishmentType;

	public ComparableSanction(String name, String reason, long start, long end, final PunishmentType punishmentType) {
	
		this.name = name;
		this.reason = reason;
		this.start = start;
		this.end = end;
		this.punishmentType = punishmentType;
	
	}
	
	public PunishmentType getPunishmentType() {
		return punishmentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

}
