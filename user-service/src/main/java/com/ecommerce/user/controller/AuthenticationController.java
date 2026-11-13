package com.ecommerce.user.controller;

import com.ecommerce.user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ecommerce/authentications")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
}
