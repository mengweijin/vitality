package com.github.mengweijin.quickboot.framework.util.http;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Meng Wei Jin
 * @description
 **/
public class JsoupUtils {

    /**
     * 返回Document对象，Document.select()可兼容jquery和css选择器
     * @param url
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static Document getDocument(String url) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        return getDocument(url, null, null, null);
    }

    /**
     * 返回Document对象，Document.select()可兼容jquery和css选择器
     * @param url
     * @param timeout 超时时间 milliseconds
     * @param proxyIp
     * @param proxyPort
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static Document getDocument(String url, Integer timeout, String proxyIp, Integer proxyPort) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        Connection connection = Jsoup.connect(url)
                // 模拟浏览器访问
                .userAgent(HttpConnection.DEFAULT_UA)
                // 信任所有证书
                .sslSocketFactory(HttpsTrustAnyManager.getTrustAnySSLSocketFactory());

        if(timeout != null){
            connection.timeout(timeout);
        }
        if(proxyIp != null && proxyPort != null){
            // 使用代理ip访问 Represents proxy for high level protocols such as HTTP or FTP.
            connection.proxy(proxyIp, proxyPort);
        }
        return connection.get();
    }

    /**
     * JSOUP 返回JSON数据
     * @param url
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public JSONObject getJSONObject(String url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        return getJSONObject(url, null, null, null);
    }

    /**
     * JSOUP 返回JSON数据
     * @param url
     * @param timeout 超时时间 milliseconds
     * @param proxyIp
     * @param proxyPort
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public JSONObject getJSONObject(String url, Integer timeout, String proxyIp, Integer proxyPort) throws IOException, NoSuchAlgorithmException, KeyManagementException {

        Connection connection = Jsoup.connect(url)
                // 模拟浏览器访问
                .userAgent(HttpConnection.DEFAULT_UA)
                // 信任所有证书
                .sslSocketFactory(HttpsTrustAnyManager.getTrustAnySSLSocketFactory())
                // 忽略请求类型
                .ignoreContentType(true);

        if(timeout != null){
            connection.timeout(timeout);
        }
        if(proxyIp != null && proxyPort != null){
            // 使用代理ip访问 Represents proxy for high level protocols such as HTTP or FTP.
            connection.proxy(proxyIp, proxyPort);
        }

        // 用get 去执行的话，返回来是一个 HTML  页面包裹的 JSON  ，处理起来稍微有点费劲。
        Connection.Response response = connection.execute();

        return JSONObject.parseObject(response.body());
    }



}
