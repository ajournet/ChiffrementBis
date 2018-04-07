

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class DechiffrementServlet
 */
@WebServlet("/DechiffrementServlet")
public class DechiffrementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DechiffrementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		String message = "";
		
		try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                // Traiter les champs classiques ici (input type="text|radio|checkbox|etc", select, etc).
	                String fieldname = item.getFieldName();
	                String fieldvalue = item.getString();
	                message = fieldvalue;
	            } else {
	                // Traiter les champs de type fichier (input type="file").
	                String fieldname = item.getFieldName();
	                String filename = FilenameUtils.getName(item.getName());
	                InputStream filecontent = item.getInputStream();
	                is=filecontent;
	            }
	        }
	    } catch (FileUploadException e) {
	        throw new ServletException("Échec de l'analyse de la requête multipart.", e);
	    }
		
		Chiffrement c = new Chiffrement(is);
		
		message = c.dechiffrerDebut();
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("resultDechiffre.jsp").forward(request, response);
		
	}

}
