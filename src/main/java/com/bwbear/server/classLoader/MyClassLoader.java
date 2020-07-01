package com.bwbear.server.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张照威
 * @date 2020/7/1
 * @mail 865533614@qq.com
 * @describe
 */
public class MyClassLoader extends ClassLoader {
    //指定路径
    private String rootPath;
    private Map<String,byte[]> classByteMap = new HashMap<>();

    public MyClassLoader(String rootPath){
        this.rootPath=rootPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = rootPath+"/"+name.replaceAll("\\.", "/")+".class";
        Class log = null;
        byte[] classData = getData(classPath);
        if (classData != null) {
            log = defineClass(name, classData, 0, classData.length);
        }
        return log;
    }


    private byte[] getData(String path) {
        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }

}
