package com.ruoyi.web.service;

public interface IEMailSendService {
    public void SendTextMailMessage(String to, String subject, String text);

    public boolean sendEmailVerificationCode(String to);
}