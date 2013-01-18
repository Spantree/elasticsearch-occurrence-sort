package net.spantree.elasticsearch.script;
import org.elasticsearch.script.AbstractFloatSearchScript;


public class OccurrenceSortScript extends AbstractFloatSearchScript {

	@Override
	public float runAsFloat() {
		return 1.5f;
	}

	
}
