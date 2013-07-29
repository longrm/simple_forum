package org.longrm.forum.core;

import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.longrm.forum.bean.User;

// session set/remove时自动处理事务，用于在线用户统计
public class SessionBinding implements HttpSessionBindingListener {
	ServletContext application = null;

	public SessionBinding(ServletContext application) {
		super();
		if (application == null)
			throw new IllegalArgumentException("Null application is not accept.");
		this.application = application;
	}

	// set时将当前登陆用户的session加入activeSessions
	@SuppressWarnings("unchecked")
	public void valueBound(HttpSessionBindingEvent e) {
		Vector activeSessions = (Vector) application.getAttribute("activeSessions");
		if (activeSessions == null) {
			activeSessions = new Vector();
		}
		User sessionUser = (User) e.getSession().getAttribute("SESSION_USER");
		if (sessionUser != null) {
			activeSessions.add(e.getSession());
		}
		application.setAttribute("activeSessions", activeSessions);
	}

	// remove时从activeSessions里移出登出用户的session
	@SuppressWarnings("unchecked")
	public void valueUnbound(HttpSessionBindingEvent e) {
		Vector activeSessions = (Vector) application.getAttribute("activeSessions");
		if (activeSessions != null) {
			activeSessions.remove(e.getSession());
			application.setAttribute("activeSessions", activeSessions);
		}
	}
}
