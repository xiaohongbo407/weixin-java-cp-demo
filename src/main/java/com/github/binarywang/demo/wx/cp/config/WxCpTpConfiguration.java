package com.github.binarywang.demo.wx.cp.config;

import com.google.common.collect.Maps;
import lombok.val;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpTpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceOnTpImpl;
import me.chanjar.weixin.cp.api.impl.WxCpTpServiceImpl;
import me.chanjar.weixin.cp.config.WxCpTpConfigStorage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.config.impl.WxCpTpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WxCpTpConfiguration TODO
 *
 * @author Xiaohb
 * @version 1.0
 * @date 2020/7/24 12:28
 */
@Configuration
@EnableConfigurationProperties(WxCpTpProperties.class)
public class WxCpTpConfiguration {

    private WxCpTpProperties properties;

    private static Map<String, WxCpTpService> cpTpServices = Maps.newHashMap();

    private static Map<String, WxCpService> cpService = Maps.newHashMap();


    @Autowired
    public WxCpTpConfiguration(WxCpTpProperties wxCpTpProperties){

        this.properties = wxCpTpProperties;
    }

    public static WxCpTpService getCpTpService(String suiteId){
        return cpTpServices.get(suiteId);
    }

    public static WxCpService getCpService(String authCorpId,WxCpTpService wxCpTpService) {
        // check 是否Map中
        String  suiteId = wxCpTpService.getWxCpTpConfigStorage().getSuiteId();

        WxCpService wxCpService = cpService.get(authCorpId + "_" + suiteId);
        if(wxCpService == null){
            // 不在Map里，则缓存查询当前授权企业ID和应用ID，是否存在
            // WcCpTpCacheService.getPermanentCode(String authCorpId);
            String permanentCode = "7gIECJ2OFvETIJOMdsJIjRKzXbz4IuuXePW_VBLnuJA";

            WxCpServiceOnTpImpl wxCpServiceOnTp = new WxCpServiceOnTpImpl(wxCpTpService);

            WxCpDefaultConfigImpl wxCpConfigStorage = new WxCpDefaultConfigImpl() ;
            wxCpConfigStorage.setCorpId(authCorpId);
            wxCpConfigStorage.setCorpSecret(permanentCode);
            wxCpServiceOnTp.setWxCpConfigStorage(wxCpConfigStorage);
            // 如果存在，初始化WxCpServiceOnTpImpl，放到map

            cpService.put(authCorpId+"_"+suiteId,wxCpServiceOnTp);
            return wxCpServiceOnTp;
        }
        return wxCpService;
    }

    public static WxCpService setCpService(String authCorpId,String suiteId,WxCpService service) {
        return cpService.put(authCorpId+"_"+suiteId,service);
    }

    @Bean
    public WxCpTpService wxCpTpService(){

        return WxCpTpConfiguration.getCpTpService(this.properties.getSuiteConfigs().get(0).getSuiteId());
    }



    @PostConstruct
    public void initServices(){
        cpTpServices = this.properties.getSuiteConfigs().stream().map(a -> {
            val tpConfigStorage = new WxCpTpDefaultConfigImpl();
            tpConfigStorage.setCorpId(properties.getCorpId());
            tpConfigStorage.setCorpSecret(properties.getCorpSecret());
            tpConfigStorage.setSuiteId(a.getSuiteId());
            tpConfigStorage.setSuiteSecret(a.getSuiteSecret());
            tpConfigStorage.setToken(a.getToken());
            tpConfigStorage.setAesKey(a.getAesKey());

            val service = new WxCpTpServiceImpl();
            service.setWxCpTpConfigStorage(tpConfigStorage);

            return service;
        }).collect(Collectors.toMap(service -> service.getWxCpTpConfigStorage().getSuiteId(),a -> a ));
    }







}
