package org.longrm.forum.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, null);
	}

	/**
	 * ��ȡcookie���Ӧ���ֵ�ֵ
	 * 
	 * @param request
	 * @param cookieName
	 * @param defaultValue
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,
			String cookieName, String defaultValue) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName()))
					return cookie.getValue();
			}
		}
		return defaultValue;
	}
	
	/**
	 * ��ȡָ�����ֵ�cookie
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName()))
					return cookie;
			}
		}
		return null;
	}
}
