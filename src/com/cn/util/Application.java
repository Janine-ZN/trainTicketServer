package com.cn.util;

import java.net.URL;
import java.util.regex.Pattern;

/**
 * 应用程序
 */
public class Application {

    private static String root = null;
        
    /**
     * 获取应用程序路径
     * 
     * @return String
     */
    public static String getRoot() {
        return root;
    }
    
    static {
        initialize();
    }
    
    /**
     * 初始化
     */
    private static void initialize() {
        
        URL url = com.cn.util.Application.class.getResource("/");        
        root = url.getPath();
        
        root = root.replace('\\','/');
        
        int pos = root.toLowerCase().indexOf("web-inf");
        if (pos > 0)
            root = root.substring(0, pos);
        
        if (root.startsWith("/"))
            root = root.substring(1);
        
        if (root.endsWith("/"))
            root = root.substring(0, root.length() - 1);
        
        String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			root = "/" + root;
		}
		
    }
}
