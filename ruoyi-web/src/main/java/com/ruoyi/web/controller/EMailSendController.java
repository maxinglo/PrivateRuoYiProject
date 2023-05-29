package com.ruoyi.web.controller;

import com.ruoyi.common.SustechEmail.SustechEmail;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
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

    @Anonymous
    @RepeatSubmit(interval = 60000,message = "冷却时间60秒，请勿重复发送验证码")
    @GetMapping("/VerificationCode/{to}")
    public AjaxResult VerificationCode(@SustechEmail @PathVariable String to) {
        String cacheKey = "verification_code:" + to;
        boolean flag = eMailSendServiceImpl.sendEmailVerificationCode(to);
        if (flag){
            return success();
        }else {
            return error();
        }
    }
}
