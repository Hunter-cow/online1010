package com.hunter.ucenter.controller;

import com.google.gson.Gson;
import com.hunter.commonUtils.JwtUtils;
import com.hunter.ucenter.entity.Member;
import com.hunter.ucenter.service.MemberService;
import com.hunter.ucenter.utils.ConstantWxUtils;
import com.hunter.ucenter.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.PasswordRecipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Slf4j
@Controller //注意这里没有配置 @RestController
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state) {
        //获取code值，临时票据，类似于验证码
        log.info("code:{}", code);
        log.info("state:{}", state);
        //拿着code，去请求微信固定的地址，得到两个值 access_token 和 openid
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个参数：id 秘钥 和 code值
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID
                , ConstantWxUtils.WX_OPEN_APP_SECRET
                , code);
        //请求上面拼接好的地址，得到两个值 access_token 和 openid
        //使用httpclient【不用浏览器，也能模拟器出浏览器的请求和响应过程】发送请求，得到返回的结果
        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            log.info("accessTokenInfo：{}" + accessTokenInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从accessTokenInfo中获取出  access_token 和 openid 的值
        //将 accessTokenInfo 转换成 map集合，根据map的key 就可以获取对应的value
        //使用json转换工具
        Gson gson = new Gson();
        HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
        String access_token = (String) mapAccessToken.get("access_token");
        String openid = (String) mapAccessToken.get("openid");
        //判断数据库是否存在相同的微信内容
        Member member = memberService.getMemberByOpenId(openid);
        if (member == null) {//说明没有注册
            //拿着 access_token 和 openid 的值再去请求微信提供的固定地址
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                log.info("resultUserInfo:{}", resultUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap<String, Object> resultMap = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) resultMap.get("nickname");
            String headImgUrl = (String) resultMap.get("headimgurl");
            member = new Member();
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            member.setOpenid(openid);
            memberService.save(member);
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());


        return "redirect:http://localhost:3000?token=" + token;
    }


    //微信扫描二维码
    @GetMapping("/login")
    public String getWxCode() {
        //请求微信地址
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url = String.format(baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "Hunter");
        log.info(url);
        return "redirect:" + url;
    }
}
