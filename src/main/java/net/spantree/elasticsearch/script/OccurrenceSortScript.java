package net.spantree.elasticsearch.script;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.elasticsearch.script.AbstractFloatSearchScript;

import javax.xml.bind.DatatypeConverter;

public class OccurrenceSortScript extends AbstractFloatSearchScript {

	static final String OCCURRENCES_FIELD = "occurrences";
	@Override
	public float runAsFloat() {
		
		//try {
			//if(source().containsKey(OCCURRENCES_FIELD)) { 
				
				if(doc().containsKey("occurrences.startDate")) {
					Object o = doc().get("occurrences.startDate");
					
					if(o instanceof List) {
						return -6.0f;
					}
					else if (o instanceof Map) {
						return -5.0f;
					}
					else if (o instanceof String) {
						return 4.0f;
					}
					
					source().source().put("testing", o.getClass().toString());
					return -7.0f;
				}
				
				
				
				/*List<Map<String,String>> occurrences = null;
				
				if(doc()!=null && doc().containsKey(OCCURRENCES_FIELD)) {
					occurrences = (List<Map<String,String>>) doc().get(OCCURRENCES_FIELD);
				}
				else if(fields() !=null && fields().containsKey(OCCURRENCES_FIELD)) {
					occurrences = (List<Map<String,String>>) fields().get(OCCURRENCES_FIELD);
				}
				else if(source() != null && source().containsKey(OCCURRENCES_FIELD)) {
					occurrences = (List<Map<String,String>>) source().get(OCCURRENCES_FIELD);
				}
				
				Calendar startDate = null;
				
				for(int i=0;occurrences!=null && i<occurrences.size() && i<2;++i) {
					Map<String,String> occurrence = occurrences.get(i);
					if(occurrence.containsKey("startDate")) {
						startDate = DatatypeConverter.parseDateTime(occurrence.get("startDate"));
					}
				}
				
				if(startDate!=null) {
					return startDate.getTimeInMillis();
				}
				else {
					return -1.0f;
				}*/
			/*}
			
		} catch (Exception e) {
			System.out.println("OccurrenceSortScript:" + e.getMessage());
			return -2.0f;
		}*/
		
		return -99.0f;
	}

	
}
