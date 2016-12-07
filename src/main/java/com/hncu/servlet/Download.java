package com.hncu.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ҳ���ȡ��Ҫ���ص��ļ���
		String fileName = request.getParameter("fileName");
		// �����ļ����Ͷ�Ӧ��MIME����
		response.setContentType(getServletContext().getMimeType(fileName));
		//����ͷ��Ϣ���ļ�����Ĭ������attachment;fileName
//		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		response.setHeader("Content-disposition","attachment; filename="+new String(fileName.getBytes("gb2312"),"iso8859-1"));
		// ��ȡ������
		String path = getServletContext().getRealPath("/upload/" + fileName);
		InputStream is = new FileInputStream(path);
		OutputStream os = response.getOutputStream();
		int b;
		while ((b = is.read()) != -1) {
			os.write(b);
		}
		is.close();
		os.close();
	}
}
