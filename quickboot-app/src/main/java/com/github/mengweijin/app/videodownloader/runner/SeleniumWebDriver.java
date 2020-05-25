package com.github.mengweijin.app.videodownloader.runner;

import com.github.mengweijin.app.videodownloader.runner.model.BoosjVideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * driver downloader from http://chromedriver.storage.googleapis.com/index.html
 * @author mengweijin
 */
@Slf4j
public class SeleniumWebDriver {

    public static void main(String[] args) throws IOException {
        Resource resource = new ClassPathResource("files/webdriver/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", resource.getFile().getAbsolutePath());

        WebDriver driver = new ChromeDriver();
        BoosjVideoInfo boosjVideoInfo = new BoosjVideoInfo();
        try{
            // 最大化窗口
            driver.manage().window().maximize();
            driver.get("http://www.gcwdp.com/zxgcw/144199.html");

            Object html = ((ChromeDriver) driver).executeScript("return document.documentElement.outerHTML");
            Document document = Jsoup.parse(String.valueOf(html));

            System.out.println(boosjVideoInfo);
        } finally {
            driver.quit();
        }

    }
}
