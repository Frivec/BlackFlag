package fr.frivec.plugin.jail.log;

import java.util.Date;

import fr.frivec.plugin.jail.Jail;
import fr.frivec.plugin.jail.JailObjective;

public class JailLog {
	
	private String jailName;
	private JailObjective jailObjective;
	private Date start;
	private boolean finished;
	
	public JailLog(final Jail jail, final JailObjective jailObjective, final Date start) {
		
		this.jailName = jail.getId();
		this.jailObjective = jailObjective;
		this.start = start;
		this.finished = false;
		
	}

	public String getJailName() {
		return jailName;
	}

	public void setJailName(String jailName) {
		this.jailName = jailName;
	}

	public JailObjective getJailObjective() {
		return jailObjective;
	}

	public void setJailObjective(JailObjective jailObjective) {
		this.jailObjective = jailObjective;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean isFinished() {
		return finished;
	}

}
