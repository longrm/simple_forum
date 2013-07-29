package org.longrm.forum.util;

public class ValidateUtils {

	public static String checkName(String name) {
		if(name==null)
			return "isNull";
		// check whether this name exists illegal_words
		// ...
		// check whether this name exists upper_word
		for(int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if(c>='A' && c<='Z') {
				return "existUpper";
			}
		}
		return "success";
	}
}
