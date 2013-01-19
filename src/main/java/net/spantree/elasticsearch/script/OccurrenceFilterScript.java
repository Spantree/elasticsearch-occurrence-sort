package net.spantree.elasticsearch.script;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.DatatypeConverter;

import org.elasticsearch.script.AbstractSearchScript;

@SuppressWarnings("rawtypes")
public class OccurrenceFilterScript extends AbstractSearchScript {
	private final String occurrenceField = "occurrences";
	private final List<UnixTimeInterval> intervals;
	private Map<String, Object> fromOccurrence = null;
	private Map<String, Object> toOccurrence = null;
	
	public OccurrenceFilterScript(final List<UnixTimeInterval> intervals) {
		this.intervals = intervals;
		UnixTimeInterval superinterval = new UnixTimeSuperInterval(intervals);
		fromOccurrence = createOccurrenceForFilter(superinterval.getStart());
		toOccurrence = createOccurrenceForFilter(superinterval.getEnd());
	}
	
	@SuppressWarnings("unchecked")
	void addParsedOccurrenceFields(List<Map<String, Object>> occurrences) {
		for(Map occurrence : occurrences) {
			String startDateStr = (String)occurrence.get(OccurrenceComparator.STRING_FIELD);
			if(startDateStr != null) {
				occurrence.put(OccurrenceComparator.PARSED_FIELD, DatatypeConverter.parseDateTime(startDateStr).getTimeInMillis());
			}
		}
	}
	
	Map<String, Object> createOccurrenceForFilter(Long dateTime) {
		if(dateTime == null) return null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(OccurrenceComparator.PARSED_FIELD, dateTime);
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(dateTime);
		map.put(OccurrenceComparator.STRING_FIELD, DatatypeConverter.printDateTime(cal));
		
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override 
	public Object run() {
		List<Map<String, Object>> filtered = new ArrayList<Map<String, Object>>();
		if(source().containsKey(occurrenceField)) {
			List<Map<String, Object>> occurrences = (List<Map<String, Object>>)source().get(occurrenceField);
			addParsedOccurrenceFields(occurrences);
			Collections.sort(occurrences, new OccurrenceComparator());
			
			boolean isDone = false;
			Long startDate = null;
			for(Map<String, Object> occurrence : occurrences) {
				startDate = (Long)occurrence.get(OccurrenceComparator.PARSED_FIELD);
				if(startDate != null) {
					for(int i=0; i<intervals.size(); i++) {
						UnixTimeInterval interval = intervals.get(i);
						if(interval.contains(startDate)) {
							occurrence.remove(OccurrenceComparator.PARSED_FIELD);
							filtered.add(occurrence);
							break;
						} else if(i == intervals.size() - 1 && startDate > interval.getStart()) {
							isDone = true;
							break;
						}
					}
				}
				if(isDone) {
					break;
				}
			}
		}
		return filtered;
	}
}
