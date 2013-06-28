package com.ridisearch.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 8:40 AM
 */
public class Constants {
    public final static String IS_LOGGED_IN     = "isLoggedIn";
    public final static String IS_ADMIN         = "isAdmin";
    public final static String ROLE_ADMIN       = "ROLE_ADMIN";
    public final static String ROLE_USER        = "ROLE_USER";
    public final static String USER_ID          = "userId";
    public final static long ROLE_ADMIN_ID      = 1;
    public final static long ROLE_USER_ID       = 2;

    public static String publicFilePath   = File.separator + "ridisearch" + File.separator + "file" + File.separator + "public" + File.separator;
    public static String privateFilePath  = File.separator + "ridisearch" + File.separator + "file" + File.separator + "private" + File.separator;
    public static String publicIndexPath  = File.separator + "ridisearch" + File.separator + "index"+ File.separator + "public" + File.separator;
    public static String privateIndexPath = File.separator + "ridisearch" + File.separator + "index"+ File.separator + "private" + File.separator;


    public static long MAX_UPLOAD_SIZE = 14000000;

    public static final List<String> textList = new ArrayList<String>(Arrays.asList("txt","doc","docx","pdf","xls","xlsx","ppt","sql"));
    public static final List<String> multimediaList = new ArrayList<String>(Arrays.asList("gif","png","jpg","jpeg","mp3"));


    public static String INDEX_PATH = File.separator + "ridisearch" + File.separator + "luceneIndex" + File.separator;
    public static String MYSQL_USER          = "root";
    public static String MYSQL_PASSWORD      = "root";
    public static String MYSQL_DATABASE_NAME = "ridisearch";
    public static String DB_OUTPUT_PATH      = File.separator + "ridisearch" + File.separator + "ridisearch.sql";

    public static String PATH_TO_MYSQL = File.separator + "usr" + File.separator + "local"  + File.separator+ "mysql-5.5.22-osx10.6-x86_64" +
                                         File.separator + "bin" + File.separator;


    public static String DB_PATH = PATH_TO_MYSQL + "mysqldump -u" + MYSQL_USER + " -p" + MYSQL_PASSWORD + " --add-drop-database -B "+ MYSQL_DATABASE_NAME + " -r " + DB_OUTPUT_PATH;


}
