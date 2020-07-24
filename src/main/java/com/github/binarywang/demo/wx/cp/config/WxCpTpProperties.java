package com.github.binarywang.demo.wx.cp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Description {@link }
 *
 * @author XiaoHB
 * @version 1.0
 * @date 2020/7/24 12:17
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "wechat.tp")
public class WxCpTpProperties {

    /**
     * 设置微信企业服务商的企业ID（corpId）
     */
    private volatile String corpId;
    /**
     * 设置微信企业服务商的企业密钥（corpSecret）
     */
    private volatile String corpSecret;

    private List<WxCpTpProperties.SuiteConfig> suiteConfigs;

    @Getter
    @Setter
    public static class SuiteConfig {
        /**
         * 设置微信企业服务商的企业密钥（corpSecret）
         */
        private volatile String suiteId;
        private volatile String suiteSecret;
        private volatile String token;
        private volatile String aesKey;
    }


}
