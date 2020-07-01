package com.bwbear.server.mapper.standard;

import com.bwbear.server.mapper.Lifecycle;
import org.dom4j.DocumentException;

/**
 * @author 张照威
 * @date 2020/6/30
 * @mail 865533614@qq.com
 * @describe
 */
public abstract class LifecycleBase implements Lifecycle {

    @Override
    public void run() throws Exception {
        init();
        load();
    }

    @Override
    public void load() throws Exception {

    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
