package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpTpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpTpService;
import me.chanjar.weixin.cp.bean.WxCpOauth2UserInfo;
import me.chanjar.weixin.cp.bean.WxCpUserExternalGroupChatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WcCpTpTestController TODO
 *
 * @author Xiaohb
 * @version 1.0
 * @date 2020/7/24 15:31
 */
@RestController
@RequestMapping("/wx/test")
public class WcCpTpTestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/user")
    public Object getUserInfo(String suiteId,String authCorpId,String chatId,String suiteTicket){

        this.logger.info("\n接收到来自微信服务器的认证消息：suiteId = [{}], authCorpId = [{}], chatId = [{}], suiteTicket = [{}]",
            suiteId, authCorpId, chatId, suiteTicket);


        final WxCpTpService wxCpTpService = WxCpTpConfiguration.getCpTpService(suiteId);

        //
        wxCpTpService.getWxCpTpConfigStorage().updateSuiteTicket(suiteTicket,600);

        wxCpTpService.getWxCpTpConfigStorage().getSuiteTicket();
        if (wxCpTpService == null){
            throw new IllegalArgumentException(String.format("未找到对应suiteId=[%d]的配置，请核实！", suiteId));
        }

        final WxCpService wxCpService = WxCpTpConfiguration.getCpService(authCorpId,wxCpTpService);
        if (wxCpService == null){
            throw new IllegalArgumentException(String.format("未找到对应authCorpId=[%d]的配置，请核实！", authCorpId));
        }

        try {
            WxCpUserExternalGroupChatInfo groupChat = wxCpService.getExternalContactService().getGroupChat(chatId);





            return groupChat;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    }



    public String getJsapiTicket(String code,String appId){
        String suiteId = appId;

        //更具jwtToken解析用户信息（用户所属企业、用户ID）
        String myJWTtoken = "";
        String authCorpId = "";
        String UserId = "";

        final WxCpTpService wxCpTpService = WxCpTpConfiguration.getCpTpService(suiteId);
        final WxCpService wxCpService = WxCpTpConfiguration.getCpService(authCorpId,wxCpTpService);

        try {
            String jsapiTicket = wxCpService.getJsapiTicket();

//            wxCpTpService.jsCode2Session()
//

            return jsapiTicket;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }


}
