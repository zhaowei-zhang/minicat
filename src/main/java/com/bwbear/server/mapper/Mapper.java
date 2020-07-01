package com.bwbear.server.mapper;

import com.bwbear.server.HttpServlet;

/**
 * @author 张照威
 * @date 2020/6/29
 * @mail 865533614@qq.com
 * @describe
 */
public interface Mapper extends Lifecycle {
    HttpServlet handleRequest(String url, String ipOrDomain, String port);
}
