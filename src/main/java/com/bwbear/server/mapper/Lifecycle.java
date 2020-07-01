package com.bwbear.server.mapper;

import org.dom4j.DocumentException;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public interface Lifecycle {

    public void run() throws Exception;
    public void init() throws Exception;
    public void load() throws Exception;
    public void start()throws Exception;
    public void stop()throws Exception;
    public void destroy()throws Exception;

}
