package com.bwbear.server.mapper.standard;

import com.bwbear.server.HttpServlet;
import com.bwbear.server.classLoader.MyClassLoader;
import com.bwbear.server.mapper.Context;
import com.bwbear.server.mapper.WebApps;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public class StandardContext extends LifecycleBase implements Context{


    private String path;

    private String docBase;

    private Element contextElement;

    private String rootPath;

    private String name;

    private Map<String,StandardWrapper> wrapperMap = new HashMap<String,StandardWrapper>();


    public String getName() {
        return name;
    }

    public Map<String, StandardWrapper> getWrapperMap() {
        return wrapperMap;
    }

    public StandardContext(String path, String docBase) {
        this.path=path;
        this.docBase=docBase;
    }

    public StandardContext(Element contextElement) {
        this.contextElement=contextElement;
    }

    @Override
    public void init() throws DocumentException {
        this.path = contextElement.attributeValue("path");
        this.docBase = contextElement.attributeValue("docBase");
        this.rootPath = WebApps.WEB_APPS_PATH + docBase+"/classes";
        load();
    }

    @Override
    public void load() throws DocumentException {
        String webPath = this.rootPath+"/web.xml";
        File webXml = new File(webPath);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(webXml);
        Element rootElement = document.getRootElement();
        this.name = rootElement.selectSingleNode("web").selectSingleNode("web-name").getStringValue();
        List<Element> selectNodes = rootElement.selectNodes("//servlet");
        selectNodes.parallelStream().forEach(select->{
            String servletName = select.selectSingleNode("servlet-name").getStringValue();
            String servletClass = select.selectSingleNode("servlet-class").getStringValue();
            Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
            String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
            try {
                MyClassLoader myClassLoader = new MyClassLoader(rootPath);
                Class<?> Log = myClassLoader.loadClass(servletClass);
                HttpServlet httpServlet = (HttpServlet)Log.getDeclaredConstructor().newInstance();
                wrapperMap.put(urlPattern, new StandardWrapper(httpServlet));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }
}
