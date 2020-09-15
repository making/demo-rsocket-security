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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@Profile("!bearer")
public class HelloController {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	@ConnectMapping
	public void setup(@Payload String payload, @Headers Map<String, Object> headers) {
		log.info("setup:payload={}", payload);
		log.info("setup:headers={}", headers);
	}

	@MessageMapping("hello")
	public String hello(@AuthenticationPrincipal UserDetails user, @Payload String payload, @Headers Map<String, Object> headers) {
		log.info("hello:payload={}", payload);
		log.info("hello:headers={}", headers);
		return "Hello " + payload + ", " + user.getUsername() + "!";
	}
}
