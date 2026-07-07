package com.techie.authservice.controller;

import com.techie.authservice.model.dto.LoginRequest;
import com.techie.authservice.model.dto.LoginResponse;
import com.techie.authservice.service.AuthService;
import com.techie.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "使用者登入", description = "使用者登入")
    @PostMapping("/login")
    ResponseEntity<ApiResponse<LoginResponse>> doLogin(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = authService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(loginResponse));
    }

}
