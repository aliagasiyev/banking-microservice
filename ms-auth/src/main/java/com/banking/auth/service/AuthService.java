package com.banking.auth.service;

import com.banking.auth.dto.request.ForgotPasswordRequest;
import com.banking.auth.dto.request.LoginRequest;
import com.banking.auth.dto.request.RefreshRequest;
import com.banking.auth.dto.request.ResetPasswordRequest;
import com.banking.auth.dto.response.LoginResponse;
import com.banking.auth.dto.response.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshRequest request);

    String forgotPassword(ForgotPasswordRequest request, String authenticatedEmail);

    String resetPassword(ResetPasswordRequest request);
}
