package com.bwbear.server;

import com.bwbear.server.mapper.standard.StandardMapper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author 张照威
 * @date 2020/7/1
 * @mail 865533614@qq.com
 * @describe
 */
public class MiniCat4 {

    private int port = 8080;

    private StandardMapper standardMapper = new StandardMapper();

    public void init() throws Exception {
        standardMapper.run();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>Minicat start on port：" + port);
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
        while(true) {
            Socket socket = serverSocket.accept();
            RequestProcessor4 requestProcessor4 = new RequestProcessor4(socket,standardMapper);
            threadPoolExecutor.execute(requestProcessor4);
        }
    }

    public static void main(String[] args) throws Exception {
        MiniCat4 miniCat4 = new MiniCat4();
        miniCat4.init();
    }
}
