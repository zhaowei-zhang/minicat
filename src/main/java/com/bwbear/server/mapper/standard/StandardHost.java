package com.bwbear.server.mapper.standard;

import com.bwbear.server.mapper.Host;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public class StandardHost extends LifecycleBase implements Host {

    private List<StandardContext> contexts = new ArrayList<>();

    private List<String> names = new ArrayList<>();

    private Element hostElement;

    public List<StandardContext> getContexts() {
        return contexts;
    }

    public List<String> getNames() {
        return names;
    }

    public StandardHost(Element hostElement) {
        this.hostElement=hostElement;
    }

    @Override
    public void init() {
        String name = hostElement.attributeValue("name");
        setHostName(name);
        List<Element> contextsElement = hostElement.selectNodes("Context");
        contextsElement.parallelStream().forEach(contextElement->{
            StandardContext standardContext = new StandardContext(contextElement);
            try {
                standardContext.init();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            this.contexts.add(standardContext);
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

    private void setHostName(String name) {
        names.add(name) ;
        if("localhost".equals(name)){
            names.add("127.0.0.1");
        }
    }

    public void addContext(StandardContext standardContext) {
        contexts.add(standardContext);
    }
}
