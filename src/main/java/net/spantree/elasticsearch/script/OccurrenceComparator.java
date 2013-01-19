package net.spantree.elasticsearch.script;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("rawtypes")
public class OccurrenceComparator implements Comparator<Map> {
	public static final String STRING_FIELD = "startDate";
	public static final String PARSED_FIELD = "_startDateParsed";
	
	@Override
	public int compare(Map a, Map b) {
		if(a == null && b != null) return -1;
		if(a != null && b == null) return 1;
		Object aParsedObj = a.get(PARSED_FIELD);
		Object bParsedObj = a.get(PARSED_FIELD);
		if(aParsedObj != null && bParsedObj != null) {
			return ((Long)aParsedObj).compareTo((Long)bParsedObj);
		}
		Object aObj = a.get(STRING_FIELD);
		Object bObj = b.get(STRING_FIELD);
		if(aObj instanceof String && !(bObj instanceof String)) return -1;
		if(!(aObj instanceof String) && bObj instanceof String) return 1;
		Calendar aStart = DatatypeConverter.parseDateTime((String)a.get(STRING_FIELD));
		Calendar bStart = DatatypeConverter.parseDateTime((String)b.get(STRING_FIELD));
		return aStart.compareTo(bStart);
	}
	
}
