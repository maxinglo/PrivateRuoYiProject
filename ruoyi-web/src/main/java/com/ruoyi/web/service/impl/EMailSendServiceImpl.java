package com.ruoyi.web.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.service.IEMailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class EMailSendServiceImpl implements IEMailSendService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private VerificationCodeServiceImpl verificationCodeService;

    @Autowired
    private RedisCache redisCache;

    public void SendTextMailMessage(String to, String subject, String text) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom(sendMailer);
            mimeMessageHelper.setTo(to.split(","));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendEmailVerificationCode(String to) {//调用 VerificationCodeService 生产验证码
        String verifyCode = verificationCodeService.generateVerificationCode();
        MimeMessage message=javaMailSender.createMimeMessage();
        // 生成缓存键
        String cacheKey = "verification_code:" + to;
        // 判断之前的验证码是否存在
        if (redisCache.hasKey(cacheKey)) {
            // 删除之前的验证码缓存
            redisCache.deleteObject(cacheKey);
        }
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(sendMailer);
            helper.setTo(to);
            helper.setSubject("注册验证码");// 接收调用方传入的验证码
            String VALIDATE_CODE_TEMPLATE =
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "  <head>\n" +
                            "    <meta charset=\"UTF-8\" />\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                            "    <title>您的验证码请求</title>\n" +
                            "  </head>\n" +
                            "  <body>\n" +
                            "    <h2 style=\"text-align: center;\">验证您的南科大邮箱</h2>\n" +
                            "    <p style=\"text-indent: 2em;\">尊敬的老师，</p>\n" +
                            "    <p style=\"text-indent: 2em;\">\n" +
                            "      您好！我们在进行系统功能检查，需要向您的邮箱： $EMAIL$ 发送一个验证码，以验证功能的可用性。\n" +
                            "顺便一提，这是我们课程作业的一部分，以实现自动发送邮件的功能。看起来我们的课程正在起飞，很快我们就会迈向更复杂的编程任务！您的验证码是：\n" +
                            "    </p>\n" +
                            "    <h1 style=\"color: #1890ff; text-align: center;\">\n" +
                            "      $CODE$\n" +
                            "    </h1>\n" +
                            "    <p style=\"text-indent: 2em;\">如果您有任何问题，或者对我们课程的一部分感到好奇，随时联系我！我们非常愿意分享我们在课程中学到的新知识。</p>\n" +
                            "    <p style=\"text-indent: 2em;\">祝好，</p>\n" +
                            "    <p style=\"text-indent: 2em;\">马邢龙、黄俊、常旭峰</p>\n" +
                            "  </body>\n" +
                            "</html>";
                    /*"<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "  <head>\n" +
                    "    <meta charset=\"UTF-8\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "    <title>验证您的南科大邮箱</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <h2 style=\"text-align: center;\">验证您的南科大邮箱</h2>\n" +
                    "    <p style=\"text-indent: 2em;\">hi~</p>\n" +
                    "    <p style=\"text-indent: 2em;\">\n" +
                    "      您已选择 $EMAIL$ 作为你的邮箱账户，为验证此电子邮箱属于你，请在你的邮箱验证界面输入下方验证码\n" +
                    "    </p>\n" +
                    "    <h1 style=\"color: #1890ff; text-align: center;\">\n" +
                    "      $CODE$\n" +
                    "    </h1>\n" +
                    "    <p style=\"text-indent: 2em;\">此验证码将于5分钟后失效，请您尽快完成验证。</p>\n" +
                    "  </body>\n" +
                    "</html>";*/
            String text = VALIDATE_CODE_TEMPLATE.replace("$EMAIL$", to).replace("$CODE$", verifyCode);
            helper.setText(text,true);
            //javaMailSender.send(message);
            // 设置缓存对象，失效时间为5分钟
            redisCache.setCacheObject(cacheKey, verifyCode, 5, TimeUnit.MINUTES);
            return true;
        }catch (MessagingException e) {
            return false;
        }
    }
}
