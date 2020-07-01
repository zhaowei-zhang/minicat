package com.bwbear.server.mapper.standard;

import com.bwbear.server.HttpServlet;
import com.bwbear.server.mapper.Wrapper;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public class StandardWrapper extends LifecycleBase implements Wrapper {

    private HttpServlet httpServlet;

    public HttpServlet getHttpServlet() {
        return httpServlet;
    }

    public StandardWrapper(HttpServlet httpServlet){
        this.httpServlet=httpServlet;
    }

    @Override
    public void init() throws Exception {

    }
}
