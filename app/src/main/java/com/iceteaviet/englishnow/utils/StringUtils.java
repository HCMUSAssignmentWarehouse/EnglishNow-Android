package com.iceteaviet.englishnow.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Genius Doan on 07/01/2018.
 */

public class StringUtils {
    private StringUtils() {

    }

    public static String decodeUrl(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFileNameFromPath(String path) {
        String pathWithParams = path.substring(path.lastIndexOf("/") + 1);
        if (pathWithParams.contains("?"))
            return pathWithParams.substring(0, pathWithParams.indexOf("?"));
        return pathWithParams;
    }
}
