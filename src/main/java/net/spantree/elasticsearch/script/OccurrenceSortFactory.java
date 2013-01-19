package net.spantree.elasticsearch.script;

import java.util.Map;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;

public class OccurrenceSortFactory implements NativeScriptFactory {
	public ExecutableScript newScript(@Nullable Map<String, Object> params) {
		return new OccurrenceSortScript();
	}

}
