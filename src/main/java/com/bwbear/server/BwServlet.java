package com.bwbear.server;

import java.io.IOException;

/**
 * @author 张照威
 * @date 2020/6/27
 * @mail 865533614@qq.com
 * @describe
 */
public class BwServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String content = "<h1>BwServlet get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>BwServlet post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
