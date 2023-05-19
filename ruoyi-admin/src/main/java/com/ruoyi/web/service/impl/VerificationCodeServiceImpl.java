package com.ruoyi.web.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    /**
     * 生产的验证码位数
     */
    private final int generateVerificationCodeLength = 4;
    private final String[] metaCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        while (verificationCode.length() < generateVerificationCodeLength) {
            int i = random.nextInt(metaCode.length);
            verificationCode.append(metaCode[i]);
        }
        return verificationCode.toString();
    }
}

