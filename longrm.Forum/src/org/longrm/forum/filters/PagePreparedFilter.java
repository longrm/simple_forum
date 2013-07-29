
package org.longrm.forum.filters;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.longrm.forum.bean.User;
import org.longrm.forum.business.ForumRequest;
import org.longrm.forum.business.IndexRequest;
import org.longrm.forum.business.MessageRequest;
import org.longrm.forum.business.ProfileRequest;
import org.longrm.forum.business.TopicRequest;
import org.longrm.forum.core.MessageService;
import org.longrm.forum.core.SessionBinding;
import org.longrm.forum.core.UserService;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.CookieUtils;

public class PagePreparedFilter implements Filter {

	/**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    protected FilterConfig filterConfig = null;

    /**
     * Take this filter out of service.
     */
    public void destroy() {
        this.filterConfig = null;
    }

    /**
     * 拦截页面，在加载前取数
     */
    public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
    	// 获取请求的path地址，根据这个地址来转发请求
    	HttpServletRequest hrequest = (HttpServletRequest) request;
    	HttpServletResponse hresponse = (HttpServletResponse) response;
		String path = hrequest.getServletPath();
		
		// 打开页面时自动登陆
		HttpSession session = hrequest.getSession();
		User user = (User) session.getAttribute("SESSION_USER");
		if(user==null) {
			// 获取cookie里的id值
			String user_id = CookieUtils.getCookieValue(hrequest, "chinalong_user_id");
			if(user_id!=null) {
				// 如果之前的session仍存在，则去除
				Vector activeSessions = (Vector) session.getServletContext().getAttribute("activeSessions");
				if(activeSessions!=null) {
					Iterator it = activeSessions.iterator();
					while(it.hasNext()) {
						HttpSession sess = (HttpSession)it.next();
						User tmpUser = (User)sess.getAttribute("SESSION_USER");
						if(user_id.equals(tmpUser.getId())) {
							sess.removeAttribute("BindingNotify");
							break;
						}
					}
				}
				// 加入新的session
				user = UserService.requestUserById(user_id);
				if(user!=null) {
					session.setAttribute("SESSION_USER", user);
					session.setAttribute("BindingNotify",new SessionBinding(session.getServletContext()));
					String ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
					user.setAccess_ip(ip);
					user.setAccess_time(new Timestamp(System.currentTimeMillis()));
					updateAccessInfo(user_id, ip);
				}
			}
		}
		
		// 检查是否有新短信
		if(user!=null) {
			boolean hasNewMessage = MessageService.hasNewMessage(user.getName());
			hrequest.setAttribute("has_new_message", hasNewMessage);
		}
		
		boolean result = true;
		if(path.equals("/index.jsp"))
			result = IndexRequest.viewRequest(hrequest, hresponse);
		else if(path.equals("/viewforum.jsp"))
			result = ForumRequest.viewRequest(hrequest, hresponse);
		else if(path.equals("/viewtopic.jsp"))
			result = TopicRequest.viewRequest(hrequest, hresponse);
		else if(path.equals("/posttopic.jsp"))
			result = TopicRequest.postRequest(hrequest, hresponse);
		else if(path.endsWith("/profile.jsp"))
			result = ProfileRequest.viewRequest(hrequest, hresponse);
		else if(path.endsWith("/message.jsp"))
			result = MessageRequest.viewRequest(hrequest, hresponse);
		
		if(result)
			chain.doFilter(request, response);
	}

     public void init(FilterConfig filterConfig) throws ServletException {
    	 this.filterConfig = filterConfig;
    }
     
     /**
      * 更新访问时间和ip
      * @param user_id
      * @param ip
      */
     private void updateAccessInfo(String user_id, String ip) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				String sql = "update fm_user set access_ip=?, access_time=? where id=?";
				conn = DAOFactory.getInstance().getConnection();
				pstmt = conn.prepareStatement(sql);
				conn = DAOFactory.getInstance().getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ip);
				pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(3, user_id);
				pstmt.execute();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
				DBTool.closeConnection(conn, pstmt, null);
			}
     }

}
