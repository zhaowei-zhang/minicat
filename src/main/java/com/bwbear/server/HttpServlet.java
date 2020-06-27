package com.bwbear.server;

/**
 * @author 张照威
 * @date 2020/6/27
 * @mail 865533614@qq.com
 * @describe
 */
public abstract class HttpServlet implements Servlet{


    public abstract void doGet(Request request,Response response);

    public abstract void doPost(Request request,Response response);


    @Override
    public void service(Request request, Response response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
}