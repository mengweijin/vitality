package com.github.mengweijin.vitality.framework.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.IoUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author mengweijin
 * @date 2023/5/13
 */
@Slf4j
public class Ip2regionUtils {

    /**
     *
     * @param ip ip
     * @return 数据格式：国家|区域|省份|城市|ISP。例如：中国|0|广东省|广州市|联通
     */
    public static String search(String ip) {
        if(ip == null) {
            return null;
        }
        if("0:0:0:0:0:0:0:1".equals(ip.trim())) {
            return "0|0|0|内网IP|内网IP";
        }

        InputStream in = Ip2regionUtils.class.getClassLoader().getResourceAsStream("ip2region.xdb");
        Searcher searcher = null;
        try(in) {
            searcher = Searcher.newWithBuffer(IoUtil.readBytes(in));
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("Search ip to region error! ip=" + ip);
            return null;
        } finally {
            if(searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    public static IpRegion searchIpRegion(String ip) {
        String regionString = search(ip);
        if(regionString == null) {
            return null;
        }

        try {
            String[] split = regionString.split("\\|");
            IpRegion region = new IpRegion();
            region.setCountry(split[0]);
            region.setRegion(split[1]);
            region.setProvince(split[2]);
            region.setCity(split[3]);
            region.setIsp(split[4]);
            return region;
        } catch (RuntimeException e) {
            log.error("Split ip region string error! regionString=" + regionString);
        }

        return null;
    }

    @Data
    static class IpRegion {
        /** 国家 */
        private String country;
        /** 地区 */
        private String region;
        /** 省 */
        private String province;
        /** 市 */
        private String city;
        /** 运营商 */
        private String isp;
    }

}
