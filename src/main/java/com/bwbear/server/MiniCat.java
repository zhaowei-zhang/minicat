package com.bwbear.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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


    /**
     * 启动初始化
     */
    public void init() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>Minicat start on port：" + port);

        while(true) {
            Socket socket = serverSocket.accept();
            // 有了socket，接收到请求，获取输出流
            OutputStream outputStream = socket.getOutputStream();
            String data = "Hello Minicat!";
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
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
        }
    }
}
