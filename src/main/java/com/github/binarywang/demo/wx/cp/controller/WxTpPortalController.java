package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpTpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpTpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceOnTpImpl;
import me.chanjar.weixin.cp.bean.WxCpTpAuthInfo;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.util.crypto.WxCpTpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WxTpPortalController TODO
 *
 * @author Xiaohb
 * @version 1.0
 * @date 2020/7/24 14:12
 */
@RestController
@RequestMapping("/wx/cp/tp/portal/{suiteId}")
public class WxTpPortalController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String authGet(@PathVariable String suiteId,
                          @RequestParam(name = "msg_signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr){
        this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
            signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxCpTpService wxCpTpService = WxCpTpConfiguration.getCpTpService(suiteId);

        if (wxCpTpService == null){
            throw new IllegalArgumentException(String.format("未找到对应suiteId=[%d]的配置，请核实！", suiteId));
        }

        if(wxCpTpService.checkSignature(signature,timestamp,nonce,echostr)){
            return  new WxCpTpCryptUtil(wxCpTpService.getWxCpTpConfigStorage()).decrypt(echostr);
        }

        try {
            WxCpTpAuthInfo authInfo = wxCpTpService.getAuthInfo("", "");





        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "非法请求";
    }



}
