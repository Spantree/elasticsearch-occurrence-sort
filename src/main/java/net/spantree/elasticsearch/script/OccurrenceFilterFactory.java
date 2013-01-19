package net.spantree.elasticsearch.script;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;

public class OccurrenceFilterFactory implements NativeScriptFactory {
	public ExecutableScript newScript(@Nullable Map<String, Object> params) {
		List<UnixTimeInterval> intervals = OccurrenceParameterParser.parseIntervals(params);
		return new OccurrenceFilterScript(intervals);
	}
}
