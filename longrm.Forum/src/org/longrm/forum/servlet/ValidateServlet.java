package org.longrm.forum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.ValidateUtils;

public class ValidateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		validate(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		validate(request, response);
	}

	private void validate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get name or email
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		// get connection
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String result = "failure";

		try {
			// check name
			if (name != null) {
				result = ValidateUtils.checkName(name);
				if(result!="success")
					return;
				
				// check whether this name is used
				sql = "select * from fm_user where name=?";
				conn = DAOFactory.getInstance().getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				rs = pstmt.executeQuery();
				if (rs.next())
					result = "isUsed";
				else
					result = "success";
			}

			// check email
			if (email != null) {
				sql = "select * from fm_user where email=?";
				conn = DAOFactory.getInstance().getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();
				if (rs.next())
					result = "isUsed";
				else
					result = "success";
			}
		} catch (SQLException e) {
			result = "failure";
			e.printStackTrace();
		} catch (Exception exc) {
			result = "failure";
			exc.printStackTrace();			
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);			
			// return result
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
		}
	}
	
}
