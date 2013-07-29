package org.longrm.forum.util;

public class ServletUtils {

	/**
	 * �������봮��ķǷ��ַ�����֤��ȫ
	 * @param input
	 * @return
	 */
	public static String filter(String input) {
		if(!hasSpecialChars(input))
			return input;
		
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for(int i=0; i<input.length(); i++) {
			c = input.charAt(i);
			// ���ｫ�Ƿ��ַ��滻
			switch(c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}
		}
		return filtered.toString();
	}
	
	private static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if(input!=null && input.length()>0) {
			char c;
			for(int i=0; i<input.length(); i++) {
				c = input.charAt(i);
				switch(c) {
				case '<':
					flag = true;
					break;
				case '>':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * ��ֵ�滻
	 * @param org
	 * @param replace
	 * @return
	 */
	public static String replaceIfMissing(String org, String replace) {
		if( (org==null) || (org.equals("")) ) {
			return replace;
		}
		else
			return org;
	}
}
