/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.util;

import java.io.File;
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
public class AppConfig implements Serializable {

    private static final long serialVersionUID = -8269216861708175445L;
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String BASE_NAME = "config.config";
    public static final String FILE_STORE_PATH_PROPERTY = "filestore.path";
    public static final String RTMP_URL = "rtmp.url";
    public static final String RTSP_URL = "rtsp.url";
    public static final String HTTP_M3U8_URL = "http.m3u8.url";
    public static final String HTTP_HDS_URL = "http.hds.url";

    public static ResourceBundle bundle = null;

    static {
        try {
            loadBundle();
        } catch (MissingResourceException | NullPointerException e) {
            logger.warn("[AppConfig] Error when try loading the '{}' bundle.", BASE_NAME);
        }
    }

    public static void loadBundle() {
        bundle = ResourceBundle.getBundle(BASE_NAME);
    }

    public static String getFileStorePath() {
        String path = null;

        if (bundle != null) {
            try {
                path = bundle.getString(FILE_STORE_PATH_PROPERTY);
            } catch (MissingResourceException e) {
                logger.warn("Cannot fint resource with key {}", FILE_STORE_PATH_PROPERTY);
            }
        }

        if (path == null) {
            path = getDefaultPath();
        }

        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }

        return path;
    }

    private static String getDefaultPath() {
        StringBuilder sb = new StringBuilder(System.getProperty("user.home"));
        sb.append(File.separator);
        sb.append(".wapFiles").append(File.separator);
        return sb.toString();
    }

    public static String getConfig(String key) {
        return bundle.getString(key);
    }

}
