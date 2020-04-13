package com.mengweijin.video.downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mengweijin
 */
@SpringBootApplication(scanBasePackages = {"com.mengweijin"})
public class VideoDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoDownloaderApplication.class, args);
	}

}
