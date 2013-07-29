package org.longrm.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.FileUpDownLoad;

public class FileDownLoadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// δ��½�û�
		if(user==null) {
			String message = "����û�е�½���޷��������Ӹ�����";
			ResultMessage rMessage = new ResultMessage("����", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		FileUpDownLoad.fileDownLoad(request, response);
	}

}
