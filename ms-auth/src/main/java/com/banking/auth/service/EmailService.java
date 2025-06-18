package com.banking.auth.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);

}
