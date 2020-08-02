package fr.frivec.plugin.punishment;

import java.util.Comparator;

public class DateComparator implements Comparator<ComparableSanction> {
	
	@Override
	public int compare(ComparableSanction o1, ComparableSanction o2) {
		
		return o1.getStart() < o2.getStart() ? -1 : 1;
	}
	
}
