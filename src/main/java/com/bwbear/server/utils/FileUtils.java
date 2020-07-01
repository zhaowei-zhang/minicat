package com.bwbear.server.utils;

import com.bwbear.server.mapper.standard.StandardContext;
import com.bwbear.server.mapper.standard.StandardHost;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张照威
 * @date 2020/6/30
 * @mail 865533614@qq.com
 * @describe
 */
public class FileUtils {

    private static File[] queryListFiles(String mainPath){
        File file = new File(mainPath);
        File[] tempList = file.listFiles();
        return tempList;
    }

    private static File[] queryListFiles(File file){
        File[] tempList = file.listFiles();
        return tempList;
    }

    public static List<File> queryWebXml(String webAppsPath){
        File[] contextFiles = queryListFiles(webAppsPath);
        List<File> webXmlFile = new ArrayList<>();
        for (File contextFile : contextFiles) {
            File[] contextBody = queryListFiles(contextFile);
            for (File file : contextBody) {
                if(file.getName().equals("classes")){
                    File[] classess = file.listFiles();
                    for (File classes : classess) {
                        if(classes.getName().equals("web.xml")){
                            webXmlFile.add(classes);
                        }
                    }
                }
            }
        }
        return webXmlFile;
    }



    public static List<StandardHost> queryConfig(InputStream resourceAsStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(resourceAsStream);
        Element rootElement = document.getRootElement();
        List<Element> hostsElement = rootElement.selectNodes("Host");
        List<StandardHost> standardHostList = new ArrayList<>();
        hostsElement.parallelStream().forEach(hostElement->{
            StandardHost standardHost = new StandardHost(hostElement);
            standardHostList.add(standardHost);
        });
        return standardHostList;
    }
}
