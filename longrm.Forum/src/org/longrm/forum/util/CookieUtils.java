package org.longrm.forum.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, null);
	}

	/**
	 * 获取cookie里对应名字的值
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
	 * 获取指定名字的cookie
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
