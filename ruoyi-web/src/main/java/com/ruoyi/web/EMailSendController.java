package com.ruoyi.web;

import com.ruoyi.common.SustechEmail.SustechEmail;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.service.impl.EMailSendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@Anonymous
@RestController
@RequestMapping("/web/Email")
public class EMailSendController extends BaseController {
    @Autowired
    private EMailSendServiceImpl eMailSendServiceImpl;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/emailCode")
    public void emailCode() {
        System.out.println("发送方法触发");
        eMailSendServiceImpl.SendTextMailMessage("1046606655@qq.com","测试标题","测试内容");
    }
    @RepeatSubmit(interval = 60000,message = "冷却时间60秒，请勿重复发送验证码")
    @GetMapping("/VerificationCode/{to}")
    public AjaxResult VerificationCode(@SustechEmail @PathVariable String to) {
        System.out.println("发送方法触发");
        String cacheKey = "verification_code:" + to;
        Boolean flag = eMailSendServiceImpl.sendEmailVerificationCode(to);
        String cachedCode = redisCache.getCacheObject(cacheKey);
        System.out.println(cachedCode);
        if (flag){
            return success();
        }else {
            return error();
        }
    }
    @GetMapping("/Test")
    public AjaxResult Test() {
        System.out.println("方法触发");
        return success();
    }
}
