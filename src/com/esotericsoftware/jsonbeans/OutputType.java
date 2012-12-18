
package com.esotericsoftware.jsonbeans;

import java.util.regex.Pattern;

public enum OutputType {
	/** Normal JSON, with all its quotes. */
	json,
	/** Like JSON, but names are only quoted if necessary. */
	javascript,
	/** Like JSON, but names and values are only quoted if necessary. */
	minimal;

	static private Pattern javascriptPattern = Pattern.compile("[a-zA-Z_$][a-zA-Z_$0-9]*");
	static private Pattern minimalPattern = Pattern.compile("[a-zA-Z_$][^:}\\], ]*");

	public String quoteValue (String value) {
		value = value.replace("\\", "\\\\");
		if (this == OutputType.minimal && !value.equals("true") && !value.equals("false") && !value.equals("null")
			&& minimalPattern.matcher(value).matches()) return value;
		return '"' + value.replace("\"", "\\\"") + '"';
	}

	public String quoteName (String value) {
		value = value.replace("\\", "\\\\");
		switch (this) {
		case minimal:
			if (minimalPattern.matcher(value).matches()) return value;
			return '"' + value.replace("\"", "\\\"") + '"';
		case javascript:
			if (javascriptPattern.matcher(value).matches()) return value;
			return '"' + value.replace("\"", "\\\"") + '"';
		default:
			return '"' + value.replace("\"", "\\\"") + '"';
		}
	}
}