package com.github.binarywang.demo.wx.cp.security.utils;

/**
 * @Description:
 * @Author: zule
 * @Date: 2019/5/9
 */
public interface UrlMatcher {
    Object compile(String paramString);
    boolean pathMatchesUrl(Object paramObject, String paramString);
    String getUniversalMatchPattern();
    boolean requiresLowerCaseUrl();
}
