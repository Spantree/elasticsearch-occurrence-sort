package net.spantree.elasticsearch.script;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.index.field.data.longs.LongDocFieldData;
import org.elasticsearch.script.AbstractFloatSearchScript;

public class OccurrenceSortScript extends AbstractFloatSearchScript {
	String occurrenceField = "occurrences.startDate";
	private List<UnixTimeInterval> intervals = null;
	UnixTimeInterval superinterval = null;
	
	public OccurrenceSortScript(final List<UnixTimeInterval> intervals) {
		this.intervals = intervals;
		superinterval = new UnixTimeSuperInterval(intervals);
	}
	
	@Override
	public float runAsFloat() {
		if(doc().containsKey(occurrenceField)) {
			Object o = doc().get(occurrenceField);
			
			if(o instanceof LongDocFieldData) {
				LongDocFieldData data = (LongDocFieldData)o;
				if(!data.isEmpty()) {
					long[] startDates = data.getValues();
					Arrays.sort(startDates);
					for(long startDate : startDates) {
						if(superinterval.getStart() != null && startDate < superinterval.getStart()) {
							continue;
						}
						if(superinterval.getEnd() != null && startDate > superinterval.getEnd()) {
							continue;
						}
						for(UnixTimeInterval interval : intervals) {
							if(interval.contains(startDate)) {
								if(superinterval.getStart() != null) {
									return 1f/(startDate-superinterval.getStart());
								} else {
									return 1f/startDate;
								}
							}
						}
					}
				}
			}
		}
		return -1f;
	}

	
}
