package com.example;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

@Controller
@Profile("bearer")
public class HelloControllerBearer {

	@MessageMapping("hello")
	public String hello(@AuthenticationPrincipal Jwt jwt, @Payload String payload) {
		return "Hello " + payload + ", " + jwt.getClaimAsString("email") + "!";
	}
}
