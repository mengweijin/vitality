package com.github.mengweijin.app.videodownloader.runner.enums;

/**
 * @author mengweijin
 */

public enum EWebSite {

    BOOSJ("www.boosj.com"),
    GCWDJ("www.gcwdp.com");

    private String webSite;

    EWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getWebSite(){
        return this.webSite;
    }
}
