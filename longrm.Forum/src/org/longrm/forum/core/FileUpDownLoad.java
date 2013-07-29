package org.longrm.forum.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.Constant;

public class FileUpDownLoad {

	/**
	 * �ϴ��ļ�
	 * @param request
	 * @throws Exception
	 */
	public static void fileUpLoad(HttpServletRequest request) throws Exception {
		// ���request�Ƿ�Ϊ�ļ��ϴ�����
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if(!isMultipart)
//			return;

		/** Create a factory for disk-based file items */
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/** ���������ڴ��С */
		factory.setSizeThreshold(4096);

		/** ����һ���µ��ļ��ϴ����� */
		ServletFileUpload upload = new ServletFileUpload(factory);

		/** �������ϴ�������ļ��Ĵ�С:10MB */
		upload.setSizeMax(10 * 1024 * 1024);
		
		/** Parse the request */
		List fileItems = null;
		fileItems = upload.parseRequest(request);
		
		// ��ʼ��ȡ�ϴ���Ϣ
		Iterator iter = fileItems.iterator();
				
		String action="";
		String topic_id="";
		String retopic_id="";
		ArrayList<String> fileNameList = new ArrayList<String>();
		ArrayList<Long> fileSizeList = new ArrayList<Long>();
		// ���δ���ÿ���ϴ����ļ�
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			
			// �ж��Ǻ��ֲ���
			if(item.isFormField()) {
				if(item.getFieldName().equals("action"))
					action = item.getString();
				else if(item.getFieldName().equals("topic_id"))
					topic_id = item.getString();
				else if(item.getFieldName().equals("retopic_id"))
					retopic_id = item.getString();
			}
			// �ļ����ϴ��ļ�
			else if (!item.isFormField()) {
				String name = item.getName();
				String fileName = name.substring(name.lastIndexOf("\\")+1);
				long fileSize = item.getSize();
				if(fileSize==0)
					continue;
				
				File f = new File(request.getRealPath(Constant.DEFAULT_UPLOAD + fileName));
				while(f.exists()) {
					int i = fileName.lastIndexOf(".");
					fileName = fileName.substring(0, i) + "~" + fileName.substring(i);
					f = new File(request.getRealPath(Constant.DEFAULT_UPLOAD + fileName));
				}
				item.write(f);
				
				fileNameList.add(fileName);
				fileSizeList.add(fileSize);
			}
		}
		
		// û���ļ��ϴ���ֱ���˳�
		if(fileNameList.size()==0)
			return;
		
		Connection conn = DAOFactory.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {			
			String sql = "";
			if(action.equals("null") || action.equals("new") || action.equals("edit_topic"))
				sql = "insert into topic_file values(?, ?, ?, ?, ?)";
			else
				sql = "insert into re_topic_file values(?, ?, ?, ?, ?)";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			// ����ֵ
			String file_id="";
			String tid="";
			if(action.equals("null") || action.equals("new")) {
				file_id = DBTool.getMaxId("topic_file", "id");
				tid = DBTool.getNextId("topic", "id");
			}
			else if(action.equals("edit_topic")) {
				file_id = DBTool.getMaxId("topic_file", "id");
				tid = topic_id;
			}
			else if(action.equals("reply")) {
				file_id = DBTool.getMaxId("re_topic_file", "id");
				tid = DBTool.getNextId("re_topic", "id");
			}
			else if(action.equals("edit_retopic")) {
				file_id = DBTool.getMaxId("re_topic_file", "id");
				tid = retopic_id;
			}
			
			// ��������Ϣд��
			for(int i=0; i<fileNameList.size(); i++) {
				file_id = DBTool.produceNextId(file_id);
				pstmt.setString(1, file_id);
				pstmt.setString(2, tid);
				pstmt.setString(3, fileNameList.get(i));
				pstmt.setLong(4, fileSizeList.get(i));
				pstmt.setInt(5, 0);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch(SQLException e) {
			conn.rollback();
			throw e;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}

	}
	
	/**
	 * �����ļ�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void fileDownLoad(HttpServletRequest request, HttpServletResponse response) {
		response.reset();
		// ��ȡ�ļ�����·��
		String filename = request.getParameter("filename");
		String iso_filename="";
		String path = request.getRealPath(Constant.DEFAULT_UPLOAD + filename);
		response.setContentType("unknown");
		// ���ı��룬֧���������ļ�����
		try {
			iso_filename = new String(filename.getBytes(),"ISO8859_1");
		}
		catch(UnsupportedEncodingException ue) {
			ue.printStackTrace();
		}
		response.addHeader("Content-Disposition", "filename=\"" + iso_filename + "\"");
		try {
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(path);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			fis.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// �����ļ����ش���
		Connection conn = DAOFactory.getInstance().getConnection();
		PreparedStatement pstmt = null;
		try {
			String tab = request.getParameter("topic")==null?"re_topic_file":"topic_file";
			String sql = "update " + tab + " set click=click+1 where filename=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filename);
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
