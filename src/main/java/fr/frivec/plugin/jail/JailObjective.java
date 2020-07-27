package fr.frivec.plugin.jail;

public enum JailObjective {
	
	LEVEL_1(1, 2),
	LEVEL_2(2, 3),
	LEVEL_3(3, 4),
	LEVEL_4(4, 5),
	LEVEL_5(5, 8);
	
	private int level, numberOfStack;
	
	private JailObjective(final int level, final int numberOfStack) {
		
		this.level = level;
		this.numberOfStack = numberOfStack;
		
	}
	
	public int getNumberOfStack() {
		return numberOfStack;
	}
	
	public int getLevel() {
		return level;
	}

}
