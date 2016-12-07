package com.hncu.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Upload extends HttpServlet {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String savePath = this.getServletContext().getRealPath("/upload"); // ��ȡ�����ļ��ľ���·��
		File file = new File(savePath);
		// ��·���Ƿ���Ч����Ч�򴴽����ļ���
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		// ��Ϣ��ʾ
		String message = "";
		try {
			// 1������һ��DiskFileItemFactory����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 3.����ϴ�������������
			upload.setHeaderEncoding("utf-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			FileItemIterator list = upload.getItemIterator(request);
			while (list.hasNext()) {
				FileItemStream item = list.next();
				String tempname = item.getName() ;
				if(tempname.indexOf("\\") != -1)  
				{  
					String fse = File.separator;
				     tempname = tempname.substring(tempname.lastIndexOf(fse)+1);
				}  
				String path = savePath + File.separator + tempname;// �ļ�����·��
				InputStream in = item.openStream();
				FileOutputStream out = new FileOutputStream(path);//�����ļ������
				byte buffer[] = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}

				in.close();
				out.close();
				message = "�ϴ��ļ��ɹ�";
			}
		} catch (Exception e) {
			message = "�ϴ��ļ�ʧ��";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("uploadMessage.jsp").forward(request, response);
	}

}
