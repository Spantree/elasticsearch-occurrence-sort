package net.spantree.elasticsearch.script;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class UnixTimeSuperInterval extends UnixTimeInterval {
	public UnixTimeSuperInterval(List<UnixTimeInterval> intervals) {
		if(intervals.size() > 0) {
			SortedSet<Long> startDates = new TreeSet<Long>();
			SortedSet<Long> endDates = new TreeSet<Long>();
			for(UnixTimeInterval interval : intervals) {
				if(interval.getStart() != null) {
					startDates.add(interval.getStart());
				}
				if(interval.getEnd() != null) {
					endDates.add(interval.getEnd());
				}
			}
			if(startDates.size() > 0) {
				setStart(startDates.first());
			}
			if(endDates.size() > 0) {
				setEnd(endDates.last()+1);
			}
		}
	}
}
