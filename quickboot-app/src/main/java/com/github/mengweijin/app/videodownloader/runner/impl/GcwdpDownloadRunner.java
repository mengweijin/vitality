package com.github.mengweijin.app.videodownloader.runner.impl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.mengweijin.app.videodownloader.entity.Task;
import com.github.mengweijin.app.videodownloader.runner.BaseDownloadRunner;
import com.github.mengweijin.app.videodownloader.runner.enums.EWebSite;
import com.github.mengweijin.quickboot.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;

/**
 * 广场舞地盘(http://www.gcwdp.com/)
 * 1. 用播视网视频源的URL
 * 2. 其他视频源（打开原网页可以右键另存为保存视频）
 * @author mengweijin
 */
@Slf4j
public class GcwdpDownloadRunner extends BaseDownloadRunner {

    public GcwdpDownloadRunner(Task task) {
        this.setTask(task);
    }

    @Override
    public File execute() {
        String url = getBoosjIndexUrl();

        if(url != null && url.contains(EWebSite.BOOSJ.getWebSite())){
            // change current task url to boosj url
            this.getTask().setUrl(url);
            return new BoosjDownloadRunner(this.getTask()).execute();
        } else {
            throw new ServerException("不支持当前视频源，请尝试打开原网页在视频上方点击右键另存为下载视频。");
        }
    }

    private String getBoosjIndexUrl() {
        // 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // 当JS执行出错的时候是否抛出异常, 这里选择不需要
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            // 当HTTP的状态非200时是否抛出异常, 这里选择不需要
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            // 是否启用CSS, 因为不需要展现页面, 所以不需要启用
            webClient.getOptions().setCssEnabled(false);

            HtmlPage page = webClient.getPage(this.getTask().getUrl());

            String html = page.asXml();
            Document document = Jsoup.parse(html);
            Element iframeElement = document.selectFirst("#player iframe");

            String url = iframeElement.attr("src");
            log.info("Get boosj index url ={}", url);
            return url;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(e);
        }
    }
}
