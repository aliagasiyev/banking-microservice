package com.banking.auth.service;


import com.banking.auth.dto.response.RegisterResponse;
import com.banking.auth.enums.Role;
import com.banking.auth.dto.request.RegisterRequest;
import java.util.List;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest user, String creatorEmail);

    List<RegisterResponse> getUsersByRole(Role role);

    List<RegisterResponse> getAllUsers();

    String deleteUser(Long userId, String requestEmail);

}
