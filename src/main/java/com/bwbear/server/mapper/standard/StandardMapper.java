package com.bwbear.server.mapper.standard;

import com.bwbear.server.HttpServlet;
import com.bwbear.server.mapper.Host;
import com.bwbear.server.mapper.Mapper;
import com.bwbear.server.utils.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public class StandardMapper extends LifecycleBase implements Mapper{

    private List<StandardHost> hosts = new ArrayList<>();

    public List<StandardHost> getHosts() {
        return hosts;
    }

    @Override
    public void init() throws DocumentException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        List<StandardHost> standardHosts = FileUtils.queryConfig(resourceAsStream);
        standardHosts.parallelStream().forEach(standardHost->{
            hosts.add(standardHost);
        });
    }

    @Override
    public void load() throws DocumentException {
        hosts.parallelStream().forEach(StandardHost::init);
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

    @Override
    public HttpServlet handleRequest(String url, String ipOrDomain, String port) {
        HttpServlet httpServlet = null;
        StandardHost host = null;
        for (StandardHost standardHost : hosts) {
            if(standardHost.getNames().contains(ipOrDomain)){
                host=standardHost;
            }
        }
        if(host!=null){
            StandardContext context = null;
            List<StandardContext> contexts = host.getContexts();
            String[] split = url.split("/");
            StringBuilder servletUrl = new StringBuilder("/");
            if(split.length>1){
                String contextGuessName = split[1];
                if(!"".equals(contextGuessName)){
                    for (StandardContext standardContext : contexts) {
                        if(contextGuessName.equals(standardContext.getPath())){
                            context = standardContext;
                            if(split.length>2){
                                for(int i = 2;i<split.length;i++){
                                    servletUrl.append(split[i]).append("/");
                                }
                            }
                        }
                    }
                }
            }
            if(context == null){
                for (StandardContext standardContext : contexts) {
                    if("/".equals(standardContext.getPath())){
                        context = standardContext;
                        if(split.length>1){
                            for(int i = 1;i<split.length;i++){
                                servletUrl.append(split[i]).append("/");
                            }
                        }
                    }
                }
            }
            if(context!=null){
                servletUrl.deleteCharAt(servletUrl.length()-1);
                Map<String, StandardWrapper> wrapperMap = context.getWrapperMap();
                StandardWrapper standardWrapper = wrapperMap.get(servletUrl.toString());
                httpServlet = standardWrapper.getHttpServlet();
            }
        }
        return httpServlet;
    }
}
