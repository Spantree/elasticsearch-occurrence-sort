package net.spantree.elasticsearch.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OccurrenceParameterParser {
	static final Pattern startPattern = Pattern.compile("start_(\\d+)", Pattern.CASE_INSENSITIVE);
	static final Pattern endPattern = Pattern.compile("end_(\\d+)", Pattern.CASE_INSENSITIVE);
	static final String DEFAULT_OCCURRENCE_FIELD = "occurrence.startDate";
	
	static List<UnixTimeInterval> parseIntervals(Map<String, Object> params) {
		List<UnixTimeInterval> intervals = new ArrayList<UnixTimeInterval>();
		
		Integer intervalIndex = null;
		Boolean isStart = null;
		UnixTimeInterval interval = null;
		for(String key : params.keySet()) {
			intervalIndex = null;
			interval = null;
			Matcher startMatcher = startPattern.matcher(key);
			isStart = startMatcher.matches();
			
			if(isStart) {
				intervalIndex = Integer.parseInt(startMatcher.group(1));
			} else {
				Matcher endMatcher = endPattern.matcher(key);
				if(endMatcher.matches()) {
					intervalIndex = Integer.parseInt(endMatcher.group(1));
				}
			}
			
			if(intervalIndex != null) {
				if(intervals.size() < intervalIndex) {
					interval = new UnixTimeInterval();
					intervals.add(interval);
				} else {
					interval = intervals.get(intervalIndex-1);
				}
				
				Long value = (Long)params.get(key);
				if(isStart) {
					interval.setStart(value);
				} else {
					interval.setEnd(value);
				}
			}
		}
		Collections.sort(intervals);
		return intervals;
	}
}
