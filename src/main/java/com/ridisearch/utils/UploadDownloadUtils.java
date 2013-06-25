package com.ridisearch.utils;

import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/25/13
 * Time: 3:52 PM
 */
@MultipartConfig
public class UploadDownloadUtils {
//    public static void processRequest(HttpServletRequest request,
//                                  HttpServletResponse response, String path,final Part filePart)
//            throws ServletException, IOException {
//
//        response.setContentType("text/html;charset=UTF-8");
//
//        // Create path components to save the file
//
//        final String fileName   = getFileName(filePart);
//
//        OutputStream out = null;
//        InputStream filecontent = null;
//        final PrintWriter writer = response.getWriter();
//
//        try {
//            out = new FileOutputStream(new File(path + File.separator
//                    + fileName));
//            filecontent = filePart.getInputStream();
//
//            int read = 0;
//            final byte[] bytes = new byte[5000000];
//
//            while ((read = filecontent.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            writer.println("New file " + fileName + " created at " + path);
//            System.out.println("File{0}being uploaded to {1}"+ new Object[]{fileName, path});
//        } catch (FileNotFoundException fne) {
//            writer.println("You either did not specify a file to upload or are "
//                    + "trying to upload a file to a protected or nonexistent "
//                    + "location.");
//            writer.println("<br/> ERROR: " + fne.getMessage());
//
//            System.out.println("Problems during file upload. Error: {0}" + new Object[]{fne.getMessage()});
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//            if (filecontent != null) {
//                filecontent.close();
//            }
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }
//
//    private static String getFileName(final Part part) {
//        final String partHeader = part.getHeader("content-disposition");
//        System.out.println("Part Header = {0}" + partHeader);
//        for (String content : part.getHeader("content-disposition").split(";")) {
//            if (content.trim().startsWith("filename")) {
//                return content.substring(
//                        content.indexOf('=') + 1).trim().replace("\"", "");
//            }
//        }
//        return null;
//    }
//
    public static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {

            }
        }
    }

//    public static String uploadFile(HttpServletRequest req, String filePath) throws Exception {
//        User user = (User) request.getSession().getAttribute("user");
//        String fileName = null;
//        try{
//            HttpServletRequestWrapper request = new HttpServletRequestWrapper(req);
//            for (Part part : request.getParts()) {
//                InputStream is = request.getPart(part.getName()).getInputStream();
//                String temp = ((ApplicationPart)part).getFilename();
//                if(temp==null || temp.trim().length()==0){
//                    return fileName;
//                }
////                fileName = MD5helper.getMD5Hash(String.valueOf(System.currentTimeMillis())) + "." + FileUtility.getExtensionFile(new File(temp));
//                fileName = temp+System.currentTimeMillis();
//                File path = new File(filePath);
//
//                if (!path.exists()) {
//                    path.mkdirs();
//                }
//                File uploadedFile = new File(path + File.separator + fileName);
//                FileOutputStream os = new FileOutputStream(uploadedFile);
////                FileUtility.copyFile(is, os);
////                os.write(is.read());
//                IOUtils.copy(is, os);
//                System.out.println("File Successfully uploaded to " + path);
//            }
//        }catch(Exception e){
//        }
//        return fileName;
//    }


    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }


    public static String getUploadPath(String access) {
        String uploadPath = "";
        if ("private".equals(access)) {
            uploadPath = Constants.privateIndexPath;
        } else if ("public".equals(access)) {
            uploadPath = Constants.publicIndexPath;
        }
        return uploadPath;
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
    public static String MD5(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }


}
