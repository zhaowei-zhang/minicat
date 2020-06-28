package com.bwbear.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张照威
 * @date 2020/6/27
 * @mail 865533614@qq.com
 * @describe
 */
public class MiniCat {

    /**定义socket监听的端口号*/
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private Map<String,HttpServlet> servletMap = new HashMap<String,HttpServlet>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < selectNodes.size(); i++) {
                Element element =  selectNodes.get(i);
                // <servlet-name>lagou</servlet-name>
                Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletnameElement.getStringValue();
                // <servlet-class>server.LagouServlet</servlet-class>
                Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletclassElement.getStringValue();


                // 根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                // /lagou
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动初始化
     */
    public void init() throws Exception {
        //加载Servlet
        loadServlet();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>Minicat start on port：" + port);

        //1.0
//        while(true) {
//            Socket socket = serverSocket.accept();
//            // 有了socket，接收到请求，获取输出流
//            OutputStream outputStream = socket.getOutputStream();
//            String data = "Hello Minicat!";
//            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
//            outputStream.write(responseText.getBytes());
//            socket.close();
//        }
            //2.0
//        while(true) {
//            Socket socket = serverSocket.accept();
//            InputStream inputStream = socket.getInputStream();
//            // 封装Request对象和Response对象
//            Request request = new Request(inputStream);
//            Response response = new Response(socket.getOutputStream());
//            response.outputHtml(request.getUrl());
//            socket.close();
//        }
        //3.0
//        while(true) {
//            Socket socket = serverSocket.accept();
//            InputStream inputStream = socket.getInputStream();
//
//            // 封装Request对象和Response对象
//            Request request = new Request(inputStream);
//            Response response = new Response(socket.getOutputStream());
//
//            // 静态资源处理
//            if(servletMap.get(request.getUrl()) == null) {
//                response.outputHtml(request.getUrl());
//            }else{
//                // 动态资源servlet请求
//                HttpServlet httpServlet = servletMap.get(request.getUrl());
//                httpServlet.service(request,response);
//            }
//            socket.close();
//        }
        while(true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,servletMap);
            requestProcessor.start();
        }

    }


    /**
     * 入口
     * @param args
     */
    public static void main(String[] args) {
        MiniCat miniCat = new MiniCat();
        try {
            miniCat.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
