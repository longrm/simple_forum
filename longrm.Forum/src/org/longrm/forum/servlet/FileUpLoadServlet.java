package org.longrm.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.core.FileUpDownLoad;

public class FileUpLoadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			FileUpDownLoad.fileUpLoad(request);
			response.getWriter().println("<script>parent.callback('success')</script>");
		}
		catch(Exception e) {
			e.printStackTrace();
			response.getWriter().println("<script>parent.callback('failed')</script>");
		}
	}

}
