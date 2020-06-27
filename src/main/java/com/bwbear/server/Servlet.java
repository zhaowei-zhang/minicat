package com.bwbear.server;

/**
 * @author 张照威
 * @date 2020/6/27
 * @mail 865533614@qq.com
 * @describe
 */
public interface Servlet {
    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request,Response response) throws Exception;
}
