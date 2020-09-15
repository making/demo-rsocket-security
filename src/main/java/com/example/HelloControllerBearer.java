package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

@Controller
@Profile("bearer")
public class HelloControllerBearer {
	private static final Logger log = LoggerFactory.getLogger(HelloControllerBearer.class);

	@ConnectMapping
	public void setup(@Payload String payload, @Headers Map<String, Object> headers) {
		log.info("setup:payload={}", payload);
		log.info("setup:headers={}", headers);
	}

	@MessageMapping("hello")
	public String hello(@AuthenticationPrincipal Jwt jwt, @Payload String payload, @Headers Map<String, Object> headers) {
		log.info("hello:payload={}", payload);
		log.info("hello:headers={}", headers);
		return "Hello " + payload + ", " + jwt.getClaimAsString("email") + "!";
	}
}
