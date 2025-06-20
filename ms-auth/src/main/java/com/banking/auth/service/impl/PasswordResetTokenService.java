package com.banking.auth.service.impl;

import com.banking.auth.config.PasswordResetToken;
import com.banking.auth.entity.UserEntity;
import com.banking.auth.exceptions.SamePasswordException;
import com.banking.auth.exceptions.TokenExpiredException;
import com.banking.auth.repository.PasswordResetTokenRepository;
import com.banking.auth.repository.UserRepository;
import com.banking.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void createPasswordResetToken(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("No user found with the provided email: " + email);
        }
        UserEntity user = userOpt.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();
        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String body = "To reset your password, click the link below:\n" + resetLink;
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    public Optional<PasswordResetToken> validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);
        if (resetTokenOpt.isPresent()) {
            PasswordResetToken resetToken = resetTokenOpt.get();
            if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                tokenRepository.delete(resetToken);
                throw new TokenExpiredException("The password reset token has expired.");
            }
            return Optional.of(resetToken);
        }
        return Optional.empty();
    }

    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = validatePasswordResetToken(token);
        if (resetTokenOpt.isEmpty()) {
            throw new RuntimeException("Token is invalid or has expired");
        }
        PasswordResetToken resetToken = resetTokenOpt.get();
        UserEntity user = resetToken.getUser();
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new SamePasswordException("The new password cannot be the same as the old password.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
