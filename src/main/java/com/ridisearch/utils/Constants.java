package com.ridisearch.utils;

import java.io.File;

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

    public static String publicFilePath   = File.separator + "ridisearch" + File.separator + "file" + File.separator + "public";
    public static String privateFilePath  = File.separator + "ridisearch" + File.separator + "file" + File.separator + "private";
    public static String publicIndexPath  = File.separator + "ridisearch" + File.separator + "index"+ File.separator + "public";
    public static String privateIndexPath = File.separator + "ridisearch" + File.separator + "index"+ File.separator + "private";


    public static long MAX_UPLOAD_SIZE = 14000000;
}
