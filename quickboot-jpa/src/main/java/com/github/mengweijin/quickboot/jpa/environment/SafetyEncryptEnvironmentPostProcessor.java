package com.github.mengweijin.quickboot.jpa.environment;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 安全加密处理器 参考自：com.baomidou.mybatisplus.autoconfigure.SafetyEncryptProcessor
 * <p>
 * 1. 生成 16 位随机 AES 密钥，在启动 jar 时把下面生成的 key 通过命令行参数 --quickboot.key 传递到应用程序中
 * SecretKey key = SecureUtil.generateKey("AES");
 * <p>
 * 2. 密钥加密：配置在 application.yaml 中的加密值
 * String result = SecureUtil.aes(key).encrypt("password");
 * <p>
 * 3. YML 配置：加密配置 quickboot: 开头紧接加密内容（ 非数据库配置专用 YML 中其它配置也是可以使用的 ）
 * spring:
 * datasource:
 * url: quickboot:qRhvCwF4GOqjessEB3G+a5okP+uXXr96wcucn2Pev6Bf1oEMZ1gVpPPhdDmjQqoM
 * password: quickboot:Hzy5iliJbwDHhjLs1L0j6w==
 * username: quickboot:Xb+EgsyuYRXw7U7sBJjBpA==
 *
 * @author mengweijin
 */
public class SafetyEncryptEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 命令行中获取密钥  --quickboot.key
        String mpwKey = null;
        for (PropertySource<?> ps : environment.getPropertySources()) {
            if (ps instanceof SimpleCommandLinePropertySource) {
                SimpleCommandLinePropertySource source = (SimpleCommandLinePropertySource) ps;
                mpwKey = source.getProperty("quickboot.key");
                break;
            }
        }
        // 处理加密内容
        if (StrUtil.isNotBlank(mpwKey)) {
            HashMap<String, Object> map = new HashMap<>();
            for (PropertySource<?> ps : environment.getPropertySources()) {
                if (ps instanceof OriginTrackedMapPropertySource) {
                    OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) ps;
                    for (String name : source.getPropertyNames()) {
                        Object value = source.getProperty(name);
                        if (value instanceof String) {
                            String str = (String) value;
                            if (str.startsWith("quickboot:")) {
                                map.put(name, SecureUtil.aes(mpwKey.getBytes(StandardCharsets.UTF_8)).decrypt(str.substring(4)));
                            }
                        }
                    }
                }
            }
            // 将解密的数据放入环境变量，并处于第一优先级上
            if (CollectionUtil.isNotEmpty(map)) {
                environment.getPropertySources().addFirst(new MapPropertySource("custom-encrypt", map));
            }
        }
    }
}
