package com.ridisearch.utils;

import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
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

    private static final int DEFAULT_BUFFER_SIZE = 20480000;

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

    public static void download(Map<String, String> itemMap, byte[] itemBytes, HttpServletResponse res) throws SQLException, IOException {
        String contentType  = itemMap.get("itemType");
        String fileName     = itemMap.get("itemName");

        Blob blob = new SerialBlob(itemBytes);
        BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream(),DEFAULT_BUFFER_SIZE);
        File file = new File(fileName);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        res.reset();
        res.setBufferSize(DEFAULT_BUFFER_SIZE);
        res.setContentType(contentType);
        res.setHeader("Content-Length", String.valueOf(blob.length()));
        res.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        BufferedOutputStream fos = new BufferedOutputStream(res.getOutputStream(), DEFAULT_BUFFER_SIZE);
        // you can set the size of the buffer
        int length = 0;
        while((length = is.read(buffer))!=-1) {
            fos.write(buffer, 0, length);
        }

        fos.flush();
        fos.close();
        is.close();
//        blob.free();

    }


}
