package com.github.mengweijin.vitality.framework;

import com.github.mengweijin.vitality.framework.util.AESUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import java.util.HashMap;

/**
 * 安全加密处理器 参考自：com.baomidou.mybatisplus.autoconfigure.SafetyEncryptProcessor
 * 必须配置在 spring.factories 中的 org.springframework.boot.env.EnvironmentPostProcessor 下面
 * 就可以自动注入到 Spring 容器。
 * 不能配置在 org.springframework.boot.autoconfigure.EnableAutoConfiguration 下面，否则不会在启动时执行类里面的方法。
 *
 * <p>
 * 1. 生成 16 位随机 AES 密钥：String randomKey = AESUtils.generateRandomKey();
 * <p>
 * 2. 在启动 jar 时把下面生成的 key 通过命令行参数 -Dvitality.cipher=${randomKey} 或者配置到 application.yml 中传递到应用程序中。Jar 启动参数（idea 设置 JVM arguments）
 * 命令行参数配置示例：-Dvitality.cipher=abcd1234
 * application.properties 示例：vitality.cipher=abcd1234
 * <p>
 * 3. 密钥加密：配置在 application.yaml 中的加密值：String encrypt = AESUtils.encryptByKey(randomKey, password);
 * <p>
 * 4. YML 配置：加密配置 {cipher} 开头紧接加密内容（ 非数据库配置专用， YML 中其它配置也是可以使用的 ）
 * spring.datasource.username='{cipher}Xb+EgsyuYRXw7U7sBJjBpA=='
 * spring.datasource.password='{cipher}Hzy5iliJbwDHhjLs1L0j6w=='
 * <p>
 * 5. 为什么以 {cipher} 作为前缀？目的是和 Spring cloud config 加密前缀保持一直
 *
 * @author mengweijin
 */
public class SafetyEncryptEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 第一优先级，从命令行中获取密钥。添加 JVM 参数：-Dvitality.cipher=abcd1234
        // 第二优先级，从 application.yml 中获取 vitality.cipher 配置的值
        String cipherKey = environment.getProperty("vitality.cipher");
        if(CharSequenceUtil.isBlank(cipherKey)) {
            return;
        }

        // 处理加密内容
        HashMap<String, Object> map = new HashMap<>();
        for (PropertySource<?> ps : environment.getPropertySources()) {
            if (ps instanceof OriginTrackedMapPropertySource source) {
                this.setDecryptValue(cipherKey, source, map);
            }
        }

        // 将解密的数据放入环境变量，并处于第一优先级上
        if (CollUtil.isNotEmpty(map)) {
            environment.getPropertySources().addFirst(new MapPropertySource("custom-encrypt", map));
        }
    }

    private void setDecryptValue(String cipherKey, OriginTrackedMapPropertySource source, HashMap<String, Object> map) {
        for (String name : source.getPropertyNames()) {
            Object value = source.getProperty(name);
            if (value instanceof String str) {
                if (str.startsWith("{cipher}")) {
                    map.put(name, AESUtils.decryptByKey(cipherKey, str.substring(8)));
                }
            }
        }
    }
}
