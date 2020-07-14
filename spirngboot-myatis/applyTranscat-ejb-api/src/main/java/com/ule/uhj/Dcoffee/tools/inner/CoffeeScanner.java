package com.ule.uhj.Dcoffee.tools.inner;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.core.application.DcoffeeApplicationContext;
import com.ule.uhj.Dcoffee.core.builder.AbstractBuilder;
import com.ule.uhj.Dcoffee.core.application.ApplicationContextHolder;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhengxin on 2018/3/14.
 */
@Dcoffee(name = "CoffeeScanner")
public class CoffeeScanner implements ApplicationContextHolder{
	private static Log log = LogFactory.getLog(CoffeeScanner.class);

    private static String classPath;
//    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static List<Class> classList = new ArrayList<Class>();
//    private static Boolean isScaned = false;
    private static DcoffeeApplicationContext dcoffeeApplicationContext;

    static {
        classPath = CoffeeScanner.class.getResource("/").getPath() + CoffeeRecipe.packageName.replace(".", File.separator);
        scan();
    }

    private CoffeeScanner(){
        super();
    }

    public static void scan(){
//    	log.info("scan begin...."+isScaned);
//        if(isScaned){
//            return;
//        }
    	log.info("scan..reentrantLock lock..");
//        reentrantLock.lock();
//        if(isScaned){
//            reentrantLock.unlock();
//            return;
//        }
        log.info("scan..reentrantLock lock..classPath"+classPath);
        File targetDirector = new File(classPath);
        Set<Class<?>> classes = getClasses("com.ule.uhj.Dcoffee");
        for(Class classz : classes){
            classList.add(classz);
        }
        //DfsScan(targetDirector);
        log.info("scan..reentrantLock updateCoffeeMap..");
        updateCoffeeMap();
//        isScaned = true;
//        reentrantLock.unlock();
        log.info("scan..reentrantLock end..");
    }

    private static void DfsScan(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File currentFile : files){
                DfsScan(currentFile);
            }
        }else{
            if(file.getName().endsWith(CoffeeRecipe.Class.nameEnd)){
                try{
                    classList.add(loadClass(file));
                }catch (Exception e){
                }
            }
        }
    }

    private static Class loadClass(File file) throws ClassNotFoundException{
        String filePath = file.getPath();
        filePath = filePath.substring(filePath.lastIndexOf("com\\"),filePath.lastIndexOf(".class")).replace("\\",".");
        return Class.forName(filePath);
    }

    private static void updateCoffeeMap(){
        for(Class classz : classList){
            Dcoffee DcoffeeAnnotation = (Dcoffee)classz.getAnnotation(Dcoffee.class);
            if(DcoffeeAnnotation==null){
                continue;
            }
            try{
                String DcoffeeName = DcoffeeAnnotation.name();
                Object instance = DcoffeeAnnotation.scope().equals(CoffeeRecipe.SINGLE) ? classz.newInstance() : null;
                dcoffeeApplicationContext.addBeans(DcoffeeName, classz, instance);
            }catch (Exception e){
            }
        }
    }

    @Override
    public void setApplicationContext(DcoffeeApplicationContext context) {
        dcoffeeApplicationContext = context;
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1, name
                                                        .length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class
                                                    .forName(packageName + '.'
                                                            + className));
                                        } catch (ClassNotFoundException e) {
                                            // log
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
//                                            e.printStackTrace();
                                        	log.error("添加用户自定义视图类错误 找不到此类的.class文件",e);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                         log.error("在扫描用户定义视图时从jar包获取文件出错",e);
//                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
            log.error("IOException",e);
        }

        return classes;
    }
    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    log.error("添加用户自定义视图类错误 找不到此类的.class文件",e);
//                    e.printStackTrace();
                }
            }
        }
    }
}