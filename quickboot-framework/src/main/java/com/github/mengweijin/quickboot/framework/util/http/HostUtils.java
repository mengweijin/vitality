package com.github.mengweijin.quickboot.framework.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

/**
 * @author Meng WEi Jin
 * @description
 **/
public class HostUtils {

    /**
     * 目标主机端口是否可连通
     * @param host 主机ip
     * @param port 主机端口
     * @return
     */
    public static boolean hostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException | ClassCastException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 目标主机是否可以连接
     * @param host ip地址
     * @param timeout 超时时间 milliseconds
     * @return
     */
    public static boolean hostReachable(String host, int timeout) {
        try {
            return InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 检测url是否可以连接
     * @param url
     * @return
     */
    public static boolean urlConnectable(String url) {
        int counts = 0;
        HttpURLConnection connection = null;
        while (counts < 3) {
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setUseCaches(false);
                if(200 == connection.getResponseCode()){
                    return true;
                }
            } catch (Exception e) {
                counts++;
                continue;
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
        }

        return false;
    }

}
