

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class ChiffrementServlet
 */
@MultipartConfig
@WebServlet("/ChiffrementServlet")
public class ChiffrementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChiffrementServlet() {
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
//	                AudioFormat frmt = new AudioFormat(44100, 8, 1, true, true);
//	                ais = new AudioInputStream(filecontent, frmt, 0);
	                // ... (traitement à faire)
	            }
	        }
	    } catch (FileUploadException e) {
	        throw new ServletException("Échec de l'analyse de la requête multipart.", e);
	    }
		
		Chiffrement c = new Chiffrement(is, message);
		
		String filePath = ".wav";
		byte[] audio = c.chiffrerDebut();
		ByteArrayInputStream inStream = new ByteArrayInputStream(audio);
		 // obtains ServletContext
        ServletContext context = getServletContext();
         
        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {        
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
         
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) audio.length);
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "audio.wav");
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
        
        int bytesRead = -1;
         
        while ((bytesRead = inStream.read()) != -1) {
            outStream.write(audio, 0, bytesRead);
        }
         
        inStream.close();
        outStream.close();     
		
	}	
	
	
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	 
	private static String getValue(Part part) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
	    StringBuilder value = new StringBuilder();
	    char[] buffer = new char[1024];
	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
	        value.append(buffer, 0, length);
	    }
	    return value.toString();
	}

}
