package com.ule.uhj.sldProxy.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassUtil {
    private static Log log = LogFactory.getLog(ClassUtil.class);

    public static List<String> getClassNameByPackage(String packageName) {
        List<String> classNameList = new ArrayList<String>();
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = URLDecoder.decode(url.getPath().replaceAll("%20", ""), Charset.defaultCharset().name());
                        addClass(classNameList, packagePath);
                    }
                }
            }
        } catch (Exception e) {
            log.error("get classname error：", e);
        }
        return classNameList;
    }

    private static void addClass(List<String> classNameList, String packagePath) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                fileName = fileName.substring(0, fileName.indexOf("."));
                classNameList.add(fileName);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + File.separator + subPackagePath;
                }
                addClass(classNameList, subPackagePath);
            }
        }
    }
}
