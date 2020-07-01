package com.bwbear.server;

import com.bwbear.server.mapper.Mapper;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author 张照威
 * @date 2020/7/1
 * @mail 865533614@qq.com
 * @describe 多线程实现
 */
public class RequestProcessor4 extends Thread {

    private Socket socket;

    private Mapper mapper;


    public RequestProcessor4(Socket socket, Mapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            HttpServlet httpServlet = mapper.handleRequest(request.getUrl(),request.getIpOrDomain(),request.getPort());
            httpServlet.service(request,response);
            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}